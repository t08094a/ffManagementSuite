package de.leif.ffmanagementsuite.web.rest;

import de.leif.ffmanagementsuite.FfManagementSuiteApp;

import de.leif.ffmanagementsuite.domain.Fahrzeug;
import de.leif.ffmanagementsuite.repository.FahrzeugRepository;
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
 * Test class for the FahrzeugResource REST controller.
 *
 * @see FahrzeugResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FfManagementSuiteApp.class)
public class FahrzeugResourceIntTest {

    private static final Integer DEFAULT_NUMMER = 1;
    private static final Integer UPDATED_NUMMER = 2;

    private static final LocalDate DEFAULT_ANGESCHAFFT_AM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ANGESCHAFFT_AM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_AUSGEMUSTERT_AM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_AUSGEMUSTERT_AM = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NUMMERNSCHILD = "UJX-K72";
    private static final String UPDATED_NUMMERNSCHILD = "P-09";

    private static final String DEFAULT_FUNKRUFNAME = "04/11";
    private static final String UPDATED_FUNKRUFNAME = "93/25";

    @Autowired
    private FahrzeugRepository fahrzeugRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFahrzeugMockMvc;

    private Fahrzeug fahrzeug;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FahrzeugResource fahrzeugResource = new FahrzeugResource(fahrzeugRepository);
        this.restFahrzeugMockMvc = MockMvcBuilders.standaloneSetup(fahrzeugResource)
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
    public static Fahrzeug createEntity(EntityManager em) {
        Fahrzeug fahrzeug = new Fahrzeug()
            .nummer(DEFAULT_NUMMER)
            .angeschafftAm(DEFAULT_ANGESCHAFFT_AM)
            .ausgemustertAm(DEFAULT_AUSGEMUSTERT_AM)
            .nummernschild(DEFAULT_NUMMERNSCHILD)
            .funkrufname(DEFAULT_FUNKRUFNAME);
        return fahrzeug;
    }

    @Before
    public void initTest() {
        fahrzeug = createEntity(em);
    }

