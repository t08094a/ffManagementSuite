package de.leif.ffmanagementsuite.web.rest;

import de.leif.ffmanagementsuite.FfManagementSuiteApp;

import de.leif.ffmanagementsuite.domain.Inventar;
import de.leif.ffmanagementsuite.repository.InventarRepository;
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
 * Test class for the InventarResource REST controller.
 *
 * @see InventarResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FfManagementSuiteApp.class)
public class InventarResourceIntTest {

    private static final Integer DEFAULT_NUMMER = 1;
    private static final Integer UPDATED_NUMMER = 2;

    private static final LocalDate DEFAULT_ANGESCHAFFT_AM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ANGESCHAFFT_AM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_AUSGEMUSTERT_AM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_AUSGEMUSTERT_AM = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private InventarRepository inventarRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInventarMockMvc;

    private Inventar inventar;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InventarResource inventarResource = new InventarResource(inventarRepository);
        this.restInventarMockMvc = MockMvcBuilders.standaloneSetup(inventarResource)
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
    public static Inventar createEntity(EntityManager em) {
        Inventar inventar = new Inventar()
            .nummer(DEFAULT_NUMMER)
            .angeschafftAm(DEFAULT_ANGESCHAFFT_AM)
            .ausgemustertAm(DEFAULT_AUSGEMUSTERT_AM);
        return inventar;
    }

    @Before
    public void initTest() {
        inventar = createEntity(em);
    }

    @Test
    @Transactional
    public void createInventar() throws Exception {
        int databaseSizeBeforeCreate = inventarRepository.findAll().size();

        // Create the Inventar
        restInventarMockMvc.perform(post("/api/inventars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventar)))
            .andExpect(status().isCreated());

        // Validate the Inventar in the database
        List<Inventar> inventarList = inventarRepository.findAll();
        assertThat(inventarList).hasSize(databaseSizeBeforeCreate + 1);
        Inventar testInventar = inventarList.get(inventarList.size() - 1);
        assertThat(testInventar.getNummer()).isEqualTo(DEFAULT_NUMMER);
        assertThat(testInventar.getAngeschafftAm()).isEqualTo(DEFAULT_ANGESCHAFFT_AM);
        assertThat(testInventar.getAusgemustertAm()).isEqualTo(DEFAULT_AUSGEMUSTERT_AM);
    }

    @Test
    @Transactional
    public void createInventarWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inventarRepository.findAll().size();

        // Create the Inventar with an existing ID
        inventar.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInventarMockMvc.perform(post("/api/inventars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventar)))
            .andExpect(status().isBadRequest());

        // Validate the Inventar in the database
        List<Inventar> inventarList = inventarRepository.findAll();
        assertThat(inventarList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNummerIsRequired() throws Exception {
        int databaseSizeBeforeTest = inventarRepository.findAll().size();
        // set the field null
        inventar.setNummer(null);

        // Create the Inventar, which fails.

        restInventarMockMvc.perform(post("/api/inventars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventar)))
            .andExpect(status().isBadRequest());

        List<Inventar> inventarList = inventarRepository.findAll();
        assertThat(inventarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInventars() throws Exception {
        // Initialize the database
        inventarRepository.saveAndFlush(inventar);

        // Get all the inventarList
        restInventarMockMvc.perform(get("/api/inventars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventar.getId().intValue())))
            .andExpect(jsonPath("$.[*].nummer").value(hasItem(DEFAULT_NUMMER)))
            .andExpect(jsonPath("$.[*].angeschafftAm").value(hasItem(DEFAULT_ANGESCHAFFT_AM.toString())))
            .andExpect(jsonPath("$.[*].ausgemustertAm").value(hasItem(DEFAULT_AUSGEMUSTERT_AM.toString())));
    }

    @Test
    @Transactional
    public void getInventar() throws Exception {
        // Initialize the database
        inventarRepository.saveAndFlush(inventar);

        // Get the inventar
        restInventarMockMvc.perform(get("/api/inventars/{id}", inventar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(inventar.getId().intValue()))
            .andExpect(jsonPath("$.nummer").value(DEFAULT_NUMMER))
            .andExpect(jsonPath("$.angeschafftAm").value(DEFAULT_ANGESCHAFFT_AM.toString()))
            .andExpect(jsonPath("$.ausgemustertAm").value(DEFAULT_AUSGEMUSTERT_AM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInventar() throws Exception {
        // Get the inventar
        restInventarMockMvc.perform(get("/api/inventars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInventar() throws Exception {
        // Initialize the database
        inventarRepository.saveAndFlush(inventar);
        int databaseSizeBeforeUpdate = inventarRepository.findAll().size();

        // Update the inventar
        Inventar updatedInventar = inventarRepository.findOne(inventar.getId());
        updatedInventar
            .nummer(UPDATED_NUMMER)
            .angeschafftAm(UPDATED_ANGESCHAFFT_AM)
            .ausgemustertAm(UPDATED_AUSGEMUSTERT_AM);

        restInventarMockMvc.perform(put("/api/inventars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInventar)))
            .andExpect(status().isOk());

        // Validate the Inventar in the database
        List<Inventar> inventarList = inventarRepository.findAll();
        assertThat(inventarList).hasSize(databaseSizeBeforeUpdate);
        Inventar testInventar = inventarList.get(inventarList.size() - 1);
        assertThat(testInventar.getNummer()).isEqualTo(UPDATED_NUMMER);
        assertThat(testInventar.getAngeschafftAm()).isEqualTo(UPDATED_ANGESCHAFFT_AM);
        assertThat(testInventar.getAusgemustertAm()).isEqualTo(UPDATED_AUSGEMUSTERT_AM);
    }

    @Test
    @Transactional
    public void updateNonExistingInventar() throws Exception {
        int databaseSizeBeforeUpdate = inventarRepository.findAll().size();

        // Create the Inventar

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInventarMockMvc.perform(put("/api/inventars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventar)))
            .andExpect(status().isCreated());

        // Validate the Inventar in the database
        List<Inventar> inventarList = inventarRepository.findAll();
        assertThat(inventarList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInventar() throws Exception {
        // Initialize the database
        inventarRepository.saveAndFlush(inventar);
        int databaseSizeBeforeDelete = inventarRepository.findAll().size();

        // Get the inventar
        restInventarMockMvc.perform(delete("/api/inventars/{id}", inventar.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Inventar> inventarList = inventarRepository.findAll();
        assertThat(inventarList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Inventar.class);
        Inventar inventar1 = new Inventar();
        inventar1.setId(1L);
        Inventar inventar2 = new Inventar();
        inventar2.setId(inventar1.getId());
        assertThat(inventar1).isEqualTo(inventar2);
        inventar2.setId(2L);
        assertThat(inventar1).isNotEqualTo(inventar2);
        inventar1.setId(null);
        assertThat(inventar1).isNotEqualTo(inventar2);
    }
}
