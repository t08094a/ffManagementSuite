package de.leif.ffmanagementsuite.web.rest;

import de.leif.ffmanagementsuite.FfManagementSuiteApp;

import de.leif.ffmanagementsuite.domain.Wartung;
import de.leif.ffmanagementsuite.repository.WartungRepository;
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
 * Test class for the WartungResource REST controller.
 *
 * @see WartungResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FfManagementSuiteApp.class)
public class WartungResourceIntTest {

    private static final String DEFAULT_BEZEICHNUNG = "AAAAAAAAAA";
    private static final String UPDATED_BEZEICHNUNG = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BEGINN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BEGINN = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LETZTE_WARTUNG = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LETZTE_WARTUNG = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_INTERVALL_WERT = 1;
    private static final Integer UPDATED_INTERVALL_WERT = 2;

    private static final IntervallEinheit DEFAULT_INTERVALL_EINHEIT = IntervallEinheit.MONAT;
    private static final IntervallEinheit UPDATED_INTERVALL_EINHEIT = IntervallEinheit.JAHR;

    private static final Integer DEFAULT_KOSTEN = 0;
    private static final Integer UPDATED_KOSTEN = 1;

    @Autowired
    private WartungRepository wartungRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWartungMockMvc;

    private Wartung wartung;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WartungResource wartungResource = new WartungResource(wartungRepository);
        this.restWartungMockMvc = MockMvcBuilders.standaloneSetup(wartungResource)
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
    public static Wartung createEntity(EntityManager em) {
        Wartung wartung = new Wartung()
            .bezeichnung(DEFAULT_BEZEICHNUNG)
            .beginn(DEFAULT_BEGINN)
            .letzteWartung(DEFAULT_LETZTE_WARTUNG)
            .intervallWert(DEFAULT_INTERVALL_WERT)
            .intervallEinheit(DEFAULT_INTERVALL_EINHEIT)
            .kosten(DEFAULT_KOSTEN);
        return wartung;
    }

    @Before
    public void initTest() {
        wartung = createEntity(em);
    }

