package de.leif.ffmanagementsuite.web.rest;

import de.leif.ffmanagementsuite.FfManagementSuiteApp;

import de.leif.ffmanagementsuite.domain.InventarKategorie;
import de.leif.ffmanagementsuite.repository.InventarKategorieRepository;
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
 * Test class for the InventarKategorieResource REST controller.
 *
 * @see InventarKategorieResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FfManagementSuiteApp.class)
public class InventarKategorieResourceIntTest {

    private static final String DEFAULT_TITEL = "AAAAAAAAAA";
    private static final String UPDATED_TITEL = "BBBBBBBBBB";

    @Autowired
    private InventarKategorieRepository inventarKategorieRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInventarKategorieMockMvc;

    private InventarKategorie inventarKategorie;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InventarKategorieResource inventarKategorieResource = new InventarKategorieResource(inventarKategorieRepository);
        this.restInventarKategorieMockMvc = MockMvcBuilders.standaloneSetup(inventarKategorieResource)
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
    public static InventarKategorie createEntity(EntityManager em) {
        InventarKategorie inventarKategorie = new InventarKategorie()
            .titel(DEFAULT_TITEL);
        return inventarKategorie;
    }

    @Before
    public void initTest() {
        inventarKategorie = createEntity(em);
    }

    @Test
    @Transactional
    public void createInventarKategorie() throws Exception {
        int databaseSizeBeforeCreate = inventarKategorieRepository.findAll().size();

        // Create the InventarKategorie
        restInventarKategorieMockMvc.perform(post("/api/inventar-kategories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventarKategorie)))
            .andExpect(status().isCreated());

        // Validate the InventarKategorie in the database
        List<InventarKategorie> inventarKategorieList = inventarKategorieRepository.findAll();
        assertThat(inventarKategorieList).hasSize(databaseSizeBeforeCreate + 1);
        InventarKategorie testInventarKategorie = inventarKategorieList.get(inventarKategorieList.size() - 1);
        assertThat(testInventarKategorie.getTitel()).isEqualTo(DEFAULT_TITEL);
    }

    @Test
    @Transactional
    public void createInventarKategorieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inventarKategorieRepository.findAll().size();

        // Create the InventarKategorie with an existing ID
        inventarKategorie.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInventarKategorieMockMvc.perform(post("/api/inventar-kategories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventarKategorie)))
            .andExpect(status().isBadRequest());

        // Validate the InventarKategorie in the database
        List<InventarKategorie> inventarKategorieList = inventarKategorieRepository.findAll();
        assertThat(inventarKategorieList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitelIsRequired() throws Exception {
        int databaseSizeBeforeTest = inventarKategorieRepository.findAll().size();
        // set the field null
        inventarKategorie.setTitel(null);

        // Create the InventarKategorie, which fails.

        restInventarKategorieMockMvc.perform(post("/api/inventar-kategories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventarKategorie)))
            .andExpect(status().isBadRequest());

        List<InventarKategorie> inventarKategorieList = inventarKategorieRepository.findAll();
        assertThat(inventarKategorieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInventarKategories() throws Exception {
        // Initialize the database
        inventarKategorieRepository.saveAndFlush(inventarKategorie);

        // Get all the inventarKategorieList
        restInventarKategorieMockMvc.perform(get("/api/inventar-kategories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventarKategorie.getId().intValue())))
            .andExpect(jsonPath("$.[*].titel").value(hasItem(DEFAULT_TITEL.toString())));
    }

    @Test
    @Transactional
    public void getInventarKategorie() throws Exception {
        // Initialize the database
        inventarKategorieRepository.saveAndFlush(inventarKategorie);

        // Get the inventarKategorie
        restInventarKategorieMockMvc.perform(get("/api/inventar-kategories/{id}", inventarKategorie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(inventarKategorie.getId().intValue()))
            .andExpect(jsonPath("$.titel").value(DEFAULT_TITEL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInventarKategorie() throws Exception {
        // Get the inventarKategorie
        restInventarKategorieMockMvc.perform(get("/api/inventar-kategories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInventarKategorie() throws Exception {
        // Initialize the database
        inventarKategorieRepository.saveAndFlush(inventarKategorie);
        int databaseSizeBeforeUpdate = inventarKategorieRepository.findAll().size();

        // Update the inventarKategorie
        InventarKategorie updatedInventarKategorie = inventarKategorieRepository.findOne(inventarKategorie.getId());
        updatedInventarKategorie
            .titel(UPDATED_TITEL);

        restInventarKategorieMockMvc.perform(put("/api/inventar-kategories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInventarKategorie)))
            .andExpect(status().isOk());

        // Validate the InventarKategorie in the database
        List<InventarKategorie> inventarKategorieList = inventarKategorieRepository.findAll();
        assertThat(inventarKategorieList).hasSize(databaseSizeBeforeUpdate);
        InventarKategorie testInventarKategorie = inventarKategorieList.get(inventarKategorieList.size() - 1);
        assertThat(testInventarKategorie.getTitel()).isEqualTo(UPDATED_TITEL);
    }

    @Test
    @Transactional
    public void updateNonExistingInventarKategorie() throws Exception {
        int databaseSizeBeforeUpdate = inventarKategorieRepository.findAll().size();

        // Create the InventarKategorie

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInventarKategorieMockMvc.perform(put("/api/inventar-kategories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventarKategorie)))
            .andExpect(status().isCreated());

        // Validate the InventarKategorie in the database
        List<InventarKategorie> inventarKategorieList = inventarKategorieRepository.findAll();
        assertThat(inventarKategorieList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInventarKategorie() throws Exception {
        // Initialize the database
        inventarKategorieRepository.saveAndFlush(inventarKategorie);
        int databaseSizeBeforeDelete = inventarKategorieRepository.findAll().size();

        // Get the inventarKategorie
        restInventarKategorieMockMvc.perform(delete("/api/inventar-kategories/{id}", inventarKategorie.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<InventarKategorie> inventarKategorieList = inventarKategorieRepository.findAll();
        assertThat(inventarKategorieList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InventarKategorie.class);
        InventarKategorie inventarKategorie1 = new InventarKategorie();
        inventarKategorie1.setId(1L);
        InventarKategorie inventarKategorie2 = new InventarKategorie();
        inventarKategorie2.setId(inventarKategorie1.getId());
        assertThat(inventarKategorie1).isEqualTo(inventarKategorie2);
        inventarKategorie2.setId(2L);
        assertThat(inventarKategorie1).isNotEqualTo(inventarKategorie2);
        inventarKategorie1.setId(null);
        assertThat(inventarKategorie1).isNotEqualTo(inventarKategorie2);
    }
}
