package de.leif.ffmanagementsuite.web.rest;

import de.leif.ffmanagementsuite.FfManagementSuiteApp;

import de.leif.ffmanagementsuite.domain.DurchfuehrungWartung;
import de.leif.ffmanagementsuite.repository.DurchfuehrungWartungRepository;
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
 * Test class for the DurchfuehrungWartungResource REST controller.
 *
 * @see DurchfuehrungWartungResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FfManagementSuiteApp.class)
public class DurchfuehrungWartungResourceIntTest {

    private static final LocalDate DEFAULT_DATUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUM = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_KOSTEN = 1;
    private static final Integer UPDATED_KOSTEN = 2;

    @Autowired
    private DurchfuehrungWartungRepository durchfuehrungWartungRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDurchfuehrungWartungMockMvc;

    private DurchfuehrungWartung durchfuehrungWartung;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DurchfuehrungWartungResource durchfuehrungWartungResource = new DurchfuehrungWartungResource(durchfuehrungWartungRepository);
        this.restDurchfuehrungWartungMockMvc = MockMvcBuilders.standaloneSetup(durchfuehrungWartungResource)
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
    public static DurchfuehrungWartung createEntity(EntityManager em) {
        DurchfuehrungWartung durchfuehrungWartung = new DurchfuehrungWartung()
            .datum(DEFAULT_DATUM)
            .kosten(DEFAULT_KOSTEN);
        return durchfuehrungWartung;
    }

    @Before
    public void initTest() {
        durchfuehrungWartung = createEntity(em);
    }

