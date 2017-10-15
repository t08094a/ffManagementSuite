package de.leif.ffmanagementsuite.web.rest;

import de.leif.ffmanagementsuite.FfManagementSuiteApp;

import de.leif.ffmanagementsuite.domain.Pruefung;
import de.leif.ffmanagementsuite.repository.PruefungRepository;
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

import de.leif.ffmanagementsuite.domain.enumeration.IntervallEinheit;
/**
 * Test class for the PruefungResource REST controller.
 *
 * @see PruefungResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FfManagementSuiteApp.class)
public class PruefungResourceIntTest {

    private static final String DEFAULT_BEZEICHNUNG = "AAAAAAAAAA";
    private static final String UPDATED_BEZEICHNUNG = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BEGINN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BEGINN = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LETZTE_PRUEFUNG = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LETZTE_PRUEFUNG = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_INTERVALL_WERT = 1;
    private static final Integer UPDATED_INTERVALL_WERT = 2;

    private static final IntervallEinheit DEFAULT_INTERVALL_EINHEIT = IntervallEinheit.MONAT;
    private static final IntervallEinheit UPDATED_INTERVALL_EINHEIT = IntervallEinheit.JAHR;

    private static final Integer DEFAULT_KOSTEN = 0;
    private static final Integer UPDATED_KOSTEN = 1;

    @Autowired
    private PruefungRepository pruefungRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPruefungMockMvc;

    private Pruefung pruefung;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PruefungResource pruefungResource = new PruefungResource(pruefungRepository);
        this.restPruefungMockMvc = MockMvcBuilders.standaloneSetup(pruefungResource)
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
    public static Pruefung createEntity(EntityManager em) {
        Pruefung pruefung = new Pruefung()
            .bezeichnung(DEFAULT_BEZEICHNUNG)
            .beginn(DEFAULT_BEGINN)
            .letztePruefung(DEFAULT_LETZTE_PRUEFUNG)
            .intervallWert(DEFAULT_INTERVALL_WERT)
            .intervallEinheit(DEFAULT_INTERVALL_EINHEIT)
            .kosten(DEFAULT_KOSTEN);
        return pruefung;
    }

    @Before
    public void initTest() {
        pruefung = createEntity(em);
    }

    @Test
    @Transactional
    public void createPruefung() throws Exception {
        int databaseSizeBeforeCreate = pruefungRepository.findAll().size();

        // Create the Pruefung
        restPruefungMockMvc.perform(post("/api/pruefungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pruefung)))
            .andExpect(status().isCreated());

        // Validate the Pruefung in the database
        List<Pruefung> pruefungList = pruefungRepository.findAll();
        assertThat(pruefungList).hasSize(databaseSizeBeforeCreate + 1);
        Pruefung testPruefung = pruefungList.get(pruefungList.size() - 1);
        assertThat(testPruefung.getBezeichnung()).isEqualTo(DEFAULT_BEZEICHNUNG);
        assertThat(testPruefung.getBeginn()).isEqualTo(DEFAULT_BEGINN);
        assertThat(testPruefung.getLetztePruefung()).isEqualTo(DEFAULT_LETZTE_PRUEFUNG);
        assertThat(testPruefung.getIntervallWert()).isEqualTo(DEFAULT_INTERVALL_WERT);
        assertThat(testPruefung.getIntervallEinheit()).isEqualTo(DEFAULT_INTERVALL_EINHEIT);
        assertThat(testPruefung.getKosten()).isEqualTo(DEFAULT_KOSTEN);
    }

    @Test
    @Transactional
    public void createPruefungWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pruefungRepository.findAll().size();

        // Create the Pruefung with an existing ID
        pruefung.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPruefungMockMvc.perform(post("/api/pruefungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pruefung)))
            .andExpect(status().isBadRequest());

        // Validate the Pruefung in the database
        List<Pruefung> pruefungList = pruefungRepository.findAll();
        assertThat(pruefungList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkBezeichnungIsRequired() throws Exception {
        int databaseSizeBeforeTest = pruefungRepository.findAll().size();
        // set the field null
        pruefung.setBezeichnung(null);

        // Create the Pruefung, which fails.

        restPruefungMockMvc.perform(post("/api/pruefungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pruefung)))
            .andExpect(status().isBadRequest());

        List<Pruefung> pruefungList = pruefungRepository.findAll();
        assertThat(pruefungList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBeginnIsRequired() throws Exception {
        int databaseSizeBeforeTest = pruefungRepository.findAll().size();
        // set the field null
        pruefung.setBeginn(null);

        // Create the Pruefung, which fails.

        restPruefungMockMvc.perform(post("/api/pruefungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pruefung)))
            .andExpect(status().isBadRequest());

        List<Pruefung> pruefungList = pruefungRepository.findAll();
        assertThat(pruefungList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIntervallWertIsRequired() throws Exception {
        int databaseSizeBeforeTest = pruefungRepository.findAll().size();
        // set the field null
        pruefung.setIntervallWert(null);

        // Create the Pruefung, which fails.

        restPruefungMockMvc.perform(post("/api/pruefungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pruefung)))
            .andExpect(status().isBadRequest());

        List<Pruefung> pruefungList = pruefungRepository.findAll();
        assertThat(pruefungList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIntervallEinheitIsRequired() throws Exception {
        int databaseSizeBeforeTest = pruefungRepository.findAll().size();
        // set the field null
        pruefung.setIntervallEinheit(null);

        // Create the Pruefung, which fails.

        restPruefungMockMvc.perform(post("/api/pruefungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pruefung)))
            .andExpect(status().isBadRequest());

        List<Pruefung> pruefungList = pruefungRepository.findAll();
        assertThat(pruefungList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKostenIsRequired() throws Exception {
        int databaseSizeBeforeTest = pruefungRepository.findAll().size();
        // set the field null
        pruefung.setKosten(null);

        // Create the Pruefung, which fails.

        restPruefungMockMvc.perform(post("/api/pruefungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pruefung)))
            .andExpect(status().isBadRequest());

        List<Pruefung> pruefungList = pruefungRepository.findAll();
        assertThat(pruefungList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPruefungs() throws Exception {
        // Initialize the database
        pruefungRepository.saveAndFlush(pruefung);

        // Get all the pruefungList
        restPruefungMockMvc.perform(get("/api/pruefungs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pruefung.getId().intValue())))
            .andExpect(jsonPath("$.[*].bezeichnung").value(hasItem(DEFAULT_BEZEICHNUNG.toString())))
            .andExpect(jsonPath("$.[*].beginn").value(hasItem(DEFAULT_BEGINN.toString())))
            .andExpect(jsonPath("$.[*].letztePruefung").value(hasItem(DEFAULT_LETZTE_PRUEFUNG.toString())))
            .andExpect(jsonPath("$.[*].intervallWert").value(hasItem(DEFAULT_INTERVALL_WERT)))
            .andExpect(jsonPath("$.[*].intervallEinheit").value(hasItem(DEFAULT_INTERVALL_EINHEIT.toString())))
            .andExpect(jsonPath("$.[*].kosten").value(hasItem(DEFAULT_KOSTEN)));
    }

    @Test
    @Transactional
    public void getPruefung() throws Exception {
        // Initialize the database
        pruefungRepository.saveAndFlush(pruefung);

        // Get the pruefung
        restPruefungMockMvc.perform(get("/api/pruefungs/{id}", pruefung.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pruefung.getId().intValue()))
            .andExpect(jsonPath("$.bezeichnung").value(DEFAULT_BEZEICHNUNG.toString()))
            .andExpect(jsonPath("$.beginn").value(DEFAULT_BEGINN.toString()))
            .andExpect(jsonPath("$.letztePruefung").value(DEFAULT_LETZTE_PRUEFUNG.toString()))
            .andExpect(jsonPath("$.intervallWert").value(DEFAULT_INTERVALL_WERT))
            .andExpect(jsonPath("$.intervallEinheit").value(DEFAULT_INTERVALL_EINHEIT.toString()))
            .andExpect(jsonPath("$.kosten").value(DEFAULT_KOSTEN));
    }

    @Test
    @Transactional
    public void getNonExistingPruefung() throws Exception {
        // Get the pruefung
        restPruefungMockMvc.perform(get("/api/pruefungs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePruefung() throws Exception {
        // Initialize the database
        pruefungRepository.saveAndFlush(pruefung);
        int databaseSizeBeforeUpdate = pruefungRepository.findAll().size();

        // Update the pruefung
        Pruefung updatedPruefung = pruefungRepository.findOne(pruefung.getId());
        updatedPruefung
            .bezeichnung(UPDATED_BEZEICHNUNG)
            .beginn(UPDATED_BEGINN)
            .letztePruefung(UPDATED_LETZTE_PRUEFUNG)
            .intervallWert(UPDATED_INTERVALL_WERT)
            .intervallEinheit(UPDATED_INTERVALL_EINHEIT)
            .kosten(UPDATED_KOSTEN);

        restPruefungMockMvc.perform(put("/api/pruefungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPruefung)))
            .andExpect(status().isOk());

        // Validate the Pruefung in the database
        List<Pruefung> pruefungList = pruefungRepository.findAll();
        assertThat(pruefungList).hasSize(databaseSizeBeforeUpdate);
        Pruefung testPruefung = pruefungList.get(pruefungList.size() - 1);
        assertThat(testPruefung.getBezeichnung()).isEqualTo(UPDATED_BEZEICHNUNG);
        assertThat(testPruefung.getBeginn()).isEqualTo(UPDATED_BEGINN);
        assertThat(testPruefung.getLetztePruefung()).isEqualTo(UPDATED_LETZTE_PRUEFUNG);
        assertThat(testPruefung.getIntervallWert()).isEqualTo(UPDATED_INTERVALL_WERT);
        assertThat(testPruefung.getIntervallEinheit()).isEqualTo(UPDATED_INTERVALL_EINHEIT);
        assertThat(testPruefung.getKosten()).isEqualTo(UPDATED_KOSTEN);
    }

    @Test
    @Transactional
    public void updateNonExistingPruefung() throws Exception {
        int databaseSizeBeforeUpdate = pruefungRepository.findAll().size();

        // Create the Pruefung

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPruefungMockMvc.perform(put("/api/pruefungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pruefung)))
            .andExpect(status().isCreated());

        // Validate the Pruefung in the database
        List<Pruefung> pruefungList = pruefungRepository.findAll();
        assertThat(pruefungList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePruefung() throws Exception {
        // Initialize the database
        pruefungRepository.saveAndFlush(pruefung);
        int databaseSizeBeforeDelete = pruefungRepository.findAll().size();

        // Get the pruefung
        restPruefungMockMvc.perform(delete("/api/pruefungs/{id}", pruefung.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Pruefung> pruefungList = pruefungRepository.findAll();
        assertThat(pruefungList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pruefung.class);
        Pruefung pruefung1 = new Pruefung();
        pruefung1.setId(1L);
        Pruefung pruefung2 = new Pruefung();
        pruefung2.setId(pruefung1.getId());
        assertThat(pruefung1).isEqualTo(pruefung2);
        pruefung2.setId(2L);
        assertThat(pruefung1).isNotEqualTo(pruefung2);
        pruefung1.setId(null);
        assertThat(pruefung1).isNotEqualTo(pruefung2);
    }
}
