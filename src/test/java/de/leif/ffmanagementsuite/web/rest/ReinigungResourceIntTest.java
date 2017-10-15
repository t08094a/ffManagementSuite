package de.leif.ffmanagementsuite.web.rest;

import de.leif.ffmanagementsuite.FfManagementSuiteApp;

import de.leif.ffmanagementsuite.domain.Reinigung;
import de.leif.ffmanagementsuite.repository.ReinigungRepository;
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
 * Test class for the ReinigungResource REST controller.
 *
 * @see ReinigungResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FfManagementSuiteApp.class)
public class ReinigungResourceIntTest {

    private static final LocalDate DEFAULT_DURCHFUEHRUNG = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DURCHFUEHRUNG = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ReinigungRepository reinigungRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restReinigungMockMvc;

    private Reinigung reinigung;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReinigungResource reinigungResource = new ReinigungResource(reinigungRepository);
        this.restReinigungMockMvc = MockMvcBuilders.standaloneSetup(reinigungResource)
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
    public static Reinigung createEntity(EntityManager em) {
        Reinigung reinigung = new Reinigung()
            .durchfuehrung(DEFAULT_DURCHFUEHRUNG);
        return reinigung;
    }

    @Before
    public void initTest() {
        reinigung = createEntity(em);
    }

    @Test
    @Transactional
    public void createReinigung() throws Exception {
        int databaseSizeBeforeCreate = reinigungRepository.findAll().size();

        // Create the Reinigung
        restReinigungMockMvc.perform(post("/api/reinigungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reinigung)))
            .andExpect(status().isCreated());

        // Validate the Reinigung in the database
        List<Reinigung> reinigungList = reinigungRepository.findAll();
        assertThat(reinigungList).hasSize(databaseSizeBeforeCreate + 1);
        Reinigung testReinigung = reinigungList.get(reinigungList.size() - 1);
        assertThat(testReinigung.getDurchfuehrung()).isEqualTo(DEFAULT_DURCHFUEHRUNG);
    }

    @Test
    @Transactional
    public void createReinigungWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reinigungRepository.findAll().size();

        // Create the Reinigung with an existing ID
        reinigung.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReinigungMockMvc.perform(post("/api/reinigungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reinigung)))
            .andExpect(status().isBadRequest());

        // Validate the Reinigung in the database
        List<Reinigung> reinigungList = reinigungRepository.findAll();
        assertThat(reinigungList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllReinigungs() throws Exception {
        // Initialize the database
        reinigungRepository.saveAndFlush(reinigung);

        // Get all the reinigungList
        restReinigungMockMvc.perform(get("/api/reinigungs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reinigung.getId().intValue())))
            .andExpect(jsonPath("$.[*].durchfuehrung").value(hasItem(DEFAULT_DURCHFUEHRUNG.toString())));
    }

    @Test
    @Transactional
    public void getReinigung() throws Exception {
        // Initialize the database
        reinigungRepository.saveAndFlush(reinigung);

        // Get the reinigung
        restReinigungMockMvc.perform(get("/api/reinigungs/{id}", reinigung.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reinigung.getId().intValue()))
            .andExpect(jsonPath("$.durchfuehrung").value(DEFAULT_DURCHFUEHRUNG.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReinigung() throws Exception {
        // Get the reinigung
        restReinigungMockMvc.perform(get("/api/reinigungs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReinigung() throws Exception {
        // Initialize the database
        reinigungRepository.saveAndFlush(reinigung);
        int databaseSizeBeforeUpdate = reinigungRepository.findAll().size();

        // Update the reinigung
        Reinigung updatedReinigung = reinigungRepository.findOne(reinigung.getId());
        updatedReinigung
            .durchfuehrung(UPDATED_DURCHFUEHRUNG);

        restReinigungMockMvc.perform(put("/api/reinigungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedReinigung)))
            .andExpect(status().isOk());

        // Validate the Reinigung in the database
        List<Reinigung> reinigungList = reinigungRepository.findAll();
        assertThat(reinigungList).hasSize(databaseSizeBeforeUpdate);
        Reinigung testReinigung = reinigungList.get(reinigungList.size() - 1);
        assertThat(testReinigung.getDurchfuehrung()).isEqualTo(UPDATED_DURCHFUEHRUNG);
    }

    @Test
    @Transactional
    public void updateNonExistingReinigung() throws Exception {
        int databaseSizeBeforeUpdate = reinigungRepository.findAll().size();

        // Create the Reinigung

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restReinigungMockMvc.perform(put("/api/reinigungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reinigung)))
            .andExpect(status().isCreated());

        // Validate the Reinigung in the database
        List<Reinigung> reinigungList = reinigungRepository.findAll();
        assertThat(reinigungList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteReinigung() throws Exception {
        // Initialize the database
        reinigungRepository.saveAndFlush(reinigung);
        int databaseSizeBeforeDelete = reinigungRepository.findAll().size();

        // Get the reinigung
        restReinigungMockMvc.perform(delete("/api/reinigungs/{id}", reinigung.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Reinigung> reinigungList = reinigungRepository.findAll();
        assertThat(reinigungList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reinigung.class);
        Reinigung reinigung1 = new Reinigung();
        reinigung1.setId(1L);
        Reinigung reinigung2 = new Reinigung();
        reinigung2.setId(reinigung1.getId());
        assertThat(reinigung1).isEqualTo(reinigung2);
        reinigung2.setId(2L);
        assertThat(reinigung1).isNotEqualTo(reinigung2);
        reinigung1.setId(null);
        assertThat(reinigung1).isNotEqualTo(reinigung2);
    }
}