    @Test
    @Transactional
    public void createFahrzeug() throws Exception {
        int databaseSizeBeforeCreate = fahrzeugRepository.findAll().size();

        // Create the Fahrzeug
        restFahrzeugMockMvc.perform(post("/api/fahrzeugs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fahrzeug)))
            .andExpect(status().isCreated());

        // Validate the Fahrzeug in the database
        List<Fahrzeug> fahrzeugList = fahrzeugRepository.findAll();
        assertThat(fahrzeugList).hasSize(databaseSizeBeforeCreate + 1);
        Fahrzeug testFahrzeug = fahrzeugList.get(fahrzeugList.size() - 1);
        assertThat(testFahrzeug.getNummer()).isEqualTo(DEFAULT_NUMMER);
        assertThat(testFahrzeug.getAngeschafftAm()).isEqualTo(DEFAULT_ANGESCHAFFT_AM);
        assertThat(testFahrzeug.getAusgemustertAm()).isEqualTo(DEFAULT_AUSGEMUSTERT_AM);
        assertThat(testFahrzeug.getNummernschild()).isEqualTo(DEFAULT_NUMMERNSCHILD);
        assertThat(testFahrzeug.getFunkrufname()).isEqualTo(DEFAULT_FUNKRUFNAME);
    }

    @Test
    @Transactional
    public void createFahrzeugWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fahrzeugRepository.findAll().size();

        // Create the Fahrzeug with an existing ID
        fahrzeug.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFahrzeugMockMvc.perform(post("/api/fahrzeugs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fahrzeug)))
            .andExpect(status().isBadRequest());

        // Validate the Fahrzeug in the database
        List<Fahrzeug> fahrzeugList = fahrzeugRepository.findAll();
        assertThat(fahrzeugList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNummerIsRequired() throws Exception {
        int databaseSizeBeforeTest = fahrzeugRepository.findAll().size();
        // set the field null
        fahrzeug.setNummer(null);

        // Create the Fahrzeug, which fails.

        restFahrzeugMockMvc.perform(post("/api/fahrzeugs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fahrzeug)))
            .andExpect(status().isBadRequest());

        List<Fahrzeug> fahrzeugList = fahrzeugRepository.findAll();
        assertThat(fahrzeugList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNummernschildIsRequired() throws Exception {
        int databaseSizeBeforeTest = fahrzeugRepository.findAll().size();
        // set the field null
        fahrzeug.setNummernschild(null);

        // Create the Fahrzeug, which fails.

        restFahrzeugMockMvc.perform(post("/api/fahrzeugs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fahrzeug)))
            .andExpect(status().isBadRequest());

        List<Fahrzeug> fahrzeugList = fahrzeugRepository.findAll();
        assertThat(fahrzeugList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFunkrufnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = fahrzeugRepository.findAll().size();
        // set the field null
        fahrzeug.setFunkrufname(null);

        // Create the Fahrzeug, which fails.

        restFahrzeugMockMvc.perform(post("/api/fahrzeugs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fahrzeug)))
            .andExpect(status().isBadRequest());

        List<Fahrzeug> fahrzeugList = fahrzeugRepository.findAll();
        assertThat(fahrzeugList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFahrzeugs() throws Exception {
        // Initialize the database
        fahrzeugRepository.saveAndFlush(fahrzeug);

        // Get all the fahrzeugList
        restFahrzeugMockMvc.perform(get("/api/fahrzeugs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fahrzeug.getId().intValue())))
            .andExpect(jsonPath("$.[*].nummer").value(hasItem(DEFAULT_NUMMER)))
            .andExpect(jsonPath("$.[*].angeschafftAm").value(hasItem(DEFAULT_ANGESCHAFFT_AM.toString())))
            .andExpect(jsonPath("$.[*].ausgemustertAm").value(hasItem(DEFAULT_AUSGEMUSTERT_AM.toString())))
            .andExpect(jsonPath("$.[*].nummernschild").value(hasItem(DEFAULT_NUMMERNSCHILD.toString())))
            .andExpect(jsonPath("$.[*].funkrufname").value(hasItem(DEFAULT_FUNKRUFNAME.toString())));
    }

    @Test
    @Transactional
    public void getFahrzeug() throws Exception {
        // Initialize the database
        fahrzeugRepository.saveAndFlush(fahrzeug);

        // Get the fahrzeug
        restFahrzeugMockMvc.perform(get("/api/fahrzeugs/{id}", fahrzeug.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fahrzeug.getId().intValue()))
            .andExpect(jsonPath("$.nummer").value(DEFAULT_NUMMER))
            .andExpect(jsonPath("$.angeschafftAm").value(DEFAULT_ANGESCHAFFT_AM.toString()))
            .andExpect(jsonPath("$.ausgemustertAm").value(DEFAULT_AUSGEMUSTERT_AM.toString()))
            .andExpect(jsonPath("$.nummernschild").value(DEFAULT_NUMMERNSCHILD.toString()))
            .andExpect(jsonPath("$.funkrufname").value(DEFAULT_FUNKRUFNAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFahrzeug() throws Exception {
        // Get the fahrzeug
        restFahrzeugMockMvc.perform(get("/api/fahrzeugs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFahrzeug() throws Exception {
        // Initialize the database
        fahrzeugRepository.saveAndFlush(fahrzeug);
        int databaseSizeBeforeUpdate = fahrzeugRepository.findAll().size();

        // Update the fahrzeug
        Fahrzeug updatedFahrzeug = fahrzeugRepository.findOne(fahrzeug.getId());
        updatedFahrzeug
            .nummer(UPDATED_NUMMER)
            .angeschafftAm(UPDATED_ANGESCHAFFT_AM)
            .ausgemustertAm(UPDATED_AUSGEMUSTERT_AM)
            .nummernschild(UPDATED_NUMMERNSCHILD)
            .funkrufname(UPDATED_FUNKRUFNAME);

        restFahrzeugMockMvc.perform(put("/api/fahrzeugs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFahrzeug)))
            .andExpect(status().isOk());

        // Validate the Fahrzeug in the database
        List<Fahrzeug> fahrzeugList = fahrzeugRepository.findAll();
        assertThat(fahrzeugList).hasSize(databaseSizeBeforeUpdate);
        Fahrzeug testFahrzeug = fahrzeugList.get(fahrzeugList.size() - 1);
        assertThat(testFahrzeug.getNummer()).isEqualTo(UPDATED_NUMMER);
        assertThat(testFahrzeug.getAngeschafftAm()).isEqualTo(UPDATED_ANGESCHAFFT_AM);
        assertThat(testFahrzeug.getAusgemustertAm()).isEqualTo(UPDATED_AUSGEMUSTERT_AM);
        assertThat(testFahrzeug.getNummernschild()).isEqualTo(UPDATED_NUMMERNSCHILD);
        assertThat(testFahrzeug.getFunkrufname()).isEqualTo(UPDATED_FUNKRUFNAME);
    }

    @Test
    @Transactional
    public void updateNonExistingFahrzeug() throws Exception {
        int databaseSizeBeforeUpdate = fahrzeugRepository.findAll().size();

        // Create the Fahrzeug

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFahrzeugMockMvc.perform(put("/api/fahrzeugs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fahrzeug)))
            .andExpect(status().isCreated());

        // Validate the Fahrzeug in the database
        List<Fahrzeug> fahrzeugList = fahrzeugRepository.findAll();
        assertThat(fahrzeugList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFahrzeug() throws Exception {
        // Initialize the database
        fahrzeugRepository.saveAndFlush(fahrzeug);
        int databaseSizeBeforeDelete = fahrzeugRepository.findAll().size();

        // Get the fahrzeug
        restFahrzeugMockMvc.perform(delete("/api/fahrzeugs/{id}", fahrzeug.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Fahrzeug> fahrzeugList = fahrzeugRepository.findAll();
        assertThat(fahrzeugList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fahrzeug.class);
        Fahrzeug fahrzeug1 = new Fahrzeug();
        fahrzeug1.setId(1L);
        Fahrzeug fahrzeug2 = new Fahrzeug();
        fahrzeug2.setId(fahrzeug1.getId());
        assertThat(fahrzeug1).isEqualTo(fahrzeug2);
        fahrzeug2.setId(2L);
        assertThat(fahrzeug1).isNotEqualTo(fahrzeug2);
        fahrzeug1.setId(null);
        assertThat(fahrzeug1).isNotEqualTo(fahrzeug2);
    }
}
