package de.leif.ffmanagementsuite.web.rest;

import de.leif.ffmanagementsuite.FfManagementSuiteApp;

import de.leif.ffmanagementsuite.domain.Ausbildung;
import de.leif.ffmanagementsuite.repository.AusbildungRepository;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AusbildungResource REST controller.
 *
 * @see AusbildungResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FfManagementSuiteApp.class)
public class AusbildungResourceIntTest {

    private static final LocalDate DEFAULT_BEGINN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BEGINN = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ENDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ENDE = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_ZEUGNIS = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ZEUGNIS = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_ZEUGNIS_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ZEUGNIS_CONTENT_TYPE = "image/png";

    @Autowired
    private AusbildungRepository ausbildungRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAusbildungMockMvc;

    private Ausbildung ausbildung;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AusbildungResource ausbildungResource = new AusbildungResource(ausbildungRepository);
        this.restAusbildungMockMvc = MockMvcBuilders.standaloneSetup(ausbildungResource)
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
    public static Ausbildung createEntity(EntityManager em) {
        Ausbildung ausbildung = new Ausbildung()
            .beginn(DEFAULT_BEGINN)
            .ende(DEFAULT_ENDE)
            .zeugnis(DEFAULT_ZEUGNIS)
            .zeugnisContentType(DEFAULT_ZEUGNIS_CONTENT_TYPE);
        return ausbildung;
    }

    @Before
    public void initTest() {
        ausbildung = createEntity(em);
    }