    @Test
    @Transactional
    public void createDurchfuehrungWartung() throws Exception {
        int databaseSizeBeforeCreate = durchfuehrungWartungRepository.findAll().size();

        // Create the DurchfuehrungWartung
        restDurchfuehrungWartungMockMvc.perform(post("/api/durchfuehrung-wartungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(durchfuehrungWartung)))
            .andExpect(status().isCreated());

        // Validate the DurchfuehrungWartung in the database
        List<DurchfuehrungWartung> durchfuehrungWartungList = durchfuehrungWartungRepository.findAll();
        assertThat(durchfuehrungWartungList).hasSize(databaseSizeBeforeCreate + 1);
        DurchfuehrungWartung testDurchfuehrungWartung = durchfuehrungWartungList.get(durchfuehrungWartungList.size() - 1);
        assertThat(testDurchfuehrungWartung.getDatum()).isEqualTo(DEFAULT_DATUM);
        assertThat(testDurchfuehrungWartung.getKosten()).isEqualTo(DEFAULT_KOSTEN);
    }

    @Test
    @Transactional
    public void createDurchfuehrungWartungWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = durchfuehrungWartungRepository.findAll().size();

        // Create the DurchfuehrungWartung with an existing ID
        durchfuehrungWartung.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDurchfuehrungWartungMockMvc.perform(post("/api/durchfuehrung-wartungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(durchfuehrungWartung)))
            .andExpect(status().isBadRequest());

        // Validate the DurchfuehrungWartung in the database
        List<DurchfuehrungWartung> durchfuehrungWartungList = durchfuehrungWartungRepository.findAll();
        assertThat(durchfuehrungWartungList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDatumIsRequired() throws Exception {
        int databaseSizeBeforeTest = durchfuehrungWartungRepository.findAll().size();
        // set the field null
        durchfuehrungWartung.setDatum(null);

        // Create the DurchfuehrungWartung, which fails.

        restDurchfuehrungWartungMockMvc.perform(post("/api/durchfuehrung-wartungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(durchfuehrungWartung)))
            .andExpect(status().isBadRequest());

        List<DurchfuehrungWartung> durchfuehrungWartungList = durchfuehrungWartungRepository.findAll();
        assertThat(durchfuehrungWartungList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDurchfuehrungWartungs() throws Exception {
        // Initialize the database
        durchfuehrungWartungRepository.saveAndFlush(durchfuehrungWartung);

        // Get all the durchfuehrungWartungList
        restDurchfuehrungWartungMockMvc.perform(get("/api/durchfuehrung-wartungs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(durchfuehrungWartung.getId().intValue())))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM.toString())))
            .andExpect(jsonPath("$.[*].kosten").value(hasItem(DEFAULT_KOSTEN)));
    }

    @Test
    @Transactional
    public void getDurchfuehrungWartung() throws Exception {
        // Initialize the database
        durchfuehrungWartungRepository.saveAndFlush(durchfuehrungWartung);

        // Get the durchfuehrungWartung
        restDurchfuehrungWartungMockMvc.perform(get("/api/durchfuehrung-wartungs/{id}", durchfuehrungWartung.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(durchfuehrungWartung.getId().intValue()))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM.toString()))
            .andExpect(jsonPath("$.kosten").value(DEFAULT_KOSTEN));
    }

    @Test
    @Transactional
    public void getNonExistingDurchfuehrungWartung() throws Exception {
        // Get the durchfuehrungWartung
        restDurchfuehrungWartungMockMvc.perform(get("/api/durchfuehrung-wartungs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDurchfuehrungWartung() throws Exception {
        // Initialize the database
        durchfuehrungWartungRepository.saveAndFlush(durchfuehrungWartung);
        int databaseSizeBeforeUpdate = durchfuehrungWartungRepository.findAll().size();

        // Update the durchfuehrungWartung
        DurchfuehrungWartung updatedDurchfuehrungWartung = durchfuehrungWartungRepository.findOne(durchfuehrungWartung.getId());
        updatedDurchfuehrungWartung
            .datum(UPDATED_DATUM)
            .kosten(UPDATED_KOSTEN);

        restDurchfuehrungWartungMockMvc.perform(put("/api/durchfuehrung-wartungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDurchfuehrungWartung)))
            .andExpect(status().isOk());

        // Validate the DurchfuehrungWartung in the database
        List<DurchfuehrungWartung> durchfuehrungWartungList = durchfuehrungWartungRepository.findAll();
        assertThat(durchfuehrungWartungList).hasSize(databaseSizeBeforeUpdate);
        DurchfuehrungWartung testDurchfuehrungWartung = durchfuehrungWartungList.get(durchfuehrungWartungList.size() - 1);
        assertThat(testDurchfuehrungWartung.getDatum()).isEqualTo(UPDATED_DATUM);
        assertThat(testDurchfuehrungWartung.getKosten()).isEqualTo(UPDATED_KOSTEN);
    }

    @Test
    @Transactional
    public void updateNonExistingDurchfuehrungWartung() throws Exception {
        int databaseSizeBeforeUpdate = durchfuehrungWartungRepository.findAll().size();

        // Create the DurchfuehrungWartung

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDurchfuehrungWartungMockMvc.perform(put("/api/durchfuehrung-wartungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(durchfuehrungWartung)))
            .andExpect(status().isCreated());

        // Validate the DurchfuehrungWartung in the database
        List<DurchfuehrungWartung> durchfuehrungWartungList = durchfuehrungWartungRepository.findAll();
        assertThat(durchfuehrungWartungList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDurchfuehrungWartung() throws Exception {
        // Initialize the database
        durchfuehrungWartungRepository.saveAndFlush(durchfuehrungWartung);
        int databaseSizeBeforeDelete = durchfuehrungWartungRepository.findAll().size();

        // Get the durchfuehrungWartung
        restDurchfuehrungWartungMockMvc.perform(delete("/api/durchfuehrung-wartungs/{id}", durchfuehrungWartung.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DurchfuehrungWartung> durchfuehrungWartungList = durchfuehrungWartungRepository.findAll();
        assertThat(durchfuehrungWartungList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DurchfuehrungWartung.class);
        DurchfuehrungWartung durchfuehrungWartung1 = new DurchfuehrungWartung();
        durchfuehrungWartung1.setId(1L);
        DurchfuehrungWartung durchfuehrungWartung2 = new DurchfuehrungWartung();
        durchfuehrungWartung2.setId(durchfuehrungWartung1.getId());
        assertThat(durchfuehrungWartung1).isEqualTo(durchfuehrungWartung2);
        durchfuehrungWartung2.setId(2L);
        assertThat(durchfuehrungWartung1).isNotEqualTo(durchfuehrungWartung2);
        durchfuehrungWartung1.setId(null);
        assertThat(durchfuehrungWartung1).isNotEqualTo(durchfuehrungWartung2);
    }
}
