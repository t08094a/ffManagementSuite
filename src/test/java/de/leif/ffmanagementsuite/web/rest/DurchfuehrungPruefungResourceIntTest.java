package de.leif.ffmanagementsuite.web.rest;

import de.leif.ffmanagementsuite.FfManagementSuiteApp;

import de.leif.ffmanagementsuite.domain.DurchfuehrungPruefung;
import de.leif.ffmanagementsuite.repository.DurchfuehrungPruefungRepository;
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
 * Test class for the DurchfuehrungPruefungResource REST controller.
 *
 * @see DurchfuehrungPruefungResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FfManagementSuiteApp.class)
public class DurchfuehrungPruefungResourceIntTest {

    private static final LocalDate DEFAULT_DATUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUM = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_KOSTEN = 1;
    private static final Integer UPDATED_KOSTEN = 2;

    @Autowired
    private DurchfuehrungPruefungRepository durchfuehrungPruefungRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDurchfuehrungPruefungMockMvc;

    private DurchfuehrungPruefung durchfuehrungPruefung;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DurchfuehrungPruefungResource durchfuehrungPruefungResource = new DurchfuehrungPruefungResource(durchfuehrungPruefungRepository);
        this.restDurchfuehrungPruefungMockMvc = MockMvcBuilders.standaloneSetup(durchfuehrungPruefungResource)
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
    public static DurchfuehrungPruefung createEntity(EntityManager em) {
        DurchfuehrungPruefung durchfuehrungPruefung = new DurchfuehrungPruefung()
            .datum(DEFAULT_DATUM)
            .kosten(DEFAULT_KOSTEN);
        return durchfuehrungPruefung;
    }

    @Before
    public void initTest() {
        durchfuehrungPruefung = createEntity(em);
    }

