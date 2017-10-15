package de.leif.ffmanagementsuite.web.rest;

import de.leif.ffmanagementsuite.FfManagementSuiteApp;

import de.leif.ffmanagementsuite.domain.Fuehrerschein;
import de.leif.ffmanagementsuite.repository.FuehrerscheinRepository;
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
 * Test class for the FuehrerscheinResource REST controller.
 *
 * @see FuehrerscheinResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FfManagementSuiteApp.class)
public class FuehrerscheinResourceIntTest {

    private static final String DEFAULT_KLASSE = "AAAAAAAAAA";
    private static final String UPDATED_KLASSE = "BBBBBBBBBB";

    @Autowired
    private FuehrerscheinRepository fuehrerscheinRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFuehrerscheinMockMvc;

    private Fuehrerschein fuehrerschein;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FuehrerscheinResource fuehrerscheinResource = new FuehrerscheinResource(fuehrerscheinRepository);
        this.restFuehrerscheinMockMvc = MockMvcBuilders.standaloneSetup(fuehrerscheinResource)
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
    public static Fuehrerschein createEntity(EntityManager em) {
        Fuehrerschein fuehrerschein = new Fuehrerschein()
            .klasse(DEFAULT_KLASSE);
        return fuehrerschein;
    }

    @Before
    public void initTest() {
        fuehrerschein = createEntity(em);
    }

    @Test
    @Transactional
    public void createFuehrerschein() throws Exception {
        int databaseSizeBeforeCreate = fuehrerscheinRepository.findAll().size();

        // Create the Fuehrerschein
        restFuehrerscheinMockMvc.perform(post("/api/fuehrerscheins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fuehrerschein)))
            .andExpect(status().isCreated());

        // Validate the Fuehrerschein in the database
        List<Fuehrerschein> fuehrerscheinList = fuehrerscheinRepository.findAll();
        assertThat(fuehrerscheinList).hasSize(databaseSizeBeforeCreate + 1);
        Fuehrerschein testFuehrerschein = fuehrerscheinList.get(fuehrerscheinList.size() - 1);
        assertThat(testFuehrerschein.getKlasse()).isEqualTo(DEFAULT_KLASSE);
    }

    @Test
    @Transactional
    public void createFuehrerscheinWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fuehrerscheinRepository.findAll().size();

        // Create the Fuehrerschein with an existing ID
        fuehrerschein.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFuehrerscheinMockMvc.perform(post("/api/fuehrerscheins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fuehrerschein)))
            .andExpect(status().isBadRequest());

        // Validate the Fuehrerschein in the database
        List<Fuehrerschein> fuehrerscheinList = fuehrerscheinRepository.findAll();
        assertThat(fuehrerscheinList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkKlasseIsRequired() throws Exception {
        int databaseSizeBeforeTest = fuehrerscheinRepository.findAll().size();
        // set the field null
        fuehrerschein.setKlasse(null);

        // Create the Fuehrerschein, which fails.

        restFuehrerscheinMockMvc.perform(post("/api/fuehrerscheins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fuehrerschein)))
            .andExpect(status().isBadRequest());

        List<Fuehrerschein> fuehrerscheinList = fuehrerscheinRepository.findAll();
        assertThat(fuehrerscheinList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFuehrerscheins() throws Exception {
        // Initialize the database
        fuehrerscheinRepository.saveAndFlush(fuehrerschein);

        // Get all the fuehrerscheinList
        restFuehrerscheinMockMvc.perform(get("/api/fuehrerscheins?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fuehrerschein.getId().intValue())))
            .andExpect(jsonPath("$.[*].klasse").value(hasItem(DEFAULT_KLASSE.toString())));
    }

    @Test
    @Transactional
    public void getFuehrerschein() throws Exception {
        // Initialize the database
        fuehrerscheinRepository.saveAndFlush(fuehrerschein);

        // Get the fuehrerschein
        restFuehrerscheinMockMvc.perform(get("/api/fuehrerscheins/{id}", fuehrerschein.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fuehrerschein.getId().intValue()))
            .andExpect(jsonPath("$.klasse").value(DEFAULT_KLASSE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFuehrerschein() throws Exception {
        // Get the fuehrerschein
        restFuehrerscheinMockMvc.perform(get("/api/fuehrerscheins/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFuehrerschein() throws Exception {
        // Initialize the database
        fuehrerscheinRepository.saveAndFlush(fuehrerschein);
        int databaseSizeBeforeUpdate = fuehrerscheinRepository.findAll().size();

        // Update the fuehrerschein
        Fuehrerschein updatedFuehrerschein = fuehrerscheinRepository.findOne(fuehrerschein.getId());
        updatedFuehrerschein
            .klasse(UPDATED_KLASSE);

        restFuehrerscheinMockMvc.perform(put("/api/fuehrerscheins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFuehrerschein)))
            .andExpect(status().isOk());

        // Validate the Fuehrerschein in the database
        List<Fuehrerschein> fuehrerscheinList = fuehrerscheinRepository.findAll();
        assertThat(fuehrerscheinList).hasSize(databaseSizeBeforeUpdate);
        Fuehrerschein testFuehrerschein = fuehrerscheinList.get(fuehrerscheinList.size() - 1);
        assertThat(testFuehrerschein.getKlasse()).isEqualTo(UPDATED_KLASSE);
    }

    @Test
    @Transactional
    public void updateNonExistingFuehrerschein() throws Exception {
        int databaseSizeBeforeUpdate = fuehrerscheinRepository.findAll().size();

        // Create the Fuehrerschein

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFuehrerscheinMockMvc.perform(put("/api/fuehrerscheins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fuehrerschein)))
            .andExpect(status().isCreated());

        // Validate the Fuehrerschein in the database
        List<Fuehrerschein> fuehrerscheinList = fuehrerscheinRepository.findAll();
        assertThat(fuehrerscheinList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFuehrerschein() throws Exception {
        // Initialize the database
        fuehrerscheinRepository.saveAndFlush(fuehrerschein);
        int databaseSizeBeforeDelete = fuehrerscheinRepository.findAll().size();

        // Get the fuehrerschein
        restFuehrerscheinMockMvc.perform(delete("/api/fuehrerscheins/{id}", fuehrerschein.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Fuehrerschein> fuehrerscheinList = fuehrerscheinRepository.findAll();
        assertThat(fuehrerscheinList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fuehrerschein.class);
        Fuehrerschein fuehrerschein1 = new Fuehrerschein();
        fuehrerschein1.setId(1L);
        Fuehrerschein fuehrerschein2 = new Fuehrerschein();
        fuehrerschein2.setId(fuehrerschein1.getId());
        assertThat(fuehrerschein1).isEqualTo(fuehrerschein2);
        fuehrerschein2.setId(2L);
        assertThat(fuehrerschein1).isNotEqualTo(fuehrerschein2);
        fuehrerschein1.setId(null);
        assertThat(fuehrerschein1).isNotEqualTo(fuehrerschein2);
    }
}
