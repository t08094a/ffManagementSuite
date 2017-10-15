package de.leif.ffmanagementsuite.web.rest;

import de.leif.ffmanagementsuite.FfManagementSuiteApp;

import de.leif.ffmanagementsuite.domain.Verfuegbarkeit;
import de.leif.ffmanagementsuite.repository.VerfuegbarkeitRepository;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.leif.ffmanagementsuite.domain.enumeration.Wocheneinteilung;
import de.leif.ffmanagementsuite.domain.enumeration.Tageszeit;
/**
 * Test class for the VerfuegbarkeitResource REST controller.
 *
 * @see VerfuegbarkeitResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FfManagementSuiteApp.class)
public class VerfuegbarkeitResourceIntTest {

    private static final String DEFAULT_TITEL = "AAAAAAAAAA";
    private static final String UPDATED_TITEL = "BBBBBBBBBB";

    private static final Wocheneinteilung DEFAULT_WOCHENEINTEILUNG = Wocheneinteilung.WOCHENTAG;
    private static final Wocheneinteilung UPDATED_WOCHENEINTEILUNG = Wocheneinteilung.WOCHENENDE;

    private static final Tageszeit DEFAULT_TAGESZEIT = Tageszeit.TAG;
    private static final Tageszeit UPDATED_TAGESZEIT = Tageszeit.NACHT;

    @Autowired
    private VerfuegbarkeitRepository verfuegbarkeitRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVerfuegbarkeitMockMvc;

    private Verfuegbarkeit verfuegbarkeit;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VerfuegbarkeitResource verfuegbarkeitResource = new VerfuegbarkeitResource(verfuegbarkeitRepository);
        this.restVerfuegbarkeitMockMvc = MockMvcBuilders.standaloneSetup(verfuegbarkeitResource)
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
    public static Verfuegbarkeit createEntity(EntityManager em) {
        Verfuegbarkeit verfuegbarkeit = new Verfuegbarkeit()
            .titel(DEFAULT_TITEL)
            .wocheneinteilung(DEFAULT_WOCHENEINTEILUNG)
            .tageszeit(DEFAULT_TAGESZEIT);
        return verfuegbarkeit;
    }

    @Before
    public void initTest() {
        verfuegbarkeit = createEntity(em);
    }

    @Test
    @Transactional
    public void createVerfuegbarkeit() throws Exception {
        int databaseSizeBeforeCreate = verfuegbarkeitRepository.findAll().size();

        // Create the Verfuegbarkeit
        restVerfuegbarkeitMockMvc.perform(post("/api/verfuegbarkeits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(verfuegbarkeit)))
            .andExpect(status().isCreated());

        // Validate the Verfuegbarkeit in the database
        List<Verfuegbarkeit> verfuegbarkeitList = verfuegbarkeitRepository.findAll();
        assertThat(verfuegbarkeitList).hasSize(databaseSizeBeforeCreate + 1);
        Verfuegbarkeit testVerfuegbarkeit = verfuegbarkeitList.get(verfuegbarkeitList.size() - 1);
        assertThat(testVerfuegbarkeit.getTitel()).isEqualTo(DEFAULT_TITEL);
        assertThat(testVerfuegbarkeit.getWocheneinteilung()).isEqualTo(DEFAULT_WOCHENEINTEILUNG);
        assertThat(testVerfuegbarkeit.getTageszeit()).isEqualTo(DEFAULT_TAGESZEIT);
    }

    @Test
    @Transactional
    public void createVerfuegbarkeitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = verfuegbarkeitRepository.findAll().size();

        // Create the Verfuegbarkeit with an existing ID
        verfuegbarkeit.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVerfuegbarkeitMockMvc.perform(post("/api/verfuegbarkeits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(verfuegbarkeit)))
            .andExpect(status().isBadRequest());

        // Validate the Verfuegbarkeit in the database
        List<Verfuegbarkeit> verfuegbarkeitList = verfuegbarkeitRepository.findAll();
        assertThat(verfuegbarkeitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitelIsRequired() throws Exception {
        int databaseSizeBeforeTest = verfuegbarkeitRepository.findAll().size();
        // set the field null
        verfuegbarkeit.setTitel(null);

        // Create the Verfuegbarkeit, which fails.

        restVerfuegbarkeitMockMvc.perform(post("/api/verfuegbarkeits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(verfuegbarkeit)))
            .andExpect(status().isBadRequest());

        List<Verfuegbarkeit> verfuegbarkeitList = verfuegbarkeitRepository.findAll();
        assertThat(verfuegbarkeitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWocheneinteilungIsRequired() throws Exception {
        int databaseSizeBeforeTest = verfuegbarkeitRepository.findAll().size();
        // set the field null
        verfuegbarkeit.setWocheneinteilung(null);

        // Create the Verfuegbarkeit, which fails.

        restVerfuegbarkeitMockMvc.perform(post("/api/verfuegbarkeits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(verfuegbarkeit)))
            .andExpect(status().isBadRequest());

        List<Verfuegbarkeit> verfuegbarkeitList = verfuegbarkeitRepository.findAll();
        assertThat(verfuegbarkeitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTageszeitIsRequired() throws Exception {
        int databaseSizeBeforeTest = verfuegbarkeitRepository.findAll().size();
        // set the field null
        verfuegbarkeit.setTageszeit(null);

        // Create the Verfuegbarkeit, which fails.

        restVerfuegbarkeitMockMvc.perform(post("/api/verfuegbarkeits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(verfuegbarkeit)))
            .andExpect(status().isBadRequest());

        List<Verfuegbarkeit> verfuegbarkeitList = verfuegbarkeitRepository.findAll();
        assertThat(verfuegbarkeitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVerfuegbarkeits() throws Exception {
        // Initialize the database
        verfuegbarkeitRepository.saveAndFlush(verfuegbarkeit);

        // Get all the verfuegbarkeitList
        restVerfuegbarkeitMockMvc.perform(get("/api/verfuegbarkeits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(verfuegbarkeit.getId().intValue())))
            .andExpect(jsonPath("$.[*].titel").value(hasItem(DEFAULT_TITEL.toString())))
            .andExpect(jsonPath("$.[*].wocheneinteilung").value(hasItem(DEFAULT_WOCHENEINTEILUNG.toString())))
            .andExpect(jsonPath("$.[*].tageszeit").value(hasItem(DEFAULT_TAGESZEIT.toString())));
    }

    @Test
    @Transactional
    public void getVerfuegbarkeit() throws Exception {
        // Initialize the database
        verfuegbarkeitRepository.saveAndFlush(verfuegbarkeit);

        // Get the verfuegbarkeit
        restVerfuegbarkeitMockMvc.perform(get("/api/verfuegbarkeits/{id}", verfuegbarkeit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(verfuegbarkeit.getId().intValue()))
            .andExpect(jsonPath("$.titel").value(DEFAULT_TITEL.toString()))
            .andExpect(jsonPath("$.wocheneinteilung").value(DEFAULT_WOCHENEINTEILUNG.toString()))
            .andExpect(jsonPath("$.tageszeit").value(DEFAULT_TAGESZEIT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVerfuegbarkeit() throws Exception {
        // Get the verfuegbarkeit
        restVerfuegbarkeitMockMvc.perform(get("/api/verfuegbarkeits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVerfuegbarkeit() throws Exception {
        // Initialize the database
        verfuegbarkeitRepository.saveAndFlush(verfuegbarkeit);
        int databaseSizeBeforeUpdate = verfuegbarkeitRepository.findAll().size();

        // Update the verfuegbarkeit
        Verfuegbarkeit updatedVerfuegbarkeit = verfuegbarkeitRepository.findOne(verfuegbarkeit.getId());
        updatedVerfuegbarkeit
            .titel(UPDATED_TITEL)
            .wocheneinteilung(UPDATED_WOCHENEINTEILUNG)
            .tageszeit(UPDATED_TAGESZEIT);

        restVerfuegbarkeitMockMvc.perform(put("/api/verfuegbarkeits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVerfuegbarkeit)))
            .andExpect(status().isOk());

        // Validate the Verfuegbarkeit in the database
        List<Verfuegbarkeit> verfuegbarkeitList = verfuegbarkeitRepository.findAll();
        assertThat(verfuegbarkeitList).hasSize(databaseSizeBeforeUpdate);
        Verfuegbarkeit testVerfuegbarkeit = verfuegbarkeitList.get(verfuegbarkeitList.size() - 1);
        assertThat(testVerfuegbarkeit.getTitel()).isEqualTo(UPDATED_TITEL);
        assertThat(testVerfuegbarkeit.getWocheneinteilung()).isEqualTo(UPDATED_WOCHENEINTEILUNG);
        assertThat(testVerfuegbarkeit.getTageszeit()).isEqualTo(UPDATED_TAGESZEIT);
    }

    @Test
    @Transactional
    public void updateNonExistingVerfuegbarkeit() throws Exception {
        int databaseSizeBeforeUpdate = verfuegbarkeitRepository.findAll().size();

        // Create the Verfuegbarkeit

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVerfuegbarkeitMockMvc.perform(put("/api/verfuegbarkeits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(verfuegbarkeit)))
            .andExpect(status().isCreated());

        // Validate the Verfuegbarkeit in the database
        List<Verfuegbarkeit> verfuegbarkeitList = verfuegbarkeitRepository.findAll();
        assertThat(verfuegbarkeitList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVerfuegbarkeit() throws Exception {
        // Initialize the database
        verfuegbarkeitRepository.saveAndFlush(verfuegbarkeit);
        int databaseSizeBeforeDelete = verfuegbarkeitRepository.findAll().size();

        // Get the verfuegbarkeit
        restVerfuegbarkeitMockMvc.perform(delete("/api/verfuegbarkeits/{id}", verfuegbarkeit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Verfuegbarkeit> verfuegbarkeitList = verfuegbarkeitRepository.findAll();
        assertThat(verfuegbarkeitList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Verfuegbarkeit.class);
        Verfuegbarkeit verfuegbarkeit1 = new Verfuegbarkeit();
        verfuegbarkeit1.setId(1L);
        Verfuegbarkeit verfuegbarkeit2 = new Verfuegbarkeit();
        verfuegbarkeit2.setId(verfuegbarkeit1.getId());
        assertThat(verfuegbarkeit1).isEqualTo(verfuegbarkeit2);
        verfuegbarkeit2.setId(2L);
        assertThat(verfuegbarkeit1).isNotEqualTo(verfuegbarkeit2);
        verfuegbarkeit1.setId(null);
        assertThat(verfuegbarkeit1).isNotEqualTo(verfuegbarkeit2);
    }
}