    @Test
    @Transactional
    public void createAusbildung() throws Exception {
        int databaseSizeBeforeCreate = ausbildungRepository.findAll().size();

        // Create the Ausbildung
        restAusbildungMockMvc.perform(post("/api/ausbildungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ausbildung)))
            .andExpect(status().isCreated());

        // Validate the Ausbildung in the database
        List<Ausbildung> ausbildungList = ausbildungRepository.findAll();
        assertThat(ausbildungList).hasSize(databaseSizeBeforeCreate + 1);
        Ausbildung testAusbildung = ausbildungList.get(ausbildungList.size() - 1);
        assertThat(testAusbildung.getBeginn()).isEqualTo(DEFAULT_BEGINN);
        assertThat(testAusbildung.getEnde()).isEqualTo(DEFAULT_ENDE);
        assertThat(testAusbildung.getZeugnis()).isEqualTo(DEFAULT_ZEUGNIS);
        assertThat(testAusbildung.getZeugnisContentType()).isEqualTo(DEFAULT_ZEUGNIS_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createAusbildungWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ausbildungRepository.findAll().size();

        // Create the Ausbildung with an existing ID
        ausbildung.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAusbildungMockMvc.perform(post("/api/ausbildungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ausbildung)))
            .andExpect(status().isBadRequest());

        // Validate the Ausbildung in the database
        List<Ausbildung> ausbildungList = ausbildungRepository.findAll();
        assertThat(ausbildungList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkBeginnIsRequired() throws Exception {
        int databaseSizeBeforeTest = ausbildungRepository.findAll().size();
        // set the field null
        ausbildung.setBeginn(null);

        // Create the Ausbildung, which fails.

        restAusbildungMockMvc.perform(post("/api/ausbildungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ausbildung)))
            .andExpect(status().isBadRequest());

        List<Ausbildung> ausbildungList = ausbildungRepository.findAll();
        assertThat(ausbildungList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndeIsRequired() throws Exception {
        int databaseSizeBeforeTest = ausbildungRepository.findAll().size();
        // set the field null
        ausbildung.setEnde(null);

        // Create the Ausbildung, which fails.

        restAusbildungMockMvc.perform(post("/api/ausbildungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ausbildung)))
            .andExpect(status().isBadRequest());

        List<Ausbildung> ausbildungList = ausbildungRepository.findAll();
        assertThat(ausbildungList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAusbildungs() throws Exception {
        // Initialize the database
        ausbildungRepository.saveAndFlush(ausbildung);

        // Get all the ausbildungList
        restAusbildungMockMvc.perform(get("/api/ausbildungs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ausbildung.getId().intValue())))
            .andExpect(jsonPath("$.[*].beginn").value(hasItem(DEFAULT_BEGINN.toString())))
            .andExpect(jsonPath("$.[*].ende").value(hasItem(DEFAULT_ENDE.toString())))
            .andExpect(jsonPath("$.[*].zeugnisContentType").value(hasItem(DEFAULT_ZEUGNIS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].zeugnis").value(hasItem(Base64Utils.encodeToString(DEFAULT_ZEUGNIS))));
    }

    @Test
    @Transactional
    public void getAusbildung() throws Exception {
        // Initialize the database
        ausbildungRepository.saveAndFlush(ausbildung);

        // Get the ausbildung
        restAusbildungMockMvc.perform(get("/api/ausbildungs/{id}", ausbildung.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ausbildung.getId().intValue()))
            .andExpect(jsonPath("$.beginn").value(DEFAULT_BEGINN.toString()))
            .andExpect(jsonPath("$.ende").value(DEFAULT_ENDE.toString()))
            .andExpect(jsonPath("$.zeugnisContentType").value(DEFAULT_ZEUGNIS_CONTENT_TYPE))
            .andExpect(jsonPath("$.zeugnis").value(Base64Utils.encodeToString(DEFAULT_ZEUGNIS)));
    }

    @Test
    @Transactional
    public void getNonExistingAusbildung() throws Exception {
        // Get the ausbildung
        restAusbildungMockMvc.perform(get("/api/ausbildungs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAusbildung() throws Exception {
        // Initialize the database
        ausbildungRepository.saveAndFlush(ausbildung);
        int databaseSizeBeforeUpdate = ausbildungRepository.findAll().size();

        // Update the ausbildung
        Ausbildung updatedAusbildung = ausbildungRepository.findOne(ausbildung.getId());
        updatedAusbildung
            .beginn(UPDATED_BEGINN)
            .ende(UPDATED_ENDE)
            .zeugnis(UPDATED_ZEUGNIS)
            .zeugnisContentType(UPDATED_ZEUGNIS_CONTENT_TYPE);

        restAusbildungMockMvc.perform(put("/api/ausbildungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAusbildung)))
            .andExpect(status().isOk());

        // Validate the Ausbildung in the database
        List<Ausbildung> ausbildungList = ausbildungRepository.findAll();
        assertThat(ausbildungList).hasSize(databaseSizeBeforeUpdate);
        Ausbildung testAusbildung = ausbildungList.get(ausbildungList.size() - 1);
        assertThat(testAusbildung.getBeginn()).isEqualTo(UPDATED_BEGINN);
        assertThat(testAusbildung.getEnde()).isEqualTo(UPDATED_ENDE);
        assertThat(testAusbildung.getZeugnis()).isEqualTo(UPDATED_ZEUGNIS);
        assertThat(testAusbildung.getZeugnisContentType()).isEqualTo(UPDATED_ZEUGNIS_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingAusbildung() throws Exception {
        int databaseSizeBeforeUpdate = ausbildungRepository.findAll().size();

        // Create the Ausbildung

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAusbildungMockMvc.perform(put("/api/ausbildungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ausbildung)))
            .andExpect(status().isCreated());

        // Validate the Ausbildung in the database
        List<Ausbildung> ausbildungList = ausbildungRepository.findAll();
        assertThat(ausbildungList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAusbildung() throws Exception {
        // Initialize the database
        ausbildungRepository.saveAndFlush(ausbildung);
        int databaseSizeBeforeDelete = ausbildungRepository.findAll().size();

        // Get the ausbildung
        restAusbildungMockMvc.perform(delete("/api/ausbildungs/{id}", ausbildung.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Ausbildung> ausbildungList = ausbildungRepository.findAll();
        assertThat(ausbildungList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ausbildung.class);
        Ausbildung ausbildung1 = new Ausbildung();
        ausbildung1.setId(1L);
        Ausbildung ausbildung2 = new Ausbildung();
        ausbildung2.setId(ausbildung1.getId());
        assertThat(ausbildung1).isEqualTo(ausbildung2);
        ausbildung2.setId(2L);
        assertThat(ausbildung1).isNotEqualTo(ausbildung2);
        ausbildung1.setId(null);
        assertThat(ausbildung1).isNotEqualTo(ausbildung2);
    }
}
