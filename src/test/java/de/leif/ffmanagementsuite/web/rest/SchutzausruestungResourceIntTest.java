package de.leif.ffmanagementsuite.web.rest;

import de.leif.ffmanagementsuite.FfManagementSuiteApp;

import de.leif.ffmanagementsuite.domain.Schutzausruestung;
import de.leif.ffmanagementsuite.repository.SchutzausruestungRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SchutzausruestungResource REST controller.
 *
 * @see SchutzausruestungResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FfManagementSuiteApp.class)
public class SchutzausruestungResourceIntTest {

    private static final Integer DEFAULT_NUMMER = 1;
    private static final Integer UPDATED_NUMMER = 2;

    private static final LocalDate DEFAULT_ANGESCHAFFT_AM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ANGESCHAFFT_AM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_AUSGEMUSTERT_AM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_AUSGEMUSTERT_AM = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_GROESSE = "AAAAAAAAAA";
    private static final String UPDATED_GROESSE = "BBBBBBBBBB";

    @Autowired
    private SchutzausruestungRepository schutzausruestungRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSchutzausruestungMockMvc;

    private Schutzausruestung schutzausruestung;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SchutzausruestungResource schutzausruestungResource = new SchutzausruestungResource(schutzausruestungRepository);
        this.restSchutzausruestungMockMvc = MockMvcBuilders.standaloneSetup(schutzausruestungResource)
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
    public static Schutzausruestung createEntity(EntityManager em) {
        Schutzausruestung schutzausruestung = new Schutzausruestung()
            .nummer(DEFAULT_NUMMER)
            .angeschafftAm(DEFAULT_ANGESCHAFFT_AM)
            .ausgemustertAm(DEFAULT_AUSGEMUSTERT_AM)
            .groesse(DEFAULT_GROESSE);
        return schutzausruestung;
    }

    @Before
    public void initTest() {
        schutzausruestung = createEntity(em);
    }

