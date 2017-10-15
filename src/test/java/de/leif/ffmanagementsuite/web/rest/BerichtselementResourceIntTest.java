package de.leif.ffmanagementsuite.web.rest;

import de.leif.ffmanagementsuite.FfManagementSuiteApp;

import de.leif.ffmanagementsuite.domain.Berichtselement;
import de.leif.ffmanagementsuite.repository.BerichtselementRepository;
import de.leif.ffmanagementsuite.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.leif.ffmanagementsuite.domain.enumeration.Berichtskategorie;
/**
 * Test class for the BerichtselementResource REST controller.
 *
 * @see BerichtselementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FfManagementSuiteApp.class)
public class BerichtselementResourceIntTest {

    private static final Instant DEFAULT_BEGINN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BEGINN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ENDE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ENDE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_TITEL = "AAAAAAAAAA";
    private static final String UPDATED_TITEL = "BBBBBBBBBB";

    private static final String DEFAULT_BESCHREIBUNG = "AAAAAAAAAA";
    private static final String UPDATED_BESCHREIBUNG = "BBBBBBBBBB";

    private static final Integer DEFAULT_STUNDEN = 0;
    private static final Integer UPDATED_STUNDEN = 1;

    private static final Berichtskategorie DEFAULT_KATEGORIE = Berichtskategorie.UEBUNG;
    private static final Berichtskategorie UPDATED_KATEGORIE = Berichtskategorie.EINSATZ;

    @Autowired
    private BerichtselementRepository berichtselementRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBerichtselementMockMvc;

    private Berichtselement berichtselement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BerichtselementResource berichtselementResource = new BerichtselementResource(berichtselementRepository);
        this.restBerichtselementMockMvc = MockMvcBuilders.standaloneSetup(berichtselementResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Berichtselement createEntity(EntityManager em) {
        Berichtselement berichtselement = new Berichtselement()
            .beginn(DEFAULT_BEGINN)
            .ende(DEFAULT_ENDE)
            .titel(DEFAULT_TITEL)
            .beschreibung(DEFAULT_BESCHREIBUNG)
            .stunden(DEFAULT_STUNDEN)
            .kategorie(DEFAULT_KATEGORIE);
        return berichtselement;
    }

    @Before
    public void initTest() {
        berichtselement = createEntity(em);
    }

    @Test
    @Transactional
    public void createBerichtselement() throws Exception {
        int databaseSizeBeforeCreate = berichtselementRepository.findAll().size();

        // Create the Berichtselement
        restBerichtselementMockMvc.perform(post("/api/berichtselements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(berichtselement)))
            .andExpect(status().isCreated());

        // Validate the Berichtselement in the database
        List<Berichtselement> berichtselementList = berichtselementRepository.findAll();
        assertThat(berichtselementList).hasSize(databaseSizeBeforeCreate + 1);
        Berichtselement testBerichtselement = berichtselementList.get(berichtselementList.size() - 1);
        assertThat(testBerichtselement.getBeginn()).isEqualTo(DEFAULT_BEGINN);
        assertThat(testBerichtselement.getEnde()).isEqualTo(DEFAULT_ENDE);
        assertThat(testBerichtselement.getTitel()).isEqualTo(DEFAULT_TITEL);
        assertThat(testBerichtselement.getBeschreibung()).isEqualTo(DEFAULT_BESCHREIBUNG);
        assertThat(testBerichtselement.getStunden()).isEqualTo(DEFAULT_STUNDEN);
        assertThat(testBerichtselement.getKategorie()).isEqualTo(DEFAULT_KATEGORIE);
    }

    @Test
    @Transactional
    public void createBerichtselementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = berichtselementRepository.findAll().size();

        // Create the Berichtselement with an existing ID
        berichtselement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBerichtselementMockMvc.perform(post("/api/berichtselements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(berichtselement)))
            .andExpect(status().isBadRequest());

        // Validate the Berichtselement in the database
        List<Berichtselement> berichtselementList = berichtselementRepository.findAll();
        assertThat(berichtselementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkBeginnIsRequired() throws Exception {
        int databaseSizeBeforeTest = berichtselementRepository.findAll().size();
        // set the field null
        berichtselement.setBeginn(null);

        // Create the Berichtselement, which fails.

        restBerichtselementMockMvc.perform(post("/api/berichtselements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(berichtselement)))
            .andExpect(status().isBadRequest());

        List<Berichtselement> berichtselementList = berichtselementRepository.findAll();
        assertThat(berichtselementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndeIsRequired() throws Exception {
        int databaseSizeBeforeTest = berichtselementRepository.findAll().size();
        // set the field null
        berichtselement.setEnde(null);

        // Create the Berichtselement, which fails.

        restBerichtselementMockMvc.perform(post("/api/berichtselements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(berichtselement)))
            .andExpect(status().isBadRequest());

        List<Berichtselement> berichtselementList = berichtselementRepository.findAll();
        assertThat(berichtselementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTitelIsRequired() throws Exception {
        int databaseSizeBeforeTest = berichtselementRepository.findAll().size();
        // set the field null
        berichtselement.setTitel(null);

        // Create the Berichtselement, which fails.

        restBerichtselementMockMvc.perform(post("/api/berichtselements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(berichtselement)))
            .andExpect(status().isBadRequest());

        List<Berichtselement> berichtselementList = berichtselementRepository.findAll();
        assertThat(berichtselementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBeschreibungIsRequired() throws Exception {
        int databaseSizeBeforeTest = berichtselementRepository.findAll().size();
        // set the field null
        berichtselement.setBeschreibung(null);

        // Create the Berichtselement, which fails.

        restBerichtselementMockMvc.perform(post("/api/berichtselements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(berichtselement)))
            .andExpect(status().isBadRequest());

        List<Berichtselement> berichtselementList = berichtselementRepository.findAll();
        assertThat(berichtselementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStundenIsRequired() throws Exception {
        int databaseSizeBeforeTest = berichtselementRepository.findAll().size();
        // set the field null
        berichtselement.setStunden(null);

        // Create the Berichtselement, which fails.

        restBerichtselementMockMvc.perform(post("/api/berichtselements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(berichtselement)))
            .andExpect(status().isBadRequest());

        List<Berichtselement> berichtselementList = berichtselementRepository.findAll();
        assertThat(berichtselementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBerichtselements() throws Exception {
        // Initialize the database
        berichtselementRepository.saveAndFlush(berichtselement);

        // Get all the berichtselementList
        restBerichtselementMockMvc.perform(get("/api/berichtselements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(berichtselement.getId().intValue())))
            .andExpect(jsonPath("$.[*].beginn").value(hasItem(DEFAULT_BEGINN.toString())))
            .andExpect(jsonPath("$.[*].ende").value(hasItem(DEFAULT_ENDE.toString())))
            .andExpect(jsonPath("$.[*].titel").value(hasItem(DEFAULT_TITEL.toString())))
            .andExpect(jsonPath("$.[*].beschreibung").value(hasItem(DEFAULT_BESCHREIBUNG.toString())))
            .andExpect(jsonPath("$.[*].stunden").value(hasItem(DEFAULT_STUNDEN)))
            .andExpect(jsonPath("$.[*].kategorie").value(hasItem(DEFAULT_KATEGORIE.toString())));
    }

    @Test
    @Transactional
    public void getBerichtselement() throws Exception {
        // Initialize the database
        berichtselementRepository.saveAndFlush(berichtselement);

        // Get the berichtselement
        restBerichtselementMockMvc.perform(get("/api/berichtselements/{id}", berichtselement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(berichtselement.getId().intValue()))
            .andExpect(jsonPath("$.beginn").value(DEFAULT_BEGINN.toString()))
            .andExpect(jsonPath("$.ende").value(DEFAULT_ENDE.toString()))
            .andExpect(jsonPath("$.titel").value(DEFAULT_TITEL.toString()))
            .andExpect(jsonPath("$.beschreibung").value(DEFAULT_BESCHREIBUNG.toString()))
            .andExpect(jsonPath("$.stunden").value(DEFAULT_STUNDEN))
            .andExpect(jsonPath("$.kategorie").value(DEFAULT_KATEGORIE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBerichtselement() throws Exception {
        // Get the berichtselement
        restBerichtselementMockMvc.perform(get("/api/berichtselements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBerichtselement() throws Exception {
        // Initialize the database
        berichtselementRepository.saveAndFlush(berichtselement);
        int databaseSizeBeforeUpdate = berichtselementRepository.findAll().size();

        // Update the berichtselement
        Berichtselement updatedBerichtselement = berichtselementRepository.findOne(berichtselement.getId());
        updatedBerichtselement
            .beginn(UPDATED_BEGINN)
            .ende(UPDATED_ENDE)
            .titel(UPDATED_TITEL)
            .beschreibung(UPDATED_BESCHREIBUNG)
            .stunden(UPDATED_STUNDEN)
            .kategorie(UPDATED_KATEGORIE);

        restBerichtselementMockMvc.perform(put("/api/berichtselements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBerichtselement)))
            .andExpect(status().isOk());

        // Validate the Berichtselement in the database
        List<Berichtselement> berichtselementList = berichtselementRepository.findAll();
        assertThat(berichtselementList).hasSize(databaseSizeBeforeUpdate);
        Berichtselement testBerichtselement = berichtselementList.get(berichtselementList.size() - 1);
        assertThat(testBerichtselement.getBeginn()).isEqualTo(UPDATED_BEGINN);
        assertThat(testBerichtselement.getEnde()).isEqualTo(UPDATED_ENDE);
        assertThat(testBerichtselement.getTitel()).isEqualTo(UPDATED_TITEL);
        assertThat(testBerichtselement.getBeschreibung()).isEqualTo(UPDATED_BESCHREIBUNG);
        assertThat(testBerichtselement.getStunden()).isEqualTo(UPDATED_STUNDEN);
        assertThat(testBerichtselement.getKategorie()).isEqualTo(UPDATED_KATEGORIE);
    }

    @Test
    @Transactional
    public void updateNonExistingBerichtselement() throws Exception {
        int databaseSizeBeforeUpdate = berichtselementRepository.findAll().size();

        // Create the Berichtselement

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBerichtselementMockMvc.perform(put("/api/berichtselements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(berichtselement)))
            .andExpect(status().isCreated());

        // Validate the Berichtselement in the database
        List<Berichtselement> berichtselementList = berichtselementRepository.findAll();
        assertThat(berichtselementList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBerichtselement() throws Exception {
        // Initialize the database
        berichtselementRepository.saveAndFlush(berichtselement);
        int databaseSizeBeforeDelete = berichtselementRepository.findAll().size();

        // Get the berichtselement
        restBerichtselementMockMvc.perform(delete("/api/berichtselements/{id}", berichtselement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Berichtselement> berichtselementList = berichtselementRepository.findAll();
        assertThat(berichtselementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Berichtselement.class);
        Berichtselement berichtselement1 = new Berichtselement();
        berichtselement1.setId(1L);
        Berichtselement berichtselement2 = new Berichtselement();
        berichtselement2.setId(berichtselement1.getId());
        assertThat(berichtselement1).isEqualTo(berichtselement2);
        berichtselement2.setId(2L);
        assertThat(berichtselement1).isNotEqualTo(berichtselement2);
        berichtselement1.setId(null);
        assertThat(berichtselement1).isNotEqualTo(berichtselement2);
    }
}
