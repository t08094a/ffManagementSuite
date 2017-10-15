package de.leif.ffmanagementsuite.web.rest;

import de.leif.ffmanagementsuite.FfManagementSuiteApp;

import de.leif.ffmanagementsuite.domain.Lehrgang;
import de.leif.ffmanagementsuite.repository.LehrgangRepository;
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
 * Test class for the LehrgangResource REST controller.
 *
 * @see LehrgangResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FfManagementSuiteApp.class)
public class LehrgangResourceIntTest {

    private static final String DEFAULT_TITEL = "AAAAAAAAAA";
    private static final String UPDATED_TITEL = "BBBBBBBBBB";

    private static final String DEFAULT_ABKUERZUNG = "AAAAAAAAAA";
    private static final String UPDATED_ABKUERZUNG = "BBBBBBBBBB";

    @Autowired
    private LehrgangRepository lehrgangRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLehrgangMockMvc;

    private Lehrgang lehrgang;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LehrgangResource lehrgangResource = new LehrgangResource(lehrgangRepository);
        this.restLehrgangMockMvc = MockMvcBuilders.standaloneSetup(lehrgangResource)
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
    public static Lehrgang createEntity(EntityManager em) {
        Lehrgang lehrgang = new Lehrgang()
            .titel(DEFAULT_TITEL)
            .abkuerzung(DEFAULT_ABKUERZUNG);
        return lehrgang;
    }

    @Before
    public void initTest() {
        lehrgang = createEntity(em);
    }

    @Test
    @Transactional
    public void createLehrgang() throws Exception {
        int databaseSizeBeforeCreate = lehrgangRepository.findAll().size();

        // Create the Lehrgang
        restLehrgangMockMvc.perform(post("/api/lehrgangs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lehrgang)))
            .andExpect(status().isCreated());

        // Validate the Lehrgang in the database
        List<Lehrgang> lehrgangList = lehrgangRepository.findAll();
        assertThat(lehrgangList).hasSize(databaseSizeBeforeCreate + 1);
        Lehrgang testLehrgang = lehrgangList.get(lehrgangList.size() - 1);
        assertThat(testLehrgang.getTitel()).isEqualTo(DEFAULT_TITEL);
        assertThat(testLehrgang.getAbkuerzung()).isEqualTo(DEFAULT_ABKUERZUNG);
    }

    @Test
    @Transactional
    public void createLehrgangWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lehrgangRepository.findAll().size();

        // Create the Lehrgang with an existing ID
        lehrgang.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLehrgangMockMvc.perform(post("/api/lehrgangs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lehrgang)))
            .andExpect(status().isBadRequest());

        // Validate the Lehrgang in the database
        List<Lehrgang> lehrgangList = lehrgangRepository.findAll();
        assertThat(lehrgangList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitelIsRequired() throws Exception {
        int databaseSizeBeforeTest = lehrgangRepository.findAll().size();
        // set the field null
        lehrgang.setTitel(null);

        // Create the Lehrgang, which fails.

        restLehrgangMockMvc.perform(post("/api/lehrgangs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lehrgang)))
            .andExpect(status().isBadRequest());

        List<Lehrgang> lehrgangList = lehrgangRepository.findAll();
        assertThat(lehrgangList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLehrgangs() throws Exception {
        // Initialize the database
        lehrgangRepository.saveAndFlush(lehrgang);

        // Get all the lehrgangList
        restLehrgangMockMvc.perform(get("/api/lehrgangs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lehrgang.getId().intValue())))
            .andExpect(jsonPath("$.[*].titel").value(hasItem(DEFAULT_TITEL.toString())))
            .andExpect(jsonPath("$.[*].abkuerzung").value(hasItem(DEFAULT_ABKUERZUNG.toString())));
    }

    @Test
    @Transactional
    public void getLehrgang() throws Exception {
        // Initialize the database
        lehrgangRepository.saveAndFlush(lehrgang);

        // Get the lehrgang
        restLehrgangMockMvc.perform(get("/api/lehrgangs/{id}", lehrgang.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lehrgang.getId().intValue()))
            .andExpect(jsonPath("$.titel").value(DEFAULT_TITEL.toString()))
            .andExpect(jsonPath("$.abkuerzung").value(DEFAULT_ABKUERZUNG.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLehrgang() throws Exception {
        // Get the lehrgang
        restLehrgangMockMvc.perform(get("/api/lehrgangs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLehrgang() throws Exception {
        // Initialize the database
        lehrgangRepository.saveAndFlush(lehrgang);
        int databaseSizeBeforeUpdate = lehrgangRepository.findAll().size();

        // Update the lehrgang
        Lehrgang updatedLehrgang = lehrgangRepository.findOne(lehrgang.getId());
        updatedLehrgang
            .titel(UPDATED_TITEL)
            .abkuerzung(UPDATED_ABKUERZUNG);

        restLehrgangMockMvc.perform(put("/api/lehrgangs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLehrgang)))
            .andExpect(status().isOk());

        // Validate the Lehrgang in the database
        List<Lehrgang> lehrgangList = lehrgangRepository.findAll();
        assertThat(lehrgangList).hasSize(databaseSizeBeforeUpdate);
        Lehrgang testLehrgang = lehrgangList.get(lehrgangList.size() - 1);
        assertThat(testLehrgang.getTitel()).isEqualTo(UPDATED_TITEL);
        assertThat(testLehrgang.getAbkuerzung()).isEqualTo(UPDATED_ABKUERZUNG);
    }

    @Test
    @Transactional
    public void updateNonExistingLehrgang() throws Exception {
        int databaseSizeBeforeUpdate = lehrgangRepository.findAll().size();

        // Create the Lehrgang

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLehrgangMockMvc.perform(put("/api/lehrgangs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lehrgang)))
            .andExpect(status().isCreated());

        // Validate the Lehrgang in the database
        List<Lehrgang> lehrgangList = lehrgangRepository.findAll();
        assertThat(lehrgangList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLehrgang() throws Exception {
        // Initialize the database
        lehrgangRepository.saveAndFlush(lehrgang);
        int databaseSizeBeforeDelete = lehrgangRepository.findAll().size();

        // Get the lehrgang
        restLehrgangMockMvc.perform(delete("/api/lehrgangs/{id}", lehrgang.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Lehrgang> lehrgangList = lehrgangRepository.findAll();
        assertThat(lehrgangList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Lehrgang.class);
        Lehrgang lehrgang1 = new Lehrgang();
        lehrgang1.setId(1L);
        Lehrgang lehrgang2 = new Lehrgang();
        lehrgang2.setId(lehrgang1.getId());
        assertThat(lehrgang1).isEqualTo(lehrgang2);
        lehrgang2.setId(2L);
        assertThat(lehrgang1).isNotEqualTo(lehrgang2);
        lehrgang1.setId(null);
        assertThat(lehrgang1).isNotEqualTo(lehrgang2);
    }
}
