package de.leif.ffmanagementsuite.web.rest;

import de.leif.ffmanagementsuite.FfManagementSuiteApp;

import de.leif.ffmanagementsuite.domain.Dienststellung;
import de.leif.ffmanagementsuite.repository.DienststellungRepository;
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

/**
 * Test class for the DienststellungResource REST controller.
 *
 * @see DienststellungResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FfManagementSuiteApp.class)
public class DienststellungResourceIntTest {

    private static final String DEFAULT_TITEL = "AAAAAAAAAA";
    private static final String UPDATED_TITEL = "BBBBBBBBBB";

    private static final String DEFAULT_ABKUERZUNG = "AAAAAAAAAA";
    private static final String UPDATED_ABKUERZUNG = "BBBBBBBBBB";

    @Autowired
    private DienststellungRepository dienststellungRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDienststellungMockMvc;

    private Dienststellung dienststellung;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DienststellungResource dienststellungResource = new DienststellungResource(dienststellungRepository);
        this.restDienststellungMockMvc = MockMvcBuilders.standaloneSetup(dienststellungResource)
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
    public static Dienststellung createEntity(EntityManager em) {
        Dienststellung dienststellung = new Dienststellung()
            .titel(DEFAULT_TITEL)
            .abkuerzung(DEFAULT_ABKUERZUNG);
        return dienststellung;
    }

    @Before
    public void initTest() {
        dienststellung = createEntity(em);
    }

    @Test
    @Transactional
    public void createDienststellung() throws Exception {
        int databaseSizeBeforeCreate = dienststellungRepository.findAll().size();

        // Create the Dienststellung
        restDienststellungMockMvc.perform(post("/api/dienststellungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dienststellung)))
            .andExpect(status().isCreated());

        // Validate the Dienststellung in the database
        List<Dienststellung> dienststellungList = dienststellungRepository.findAll();
        assertThat(dienststellungList).hasSize(databaseSizeBeforeCreate + 1);
        Dienststellung testDienststellung = dienststellungList.get(dienststellungList.size() - 1);
        assertThat(testDienststellung.getTitel()).isEqualTo(DEFAULT_TITEL);
        assertThat(testDienststellung.getAbkuerzung()).isEqualTo(DEFAULT_ABKUERZUNG);
    }

    @Test
    @Transactional
    public void createDienststellungWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dienststellungRepository.findAll().size();

        // Create the Dienststellung with an existing ID
        dienststellung.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDienststellungMockMvc.perform(post("/api/dienststellungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dienststellung)))
            .andExpect(status().isBadRequest());

        // Validate the Dienststellung in the database
        List<Dienststellung> dienststellungList = dienststellungRepository.findAll();
        assertThat(dienststellungList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitelIsRequired() throws Exception {
        int databaseSizeBeforeTest = dienststellungRepository.findAll().size();
        // set the field null
        dienststellung.setTitel(null);

        // Create the Dienststellung, which fails.

        restDienststellungMockMvc.perform(post("/api/dienststellungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dienststellung)))
            .andExpect(status().isBadRequest());

        List<Dienststellung> dienststellungList = dienststellungRepository.findAll();
        assertThat(dienststellungList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAbkuerzungIsRequired() throws Exception {
        int databaseSizeBeforeTest = dienststellungRepository.findAll().size();
        // set the field null
        dienststellung.setAbkuerzung(null);

        // Create the Dienststellung, which fails.

        restDienststellungMockMvc.perform(post("/api/dienststellungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dienststellung)))
            .andExpect(status().isBadRequest());

        List<Dienststellung> dienststellungList = dienststellungRepository.findAll();
        assertThat(dienststellungList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDienststellungs() throws Exception {
        // Initialize the database
        dienststellungRepository.saveAndFlush(dienststellung);

        // Get all the dienststellungList
        restDienststellungMockMvc.perform(get("/api/dienststellungs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dienststellung.getId().intValue())))
            .andExpect(jsonPath("$.[*].titel").value(hasItem(DEFAULT_TITEL.toString())))
            .andExpect(jsonPath("$.[*].abkuerzung").value(hasItem(DEFAULT_ABKUERZUNG.toString())));
    }

    @Test
    @Transactional
    public void getDienststellung() throws Exception {
        // Initialize the database
        dienststellungRepository.saveAndFlush(dienststellung);

        // Get the dienststellung
        restDienststellungMockMvc.perform(get("/api/dienststellungs/{id}", dienststellung.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dienststellung.getId().intValue()))
            .andExpect(jsonPath("$.titel").value(DEFAULT_TITEL.toString()))
            .andExpect(jsonPath("$.abkuerzung").value(DEFAULT_ABKUERZUNG.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDienststellung() throws Exception {
        // Get the dienststellung
        restDienststellungMockMvc.perform(get("/api/dienststellungs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDienststellung() throws Exception {
        // Initialize the database
        dienststellungRepository.saveAndFlush(dienststellung);
        int databaseSizeBeforeUpdate = dienststellungRepository.findAll().size();

        // Update the dienststellung
        Dienststellung updatedDienststellung = dienststellungRepository.findOne(dienststellung.getId());
        updatedDienststellung
            .titel(UPDATED_TITEL)
            .abkuerzung(UPDATED_ABKUERZUNG);

        restDienststellungMockMvc.perform(put("/api/dienststellungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDienststellung)))
            .andExpect(status().isOk());

        // Validate the Dienststellung in the database
        List<Dienststellung> dienststellungList = dienststellungRepository.findAll();
        assertThat(dienststellungList).hasSize(databaseSizeBeforeUpdate);
        Dienststellung testDienststellung = dienststellungList.get(dienststellungList.size() - 1);
        assertThat(testDienststellung.getTitel()).isEqualTo(UPDATED_TITEL);
        assertThat(testDienststellung.getAbkuerzung()).isEqualTo(UPDATED_ABKUERZUNG);
    }

    @Test
    @Transactional
    public void updateNonExistingDienststellung() throws Exception {
        int databaseSizeBeforeUpdate = dienststellungRepository.findAll().size();

        // Create the Dienststellung

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDienststellungMockMvc.perform(put("/api/dienststellungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dienststellung)))
            .andExpect(status().isCreated());

        // Validate the Dienststellung in the database
        List<Dienststellung> dienststellungList = dienststellungRepository.findAll();
        assertThat(dienststellungList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDienststellung() throws Exception {
        // Initialize the database
        dienststellungRepository.saveAndFlush(dienststellung);
        int databaseSizeBeforeDelete = dienststellungRepository.findAll().size();

        // Get the dienststellung
        restDienststellungMockMvc.perform(delete("/api/dienststellungs/{id}", dienststellung.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Dienststellung> dienststellungList = dienststellungRepository.findAll();
        assertThat(dienststellungList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dienststellung.class);
        Dienststellung dienststellung1 = new Dienststellung();
        dienststellung1.setId(1L);
        Dienststellung dienststellung2 = new Dienststellung();
        dienststellung2.setId(dienststellung1.getId());
        assertThat(dienststellung1).isEqualTo(dienststellung2);
        dienststellung2.setId(2L);
        assertThat(dienststellung1).isNotEqualTo(dienststellung2);
        dienststellung1.setId(null);
        assertThat(dienststellung1).isNotEqualTo(dienststellung2);
    }
}