    @Test
    @Transactional
    public void createSchutzausruestung() throws Exception {
        int databaseSizeBeforeCreate = schutzausruestungRepository.findAll().size();

        // Create the Schutzausruestung
        restSchutzausruestungMockMvc.perform(post("/api/schutzausruestungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schutzausruestung)))
            .andExpect(status().isCreated());

        // Validate the Schutzausruestung in the database
        List<Schutzausruestung> schutzausruestungList = schutzausruestungRepository.findAll();
        assertThat(schutzausruestungList).hasSize(databaseSizeBeforeCreate + 1);
        Schutzausruestung testSchutzausruestung = schutzausruestungList.get(schutzausruestungList.size() - 1);
        assertThat(testSchutzausruestung.getNummer()).isEqualTo(DEFAULT_NUMMER);
        assertThat(testSchutzausruestung.getAngeschafftAm()).isEqualTo(DEFAULT_ANGESCHAFFT_AM);
        assertThat(testSchutzausruestung.getAusgemustertAm()).isEqualTo(DEFAULT_AUSGEMUSTERT_AM);
        assertThat(testSchutzausruestung.getGroesse()).isEqualTo(DEFAULT_GROESSE);
    }

    @Test
    @Transactional
    public void createSchutzausruestungWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = schutzausruestungRepository.findAll().size();

        // Create the Schutzausruestung with an existing ID
        schutzausruestung.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchutzausruestungMockMvc.perform(post("/api/schutzausruestungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schutzausruestung)))
            .andExpect(status().isBadRequest());

        // Validate the Schutzausruestung in the database
        List<Schutzausruestung> schutzausruestungList = schutzausruestungRepository.findAll();
        assertThat(schutzausruestungList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNummerIsRequired() throws Exception {
        int databaseSizeBeforeTest = schutzausruestungRepository.findAll().size();
        // set the field null
        schutzausruestung.setNummer(null);

        // Create the Schutzausruestung, which fails.

        restSchutzausruestungMockMvc.perform(post("/api/schutzausruestungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schutzausruestung)))
            .andExpect(status().isBadRequest());

        List<Schutzausruestung> schutzausruestungList = schutzausruestungRepository.findAll();
        assertThat(schutzausruestungList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGroesseIsRequired() throws Exception {
        int databaseSizeBeforeTest = schutzausruestungRepository.findAll().size();
        // set the field null
        schutzausruestung.setGroesse(null);

        // Create the Schutzausruestung, which fails.

        restSchutzausruestungMockMvc.perform(post("/api/schutzausruestungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schutzausruestung)))
            .andExpect(status().isBadRequest());

        List<Schutzausruestung> schutzausruestungList = schutzausruestungRepository.findAll();
        assertThat(schutzausruestungList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSchutzausruestungs() throws Exception {
        // Initialize the database
        schutzausruestungRepository.saveAndFlush(schutzausruestung);

        // Get all the schutzausruestungList
        restSchutzausruestungMockMvc.perform(get("/api/schutzausruestungs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(schutzausruestung.getId().intValue())))
            .andExpect(jsonPath("$.[*].nummer").value(hasItem(DEFAULT_NUMMER)))
            .andExpect(jsonPath("$.[*].angeschafftAm").value(hasItem(DEFAULT_ANGESCHAFFT_AM.toString())))
            .andExpect(jsonPath("$.[*].ausgemustertAm").value(hasItem(DEFAULT_AUSGEMUSTERT_AM.toString())))
            .andExpect(jsonPath("$.[*].groesse").value(hasItem(DEFAULT_GROESSE.toString())));
    }

    @Test
    @Transactional
    public void getSchutzausruestung() throws Exception {
        // Initialize the database
        schutzausruestungRepository.saveAndFlush(schutzausruestung);

        // Get the schutzausruestung
        restSchutzausruestungMockMvc.perform(get("/api/schutzausruestungs/{id}", schutzausruestung.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(schutzausruestung.getId().intValue()))
            .andExpect(jsonPath("$.nummer").value(DEFAULT_NUMMER))
            .andExpect(jsonPath("$.angeschafftAm").value(DEFAULT_ANGESCHAFFT_AM.toString()))
            .andExpect(jsonPath("$.ausgemustertAm").value(DEFAULT_AUSGEMUSTERT_AM.toString()))
            .andExpect(jsonPath("$.groesse").value(DEFAULT_GROESSE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSchutzausruestung() throws Exception {
        // Get the schutzausruestung
        restSchutzausruestungMockMvc.perform(get("/api/schutzausruestungs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSchutzausruestung() throws Exception {
        // Initialize the database
        schutzausruestungRepository.saveAndFlush(schutzausruestung);
        int databaseSizeBeforeUpdate = schutzausruestungRepository.findAll().size();

        // Update the schutzausruestung
        Schutzausruestung updatedSchutzausruestung = schutzausruestungRepository.findOne(schutzausruestung.getId());
        updatedSchutzausruestung
            .nummer(UPDATED_NUMMER)
            .angeschafftAm(UPDATED_ANGESCHAFFT_AM)
            .ausgemustertAm(UPDATED_AUSGEMUSTERT_AM)
            .groesse(UPDATED_GROESSE);

        restSchutzausruestungMockMvc.perform(put("/api/schutzausruestungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSchutzausruestung)))
            .andExpect(status().isOk());

        // Validate the Schutzausruestung in the database
        List<Schutzausruestung> schutzausruestungList = schutzausruestungRepository.findAll();
        assertThat(schutzausruestungList).hasSize(databaseSizeBeforeUpdate);
        Schutzausruestung testSchutzausruestung = schutzausruestungList.get(schutzausruestungList.size() - 1);
        assertThat(testSchutzausruestung.getNummer()).isEqualTo(UPDATED_NUMMER);
        assertThat(testSchutzausruestung.getAngeschafftAm()).isEqualTo(UPDATED_ANGESCHAFFT_AM);
        assertThat(testSchutzausruestung.getAusgemustertAm()).isEqualTo(UPDATED_AUSGEMUSTERT_AM);
        assertThat(testSchutzausruestung.getGroesse()).isEqualTo(UPDATED_GROESSE);
    }

    @Test
    @Transactional
    public void updateNonExistingSchutzausruestung() throws Exception {
        int databaseSizeBeforeUpdate = schutzausruestungRepository.findAll().size();

        // Create the Schutzausruestung

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSchutzausruestungMockMvc.perform(put("/api/schutzausruestungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schutzausruestung)))
            .andExpect(status().isCreated());

        // Validate the Schutzausruestung in the database
        List<Schutzausruestung> schutzausruestungList = schutzausruestungRepository.findAll();
        assertThat(schutzausruestungList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSchutzausruestung() throws Exception {
        // Initialize the database
        schutzausruestungRepository.saveAndFlush(schutzausruestung);
        int databaseSizeBeforeDelete = schutzausruestungRepository.findAll().size();

        // Get the schutzausruestung
        restSchutzausruestungMockMvc.perform(delete("/api/schutzausruestungs/{id}", schutzausruestung.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Schutzausruestung> schutzausruestungList = schutzausruestungRepository.findAll();
        assertThat(schutzausruestungList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Schutzausruestung.class);
        Schutzausruestung schutzausruestung1 = new Schutzausruestung();
        schutzausruestung1.setId(1L);
        Schutzausruestung schutzausruestung2 = new Schutzausruestung();
        schutzausruestung2.setId(schutzausruestung1.getId());
        assertThat(schutzausruestung1).isEqualTo(schutzausruestung2);
        schutzausruestung2.setId(2L);
        assertThat(schutzausruestung1).isNotEqualTo(schutzausruestung2);
        schutzausruestung1.setId(null);
        assertThat(schutzausruestung1).isNotEqualTo(schutzausruestung2);
    }
}
