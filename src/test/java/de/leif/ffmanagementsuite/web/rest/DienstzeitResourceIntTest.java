package de.leif.ffmanagementsuite.web.rest;

import de.leif.ffmanagementsuite.FfManagementSuiteApp;

import de.leif.ffmanagementsuite.domain.Dienstzeit;
import de.leif.ffmanagementsuite.repository.DienstzeitRepository;
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
 * Test class for the DienstzeitResource REST controller.
 *
 * @see DienstzeitResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FfManagementSuiteApp.class)
public class DienstzeitResourceIntTest {

    private static final LocalDate DEFAULT_BEGINN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BEGINN = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ENDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ENDE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private DienstzeitRepository dienstzeitRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDienstzeitMockMvc;

    private Dienstzeit dienstzeit;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DienstzeitResource dienstzeitResource = new DienstzeitResource(dienstzeitRepository);
        this.restDienstzeitMockMvc = MockMvcBuilders.standaloneSetup(dienstzeitResource)
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
    public static Dienstzeit createEntity(EntityManager em) {
        Dienstzeit dienstzeit = new Dienstzeit()
            .beginn(DEFAULT_BEGINN)
            .ende(DEFAULT_ENDE);
        return dienstzeit;
    }

    @Before
    public void initTest() {
        dienstzeit = createEntity(em);
    }

    @Test
    @Transactional
    public void createDienstzeit() throws Exception {
        int databaseSizeBeforeCreate = dienstzeitRepository.findAll().size();

        // Create the Dienstzeit
        restDienstzeitMockMvc.perform(post("/api/dienstzeits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dienstzeit)))
            .andExpect(status().isCreated());

        // Validate the Dienstzeit in the database
        List<Dienstzeit> dienstzeitList = dienstzeitRepository.findAll();
        assertThat(dienstzeitList).hasSize(databaseSizeBeforeCreate + 1);
        Dienstzeit testDienstzeit = dienstzeitList.get(dienstzeitList.size() - 1);
        assertThat(testDienstzeit.getBeginn()).isEqualTo(DEFAULT_BEGINN);
        assertThat(testDienstzeit.getEnde()).isEqualTo(DEFAULT_ENDE);
    }

    @Test
    @Transactional
    public void createDienstzeitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dienstzeitRepository.findAll().size();

        // Create the Dienstzeit with an existing ID
        dienstzeit.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDienstzeitMockMvc.perform(post("/api/dienstzeits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dienstzeit)))
            .andExpect(status().isBadRequest());

        // Validate the Dienstzeit in the database
        List<Dienstzeit> dienstzeitList = dienstzeitRepository.findAll();
        assertThat(dienstzeitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkBeginnIsRequired() throws Exception {
        int databaseSizeBeforeTest = dienstzeitRepository.findAll().size();
        // set the field null
        dienstzeit.setBeginn(null);

        // Create the Dienstzeit, which fails.

        restDienstzeitMockMvc.perform(post("/api/dienstzeits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dienstzeit)))
            .andExpect(status().isBadRequest());

        List<Dienstzeit> dienstzeitList = dienstzeitRepository.findAll();
        assertThat(dienstzeitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDienstzeits() throws Exception {
        // Initialize the database
        dienstzeitRepository.saveAndFlush(dienstzeit);

        // Get all the dienstzeitList
        restDienstzeitMockMvc.perform(get("/api/dienstzeits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dienstzeit.getId().intValue())))
            .andExpect(jsonPath("$.[*].beginn").value(hasItem(DEFAULT_BEGINN.toString())))
            .andExpect(jsonPath("$.[*].ende").value(hasItem(DEFAULT_ENDE.toString())));
    }

    @Test
    @Transactional
    public void getDienstzeit() throws Exception {
        // Initialize the database
        dienstzeitRepository.saveAndFlush(dienstzeit);

        // Get the dienstzeit
        restDienstzeitMockMvc.perform(get("/api/dienstzeits/{id}", dienstzeit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dienstzeit.getId().intValue()))
            .andExpect(jsonPath("$.beginn").value(DEFAULT_BEGINN.toString()))
            .andExpect(jsonPath("$.ende").value(DEFAULT_ENDE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDienstzeit() throws Exception {
        // Get the dienstzeit
        restDienstzeitMockMvc.perform(get("/api/dienstzeits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDienstzeit() throws Exception {
        // Initialize the database
        dienstzeitRepository.saveAndFlush(dienstzeit);
        int databaseSizeBeforeUpdate = dienstzeitRepository.findAll().size();

        // Update the dienstzeit
        Dienstzeit updatedDienstzeit = dienstzeitRepository.findOne(dienstzeit.getId());
        updatedDienstzeit
            .beginn(UPDATED_BEGINN)
            .ende(UPDATED_ENDE);

        restDienstzeitMockMvc.perform(put("/api/dienstzeits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDienstzeit)))
            .andExpect(status().isOk());

        // Validate the Dienstzeit in the database
        List<Dienstzeit> dienstzeitList = dienstzeitRepository.findAll();
        assertThat(dienstzeitList).hasSize(databaseSizeBeforeUpdate);
        Dienstzeit testDienstzeit = dienstzeitList.get(dienstzeitList.size() - 1);
        assertThat(testDienstzeit.getBeginn()).isEqualTo(UPDATED_BEGINN);
        assertThat(testDienstzeit.getEnde()).isEqualTo(UPDATED_ENDE);
    }

    @Test
    @Transactional
    public void updateNonExistingDienstzeit() throws Exception {
        int databaseSizeBeforeUpdate = dienstzeitRepository.findAll().size();

        // Create the Dienstzeit

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDienstzeitMockMvc.perform(put("/api/dienstzeits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dienstzeit)))
            .andExpect(status().isCreated());

        // Validate the Dienstzeit in the database
        List<Dienstzeit> dienstzeitList = dienstzeitRepository.findAll();
        assertThat(dienstzeitList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDienstzeit() throws Exception {
        // Initialize the database
        dienstzeitRepository.saveAndFlush(dienstzeit);
        int databaseSizeBeforeDelete = dienstzeitRepository.findAll().size();

        // Get the dienstzeit
        restDienstzeitMockMvc.perform(delete("/api/dienstzeits/{id}", dienstzeit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Dienstzeit> dienstzeitList = dienstzeitRepository.findAll();
        assertThat(dienstzeitList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dienstzeit.class);
        Dienstzeit dienstzeit1 = new Dienstzeit();
        dienstzeit1.setId(1L);
        Dienstzeit dienstzeit2 = new Dienstzeit();
        dienstzeit2.setId(dienstzeit1.getId());
        assertThat(dienstzeit1).isEqualTo(dienstzeit2);
        dienstzeit2.setId(2L);
        assertThat(dienstzeit1).isNotEqualTo(dienstzeit2);
        dienstzeit1.setId(null);
        assertThat(dienstzeit1).isNotEqualTo(dienstzeit2);
    }
}