    @Test
    @Transactional
    public void createDurchfuehrungPruefung() throws Exception {
        int databaseSizeBeforeCreate = durchfuehrungPruefungRepository.findAll().size();

        // Create the DurchfuehrungPruefung
        restDurchfuehrungPruefungMockMvc.perform(post("/api/durchfuehrung-pruefungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(durchfuehrungPruefung)))
            .andExpect(status().isCreated());

        // Validate the DurchfuehrungPruefung in the database
        List<DurchfuehrungPruefung> durchfuehrungPruefungList = durchfuehrungPruefungRepository.findAll();
        assertThat(durchfuehrungPruefungList).hasSize(databaseSizeBeforeCreate + 1);
        DurchfuehrungPruefung testDurchfuehrungPruefung = durchfuehrungPruefungList.get(durchfuehrungPruefungList.size() - 1);
        assertThat(testDurchfuehrungPruefung.getDatum()).isEqualTo(DEFAULT_DATUM);
        assertThat(testDurchfuehrungPruefung.getKosten()).isEqualTo(DEFAULT_KOSTEN);
    }

    @Test
    @Transactional
    public void createDurchfuehrungPruefungWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = durchfuehrungPruefungRepository.findAll().size();

        // Create the DurchfuehrungPruefung with an existing ID
        durchfuehrungPruefung.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDurchfuehrungPruefungMockMvc.perform(post("/api/durchfuehrung-pruefungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(durchfuehrungPruefung)))
            .andExpect(status().isBadRequest());

        // Validate the DurchfuehrungPruefung in the database
        List<DurchfuehrungPruefung> durchfuehrungPruefungList = durchfuehrungPruefungRepository.findAll();
        assertThat(durchfuehrungPruefungList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDatumIsRequired() throws Exception {
        int databaseSizeBeforeTest = durchfuehrungPruefungRepository.findAll().size();
        // set the field null
        durchfuehrungPruefung.setDatum(null);

        // Create the DurchfuehrungPruefung, which fails.

        restDurchfuehrungPruefungMockMvc.perform(post("/api/durchfuehrung-pruefungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(durchfuehrungPruefung)))
            .andExpect(status().isBadRequest());

        List<DurchfuehrungPruefung> durchfuehrungPruefungList = durchfuehrungPruefungRepository.findAll();
        assertThat(durchfuehrungPruefungList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDurchfuehrungPruefungs() throws Exception {
        // Initialize the database
        durchfuehrungPruefungRepository.saveAndFlush(durchfuehrungPruefung);

        // Get all the durchfuehrungPruefungList
        restDurchfuehrungPruefungMockMvc.perform(get("/api/durchfuehrung-pruefungs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(durchfuehrungPruefung.getId().intValue())))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM.toString())))
            .andExpect(jsonPath("$.[*].kosten").value(hasItem(DEFAULT_KOSTEN)));
    }

    @Test
    @Transactional
    public void getDurchfuehrungPruefung() throws Exception {
        // Initialize the database
        durchfuehrungPruefungRepository.saveAndFlush(durchfuehrungPruefung);

        // Get the durchfuehrungPruefung
        restDurchfuehrungPruefungMockMvc.perform(get("/api/durchfuehrung-pruefungs/{id}", durchfuehrungPruefung.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(durchfuehrungPruefung.getId().intValue()))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM.toString()))
            .andExpect(jsonPath("$.kosten").value(DEFAULT_KOSTEN));
    }

    @Test
    @Transactional
    public void getNonExistingDurchfuehrungPruefung() throws Exception {
        // Get the durchfuehrungPruefung
        restDurchfuehrungPruefungMockMvc.perform(get("/api/durchfuehrung-pruefungs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDurchfuehrungPruefung() throws Exception {
        // Initialize the database
        durchfuehrungPruefungRepository.saveAndFlush(durchfuehrungPruefung);
        int databaseSizeBeforeUpdate = durchfuehrungPruefungRepository.findAll().size();

        // Update the durchfuehrungPruefung
        DurchfuehrungPruefung updatedDurchfuehrungPruefung = durchfuehrungPruefungRepository.findOne(durchfuehrungPruefung.getId());
        updatedDurchfuehrungPruefung
            .datum(UPDATED_DATUM)
            .kosten(UPDATED_KOSTEN);

        restDurchfuehrungPruefungMockMvc.perform(put("/api/durchfuehrung-pruefungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDurchfuehrungPruefung)))
            .andExpect(status().isOk());

        // Validate the DurchfuehrungPruefung in the database
        List<DurchfuehrungPruefung> durchfuehrungPruefungList = durchfuehrungPruefungRepository.findAll();
        assertThat(durchfuehrungPruefungList).hasSize(databaseSizeBeforeUpdate);
        DurchfuehrungPruefung testDurchfuehrungPruefung = durchfuehrungPruefungList.get(durchfuehrungPruefungList.size() - 1);
        assertThat(testDurchfuehrungPruefung.getDatum()).isEqualTo(UPDATED_DATUM);
        assertThat(testDurchfuehrungPruefung.getKosten()).isEqualTo(UPDATED_KOSTEN);
    }

    @Test
    @Transactional
    public void updateNonExistingDurchfuehrungPruefung() throws Exception {
        int databaseSizeBeforeUpdate = durchfuehrungPruefungRepository.findAll().size();

        // Create the DurchfuehrungPruefung

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDurchfuehrungPruefungMockMvc.perform(put("/api/durchfuehrung-pruefungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(durchfuehrungPruefung)))
            .andExpect(status().isCreated());

        // Validate the DurchfuehrungPruefung in the database
        List<DurchfuehrungPruefung> durchfuehrungPruefungList = durchfuehrungPruefungRepository.findAll();
        assertThat(durchfuehrungPruefungList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDurchfuehrungPruefung() throws Exception {
        // Initialize the database
        durchfuehrungPruefungRepository.saveAndFlush(durchfuehrungPruefung);
        int databaseSizeBeforeDelete = durchfuehrungPruefungRepository.findAll().size();

        // Get the durchfuehrungPruefung
        restDurchfuehrungPruefungMockMvc.perform(delete("/api/durchfuehrung-pruefungs/{id}", durchfuehrungPruefung.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DurchfuehrungPruefung> durchfuehrungPruefungList = durchfuehrungPruefungRepository.findAll();
        assertThat(durchfuehrungPruefungList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DurchfuehrungPruefung.class);
        DurchfuehrungPruefung durchfuehrungPruefung1 = new DurchfuehrungPruefung();
        durchfuehrungPruefung1.setId(1L);
        DurchfuehrungPruefung durchfuehrungPruefung2 = new DurchfuehrungPruefung();
        durchfuehrungPruefung2.setId(durchfuehrungPruefung1.getId());
        assertThat(durchfuehrungPruefung1).isEqualTo(durchfuehrungPruefung2);
        durchfuehrungPruefung2.setId(2L);
        assertThat(durchfuehrungPruefung1).isNotEqualTo(durchfuehrungPruefung2);
        durchfuehrungPruefung1.setId(null);
        assertThat(durchfuehrungPruefung1).isNotEqualTo(durchfuehrungPruefung2);
    }
}
