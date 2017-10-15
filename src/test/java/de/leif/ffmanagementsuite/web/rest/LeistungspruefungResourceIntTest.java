package de.leif.ffmanagementsuite.web.rest;

import de.leif.ffmanagementsuite.FfManagementSuiteApp;

import de.leif.ffmanagementsuite.domain.Leistungspruefung;
import de.leif.ffmanagementsuite.repository.LeistungspruefungRepository;
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

import de.leif.ffmanagementsuite.domain.enumeration.LeistungspruefungTyp;
/**
 * Test class for the LeistungspruefungResource REST controller.
 *
 * @see LeistungspruefungResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FfManagementSuiteApp.class)
public class LeistungspruefungResourceIntTest {

    private static final LeistungspruefungTyp DEFAULT_TYP = LeistungspruefungTyp.LOESCHANGRIFF;
    private static final LeistungspruefungTyp UPDATED_TYP = LeistungspruefungTyp.THL;

    private static final Integer DEFAULT_STUFE = 1;
    private static final Integer UPDATED_STUFE = 2;

    private static final LocalDate DEFAULT_ABGELEGT_AM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ABGELEGT_AM = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private LeistungspruefungRepository leistungspruefungRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLeistungspruefungMockMvc;

    private Leistungspruefung leistungspruefung;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LeistungspruefungResource leistungspruefungResource = new LeistungspruefungResource(leistungspruefungRepository);
        this.restLeistungspruefungMockMvc = MockMvcBuilders.standaloneSetup(leistungspruefungResource)
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
    public static Leistungspruefung createEntity(EntityManager em) {
        Leistungspruefung leistungspruefung = new Leistungspruefung()
            .typ(DEFAULT_TYP)
            .stufe(DEFAULT_STUFE)
            .abgelegtAm(DEFAULT_ABGELEGT_AM);
        return leistungspruefung;
    }

    @Before
    public void initTest() {
        leistungspruefung = createEntity(em);
    }

    @Test
    @Transactional
    public void createLeistungspruefung() throws Exception {
        int databaseSizeBeforeCreate = leistungspruefungRepository.findAll().size();

        // Create the Leistungspruefung
        restLeistungspruefungMockMvc.perform(post("/api/leistungspruefungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leistungspruefung)))
            .andExpect(status().isCreated());

        // Validate the Leistungspruefung in the database
        List<Leistungspruefung> leistungspruefungList = leistungspruefungRepository.findAll();
        assertThat(leistungspruefungList).hasSize(databaseSizeBeforeCreate + 1);
        Leistungspruefung testLeistungspruefung = leistungspruefungList.get(leistungspruefungList.size() - 1);
        assertThat(testLeistungspruefung.getTyp()).isEqualTo(DEFAULT_TYP);
        assertThat(testLeistungspruefung.getStufe()).isEqualTo(DEFAULT_STUFE);
        assertThat(testLeistungspruefung.getAbgelegtAm()).isEqualTo(DEFAULT_ABGELEGT_AM);
    }

    @Test
    @Transactional
    public void createLeistungspruefungWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = leistungspruefungRepository.findAll().size();

        // Create the Leistungspruefung with an existing ID
        leistungspruefung.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeistungspruefungMockMvc.perform(post("/api/leistungspruefungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leistungspruefung)))
            .andExpect(status().isBadRequest());

        // Validate the Leistungspruefung in the database
        List<Leistungspruefung> leistungspruefungList = leistungspruefungRepository.findAll();
        assertThat(leistungspruefungList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTypIsRequired() throws Exception {
        int databaseSizeBeforeTest = leistungspruefungRepository.findAll().size();
        // set the field null
        leistungspruefung.setTyp(null);

        // Create the Leistungspruefung, which fails.

        restLeistungspruefungMockMvc.perform(post("/api/leistungspruefungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leistungspruefung)))
            .andExpect(status().isBadRequest());

        List<Leistungspruefung> leistungspruefungList = leistungspruefungRepository.findAll();
        assertThat(leistungspruefungList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStufeIsRequired() throws Exception {
        int databaseSizeBeforeTest = leistungspruefungRepository.findAll().size();
        // set the field null
        leistungspruefung.setStufe(null);

        // Create the Leistungspruefung, which fails.

        restLeistungspruefungMockMvc.perform(post("/api/leistungspruefungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leistungspruefung)))
            .andExpect(status().isBadRequest());

        List<Leistungspruefung> leistungspruefungList = leistungspruefungRepository.findAll();
        assertThat(leistungspruefungList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAbgelegtAmIsRequired() throws Exception {
        int databaseSizeBeforeTest = leistungspruefungRepository.findAll().size();
        // set the field null
        leistungspruefung.setAbgelegtAm(null);

        // Create the Leistungspruefung, which fails.

        restLeistungspruefungMockMvc.perform(post("/api/leistungspruefungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leistungspruefung)))
            .andExpect(status().isBadRequest());

        List<Leistungspruefung> leistungspruefungList = leistungspruefungRepository.findAll();
        assertThat(leistungspruefungList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLeistungspruefungs() throws Exception {
        // Initialize the database
        leistungspruefungRepository.saveAndFlush(leistungspruefung);

        // Get all the leistungspruefungList
        restLeistungspruefungMockMvc.perform(get("/api/leistungspruefungs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leistungspruefung.getId().intValue())))
            .andExpect(jsonPath("$.[*].typ").value(hasItem(DEFAULT_TYP.toString())))
            .andExpect(jsonPath("$.[*].stufe").value(hasItem(DEFAULT_STUFE)))
            .andExpect(jsonPath("$.[*].abgelegtAm").value(hasItem(DEFAULT_ABGELEGT_AM.toString())));
    }

    @Test
    @Transactional
    public void getLeistungspruefung() throws Exception {
        // Initialize the database
        leistungspruefungRepository.saveAndFlush(leistungspruefung);

        // Get the leistungspruefung
        restLeistungspruefungMockMvc.perform(get("/api/leistungspruefungs/{id}", leistungspruefung.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(leistungspruefung.getId().intValue()))
            .andExpect(jsonPath("$.typ").value(DEFAULT_TYP.toString()))
            .andExpect(jsonPath("$.stufe").value(DEFAULT_STUFE))
            .andExpect(jsonPath("$.abgelegtAm").value(DEFAULT_ABGELEGT_AM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLeistungspruefung() throws Exception {
        // Get the leistungspruefung
        restLeistungspruefungMockMvc.perform(get("/api/leistungspruefungs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLeistungspruefung() throws Exception {
        // Initialize the database
        leistungspruefungRepository.saveAndFlush(leistungspruefung);
        int databaseSizeBeforeUpdate = leistungspruefungRepository.findAll().size();

        // Update the leistungspruefung
        Leistungspruefung updatedLeistungspruefung = leistungspruefungRepository.findOne(leistungspruefung.getId());
        updatedLeistungspruefung
            .typ(UPDATED_TYP)
            .stufe(UPDATED_STUFE)
            .abgelegtAm(UPDATED_ABGELEGT_AM);

        restLeistungspruefungMockMvc.perform(put("/api/leistungspruefungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLeistungspruefung)))
            .andExpect(status().isOk());

        // Validate the Leistungspruefung in the database
        List<Leistungspruefung> leistungspruefungList = leistungspruefungRepository.findAll();
        assertThat(leistungspruefungList).hasSize(databaseSizeBeforeUpdate);
        Leistungspruefung testLeistungspruefung = leistungspruefungList.get(leistungspruefungList.size() - 1);
        assertThat(testLeistungspruefung.getTyp()).isEqualTo(UPDATED_TYP);
        assertThat(testLeistungspruefung.getStufe()).isEqualTo(UPDATED_STUFE);
        assertThat(testLeistungspruefung.getAbgelegtAm()).isEqualTo(UPDATED_ABGELEGT_AM);
    }

    @Test
    @Transactional
    public void updateNonExistingLeistungspruefung() throws Exception {
        int databaseSizeBeforeUpdate = leistungspruefungRepository.findAll().size();

        // Create the Leistungspruefung

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLeistungspruefungMockMvc.perform(put("/api/leistungspruefungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leistungspruefung)))
            .andExpect(status().isCreated());

        // Validate the Leistungspruefung in the database
        List<Leistungspruefung> leistungspruefungList = leistungspruefungRepository.findAll();
        assertThat(leistungspruefungList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLeistungspruefung() throws Exception {
        // Initialize the database
        leistungspruefungRepository.saveAndFlush(leistungspruefung);
        int databaseSizeBeforeDelete = leistungspruefungRepository.findAll().size();

        // Get the leistungspruefung
        restLeistungspruefungMockMvc.perform(delete("/api/leistungspruefungs/{id}", leistungspruefung.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Leistungspruefung> leistungspruefungList = leistungspruefungRepository.findAll();
        assertThat(leistungspruefungList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Leistungspruefung.class);
        Leistungspruefung leistungspruefung1 = new Leistungspruefung();
        leistungspruefung1.setId(1L);
        Leistungspruefung leistungspruefung2 = new Leistungspruefung();
        leistungspruefung2.setId(leistungspruefung1.getId());
        assertThat(leistungspruefung1).isEqualTo(leistungspruefung2);
        leistungspruefung2.setId(2L);
        assertThat(leistungspruefung1).isNotEqualTo(leistungspruefung2);
        leistungspruefung1.setId(null);
        assertThat(leistungspruefung1).isNotEqualTo(leistungspruefung2);
    }
}