    @Test
    @Transactional
    public void createWartung() throws Exception {
        int databaseSizeBeforeCreate = wartungRepository.findAll().size();

        // Create the Wartung
        restWartungMockMvc.perform(post("/api/wartungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wartung)))
            .andExpect(status().isCreated());

        // Validate the Wartung in the database
        List<Wartung> wartungList = wartungRepository.findAll();
        assertThat(wartungList).hasSize(databaseSizeBeforeCreate + 1);
        Wartung testWartung = wartungList.get(wartungList.size() - 1);
        assertThat(testWartung.getBezeichnung()).isEqualTo(DEFAULT_BEZEICHNUNG);
        assertThat(testWartung.getBeginn()).isEqualTo(DEFAULT_BEGINN);
        assertThat(testWartung.getLetzteWartung()).isEqualTo(DEFAULT_LETZTE_WARTUNG);
        assertThat(testWartung.getIntervallWert()).isEqualTo(DEFAULT_INTERVALL_WERT);
        assertThat(testWartung.getIntervallEinheit()).isEqualTo(DEFAULT_INTERVALL_EINHEIT);
        assertThat(testWartung.getKosten()).isEqualTo(DEFAULT_KOSTEN);
    }

    @Test
    @Transactional
    public void createWartungWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = wartungRepository.findAll().size();

        // Create the Wartung with an existing ID
        wartung.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWartungMockMvc.perform(post("/api/wartungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wartung)))
            .andExpect(status().isBadRequest());

        // Validate the Wartung in the database
        List<Wartung> wartungList = wartungRepository.findAll();
        assertThat(wartungList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkBezeichnungIsRequired() throws Exception {
        int databaseSizeBeforeTest = wartungRepository.findAll().size();
        // set the field null
        wartung.setBezeichnung(null);

        // Create the Wartung, which fails.

        restWartungMockMvc.perform(post("/api/wartungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wartung)))
            .andExpect(status().isBadRequest());

        List<Wartung> wartungList = wartungRepository.findAll();
        assertThat(wartungList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBeginnIsRequired() throws Exception {
        int databaseSizeBeforeTest = wartungRepository.findAll().size();
        // set the field null
        wartung.setBeginn(null);

        // Create the Wartung, which fails.

        restWartungMockMvc.perform(post("/api/wartungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wartung)))
            .andExpect(status().isBadRequest());

        List<Wartung> wartungList = wartungRepository.findAll();
        assertThat(wartungList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIntervallWertIsRequired() throws Exception {
        int databaseSizeBeforeTest = wartungRepository.findAll().size();
        // set the field null
        wartung.setIntervallWert(null);

        // Create the Wartung, which fails.

        restWartungMockMvc.perform(post("/api/wartungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wartung)))
            .andExpect(status().isBadRequest());

        List<Wartung> wartungList = wartungRepository.findAll();
        assertThat(wartungList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIntervallEinheitIsRequired() throws Exception {
        int databaseSizeBeforeTest = wartungRepository.findAll().size();
        // set the field null
        wartung.setIntervallEinheit(null);

        // Create the Wartung, which fails.

        restWartungMockMvc.perform(post("/api/wartungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wartung)))
            .andExpect(status().isBadRequest());

        List<Wartung> wartungList = wartungRepository.findAll();
        assertThat(wartungList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKostenIsRequired() throws Exception {
        int databaseSizeBeforeTest = wartungRepository.findAll().size();
        // set the field null
        wartung.setKosten(null);

        // Create the Wartung, which fails.

        restWartungMockMvc.perform(post("/api/wartungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wartung)))
            .andExpect(status().isBadRequest());

        List<Wartung> wartungList = wartungRepository.findAll();
        assertThat(wartungList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWartungs() throws Exception {
        // Initialize the database
        wartungRepository.saveAndFlush(wartung);

        // Get all the wartungList
        restWartungMockMvc.perform(get("/api/wartungs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wartung.getId().intValue())))
            .andExpect(jsonPath("$.[*].bezeichnung").value(hasItem(DEFAULT_BEZEICHNUNG.toString())))
            .andExpect(jsonPath("$.[*].beginn").value(hasItem(DEFAULT_BEGINN.toString())))
            .andExpect(jsonPath("$.[*].letzteWartung").value(hasItem(DEFAULT_LETZTE_WARTUNG.toString())))
            .andExpect(jsonPath("$.[*].intervallWert").value(hasItem(DEFAULT_INTERVALL_WERT)))
            .andExpect(jsonPath("$.[*].intervallEinheit").value(hasItem(DEFAULT_INTERVALL_EINHEIT.toString())))
            .andExpect(jsonPath("$.[*].kosten").value(hasItem(DEFAULT_KOSTEN)));
    }

    @Test
    @Transactional
    public void getWartung() throws Exception {
        // Initialize the database
        wartungRepository.saveAndFlush(wartung);

        // Get the wartung
        restWartungMockMvc.perform(get("/api/wartungs/{id}", wartung.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(wartung.getId().intValue()))
            .andExpect(jsonPath("$.bezeichnung").value(DEFAULT_BEZEICHNUNG.toString()))
            .andExpect(jsonPath("$.beginn").value(DEFAULT_BEGINN.toString()))
            .andExpect(jsonPath("$.letzteWartung").value(DEFAULT_LETZTE_WARTUNG.toString()))
            .andExpect(jsonPath("$.intervallWert").value(DEFAULT_INTERVALL_WERT))
            .andExpect(jsonPath("$.intervallEinheit").value(DEFAULT_INTERVALL_EINHEIT.toString()))
            .andExpect(jsonPath("$.kosten").value(DEFAULT_KOSTEN));
    }

    @Test
    @Transactional
    public void getNonExistingWartung() throws Exception {
        // Get the wartung
        restWartungMockMvc.perform(get("/api/wartungs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWartung() throws Exception {
        // Initialize the database
        wartungRepository.saveAndFlush(wartung);
        int databaseSizeBeforeUpdate = wartungRepository.findAll().size();

        // Update the wartung
        Wartung updatedWartung = wartungRepository.findOne(wartung.getId());
        updatedWartung
            .bezeichnung(UPDATED_BEZEICHNUNG)
            .beginn(UPDATED_BEGINN)
            .letzteWartung(UPDATED_LETZTE_WARTUNG)
            .intervallWert(UPDATED_INTERVALL_WERT)
            .intervallEinheit(UPDATED_INTERVALL_EINHEIT)
            .kosten(UPDATED_KOSTEN);

        restWartungMockMvc.perform(put("/api/wartungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWartung)))
            .andExpect(status().isOk());

        // Validate the Wartung in the database
        List<Wartung> wartungList = wartungRepository.findAll();
        assertThat(wartungList).hasSize(databaseSizeBeforeUpdate);
        Wartung testWartung = wartungList.get(wartungList.size() - 1);
        assertThat(testWartung.getBezeichnung()).isEqualTo(UPDATED_BEZEICHNUNG);
        assertThat(testWartung.getBeginn()).isEqualTo(UPDATED_BEGINN);
        assertThat(testWartung.getLetzteWartung()).isEqualTo(UPDATED_LETZTE_WARTUNG);
        assertThat(testWartung.getIntervallWert()).isEqualTo(UPDATED_INTERVALL_WERT);
        assertThat(testWartung.getIntervallEinheit()).isEqualTo(UPDATED_INTERVALL_EINHEIT);
        assertThat(testWartung.getKosten()).isEqualTo(UPDATED_KOSTEN);
    }

    @Test
    @Transactional
    public void updateNonExistingWartung() throws Exception {
        int databaseSizeBeforeUpdate = wartungRepository.findAll().size();

        // Create the Wartung

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWartungMockMvc.perform(put("/api/wartungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wartung)))
            .andExpect(status().isCreated());

        // Validate the Wartung in the database
        List<Wartung> wartungList = wartungRepository.findAll();
        assertThat(wartungList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteWartung() throws Exception {
        // Initialize the database
        wartungRepository.saveAndFlush(wartung);
        int databaseSizeBeforeDelete = wartungRepository.findAll().size();

        // Get the wartung
        restWartungMockMvc.perform(delete("/api/wartungs/{id}", wartung.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Wartung> wartungList = wartungRepository.findAll();
        assertThat(wartungList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Wartung.class);
        Wartung wartung1 = new Wartung();
        wartung1.setId(1L);
        Wartung wartung2 = new Wartung();
        wartung2.setId(wartung1.getId());
        assertThat(wartung1).isEqualTo(wartung2);
        wartung2.setId(2L);
        assertThat(wartung1).isNotEqualTo(wartung2);
        wartung1.setId(null);
        assertThat(wartung1).isNotEqualTo(wartung2);
    }
}
