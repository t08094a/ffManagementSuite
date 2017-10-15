package de.leif.ffmanagementsuite.web.rest;

import de.leif.ffmanagementsuite.FfManagementSuiteApp;

import de.leif.ffmanagementsuite.domain.Auspraegung;
import de.leif.ffmanagementsuite.repository.AuspraegungRepository;
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
 * Test class for the AuspraegungResource REST controller.
 *
 * @see AuspraegungResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FfManagementSuiteApp.class)
public class AuspraegungResourceIntTest {

    private static final String DEFAULT_BEZEICHNUNG = "AAAAAAAAAA";
    private static final String UPDATED_BEZEICHNUNG = "BBBBBBBBBB";

    @Autowired
    private AuspraegungRepository auspraegungRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAuspraegungMockMvc;

    private Auspraegung auspraegung;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AuspraegungResource auspraegungResource = new AuspraegungResource(auspraegungRepository);
        this.restAuspraegungMockMvc = MockMvcBuilders.standaloneSetup(auspraegungResource)
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
    public static Auspraegung createEntity(EntityManager em) {
        Auspraegung auspraegung = new Auspraegung()
            .bezeichnung(DEFAULT_BEZEICHNUNG);
        return auspraegung;
    }

    @Before
    public void initTest() {
        auspraegung = createEntity(em);
    }

    @Test
    @Transactional
    public void createAuspraegung() throws Exception {
        int databaseSizeBeforeCreate = auspraegungRepository.findAll().size();

        // Create the Auspraegung
        restAuspraegungMockMvc.perform(post("/api/auspraegungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auspraegung)))
            .andExpect(status().isCreated());

        // Validate the Auspraegung in the database
        List<Auspraegung> auspraegungList = auspraegungRepository.findAll();
        assertThat(auspraegungList).hasSize(databaseSizeBeforeCreate + 1);
        Auspraegung testAuspraegung = auspraegungList.get(auspraegungList.size() - 1);
        assertThat(testAuspraegung.getBezeichnung()).isEqualTo(DEFAULT_BEZEICHNUNG);
    }

    @Test
    @Transactional
    public void createAuspraegungWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = auspraegungRepository.findAll().size();

        // Create the Auspraegung with an existing ID
        auspraegung.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuspraegungMockMvc.perform(post("/api/auspraegungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auspraegung)))
            .andExpect(status().isBadRequest());

        // Validate the Auspraegung in the database
        List<Auspraegung> auspraegungList = auspraegungRepository.findAll();
        assertThat(auspraegungList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkBezeichnungIsRequired() throws Exception {
        int databaseSizeBeforeTest = auspraegungRepository.findAll().size();
        // set the field null
        auspraegung.setBezeichnung(null);

        // Create the Auspraegung, which fails.

        restAuspraegungMockMvc.perform(post("/api/auspraegungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auspraegung)))
            .andExpect(status().isBadRequest());

        List<Auspraegung> auspraegungList = auspraegungRepository.findAll();
        assertThat(auspraegungList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAuspraegungs() throws Exception {
        // Initialize the database
        auspraegungRepository.saveAndFlush(auspraegung);

        // Get all the auspraegungList
        restAuspraegungMockMvc.perform(get("/api/auspraegungs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auspraegung.getId().intValue())))
            .andExpect(jsonPath("$.[*].bezeichnung").value(hasItem(DEFAULT_BEZEICHNUNG.toString())));
    }

    @Test
    @Transactional
    public void getAuspraegung() throws Exception {
        // Initialize the database
        auspraegungRepository.saveAndFlush(auspraegung);

        // Get the auspraegung
        restAuspraegungMockMvc.perform(get("/api/auspraegungs/{id}", auspraegung.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(auspraegung.getId().intValue()))
            .andExpect(jsonPath("$.bezeichnung").value(DEFAULT_BEZEICHNUNG.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAuspraegung() throws Exception {
        // Get the auspraegung
        restAuspraegungMockMvc.perform(get("/api/auspraegungs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuspraegung() throws Exception {
        // Initialize the database
        auspraegungRepository.saveAndFlush(auspraegung);
        int databaseSizeBeforeUpdate = auspraegungRepository.findAll().size();

        // Update the auspraegung
        Auspraegung updatedAuspraegung = auspraegungRepository.findOne(auspraegung.getId());
        updatedAuspraegung
            .bezeichnung(UPDATED_BEZEICHNUNG);

        restAuspraegungMockMvc.perform(put("/api/auspraegungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAuspraegung)))
            .andExpect(status().isOk());

        // Validate the Auspraegung in the database
        List<Auspraegung> auspraegungList = auspraegungRepository.findAll();
        assertThat(auspraegungList).hasSize(databaseSizeBeforeUpdate);
        Auspraegung testAuspraegung = auspraegungList.get(auspraegungList.size() - 1);
        assertThat(testAuspraegung.getBezeichnung()).isEqualTo(UPDATED_BEZEICHNUNG);
    }

    @Test
    @Transactional
    public void updateNonExistingAuspraegung() throws Exception {
        int databaseSizeBeforeUpdate = auspraegungRepository.findAll().size();

        // Create the Auspraegung

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAuspraegungMockMvc.perform(put("/api/auspraegungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auspraegung)))
            .andExpect(status().isCreated());

        // Validate the Auspraegung in the database
        List<Auspraegung> auspraegungList = auspraegungRepository.findAll();
        assertThat(auspraegungList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAuspraegung() throws Exception {
        // Initialize the database
        auspraegungRepository.saveAndFlush(auspraegung);
        int databaseSizeBeforeDelete = auspraegungRepository.findAll().size();

        // Get the auspraegung
        restAuspraegungMockMvc.perform(delete("/api/auspraegungs/{id}", auspraegung.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Auspraegung> auspraegungList = auspraegungRepository.findAll();
        assertThat(auspraegungList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Auspraegung.class);
        Auspraegung auspraegung1 = new Auspraegung();
        auspraegung1.setId(1L);
        Auspraegung auspraegung2 = new Auspraegung();
        auspraegung2.setId(auspraegung1.getId());
        assertThat(auspraegung1).isEqualTo(auspraegung2);
        auspraegung2.setId(2L);
        assertThat(auspraegung1).isNotEqualTo(auspraegung2);
        auspraegung1.setId(null);
        assertThat(auspraegung1).isNotEqualTo(auspraegung2);
    }
}
