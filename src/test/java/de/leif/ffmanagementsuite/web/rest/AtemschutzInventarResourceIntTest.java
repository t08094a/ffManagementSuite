package de.leif.ffmanagementsuite.web.rest;

import de.leif.ffmanagementsuite.FfManagementSuiteApp;

import de.leif.ffmanagementsuite.domain.AtemschutzInventar;
import de.leif.ffmanagementsuite.repository.AtemschutzInventarRepository;
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
 * Test class for the AtemschutzInventarResource REST controller.
 *
 * @see AtemschutzInventarResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FfManagementSuiteApp.class)
public class AtemschutzInventarResourceIntTest {

    private static final Integer DEFAULT_NUMMER = 1;
    private static final Integer UPDATED_NUMMER = 2;

    private static final LocalDate DEFAULT_ANGESCHAFFT_AM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ANGESCHAFFT_AM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_AUSGEMUSTERT_AM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_AUSGEMUSTERT_AM = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private AtemschutzInventarRepository atemschutzInventarRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAtemschutzInventarMockMvc;

    private AtemschutzInventar atemschutzInventar;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AtemschutzInventarResource atemschutzInventarResource = new AtemschutzInventarResource(atemschutzInventarRepository);
        this.restAtemschutzInventarMockMvc = MockMvcBuilders.standaloneSetup(atemschutzInventarResource)
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
    public static AtemschutzInventar createEntity(EntityManager em) {
        AtemschutzInventar atemschutzInventar = new AtemschutzInventar()
            .nummer(DEFAULT_NUMMER)
            .angeschafftAm(DEFAULT_ANGESCHAFFT_AM)
            .ausgemustertAm(DEFAULT_AUSGEMUSTERT_AM);
        return atemschutzInventar;
    }

    @Before
    public void initTest() {
        atemschutzInventar = createEntity(em);
    }

    @Test
    @Transactional
    public void createAtemschutzInventar() throws Exception {
        int databaseSizeBeforeCreate = atemschutzInventarRepository.findAll().size();

        // Create the AtemschutzInventar
        restAtemschutzInventarMockMvc.perform(post("/api/atemschutz-inventars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(atemschutzInventar)))
            .andExpect(status().isCreated());

        // Validate the AtemschutzInventar in the database
        List<AtemschutzInventar> atemschutzInventarList = atemschutzInventarRepository.findAll();
        assertThat(atemschutzInventarList).hasSize(databaseSizeBeforeCreate + 1);
        AtemschutzInventar testAtemschutzInventar = atemschutzInventarList.get(atemschutzInventarList.size() - 1);
        assertThat(testAtemschutzInventar.getNummer()).isEqualTo(DEFAULT_NUMMER);
        assertThat(testAtemschutzInventar.getAngeschafftAm()).isEqualTo(DEFAULT_ANGESCHAFFT_AM);
        assertThat(testAtemschutzInventar.getAusgemustertAm()).isEqualTo(DEFAULT_AUSGEMUSTERT_AM);
    }

    @Test
    @Transactional
    public void createAtemschutzInventarWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = atemschutzInventarRepository.findAll().size();

        // Create the AtemschutzInventar with an existing ID
        atemschutzInventar.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAtemschutzInventarMockMvc.perform(post("/api/atemschutz-inventars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(atemschutzInventar)))
            .andExpect(status().isBadRequest());

        // Validate the AtemschutzInventar in the database
        List<AtemschutzInventar> atemschutzInventarList = atemschutzInventarRepository.findAll();
        assertThat(atemschutzInventarList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNummerIsRequired() throws Exception {
        int databaseSizeBeforeTest = atemschutzInventarRepository.findAll().size();
        // set the field null
        atemschutzInventar.setNummer(null);

        // Create the AtemschutzInventar, which fails.

        restAtemschutzInventarMockMvc.perform(post("/api/atemschutz-inventars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(atemschutzInventar)))
            .andExpect(status().isBadRequest());

        List<AtemschutzInventar> atemschutzInventarList = atemschutzInventarRepository.findAll();
        assertThat(atemschutzInventarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAtemschutzInventars() throws Exception {
        // Initialize the database
        atemschutzInventarRepository.saveAndFlush(atemschutzInventar);

        // Get all the atemschutzInventarList
        restAtemschutzInventarMockMvc.perform(get("/api/atemschutz-inventars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(atemschutzInventar.getId().intValue())))
            .andExpect(jsonPath("$.[*].nummer").value(hasItem(DEFAULT_NUMMER)))
            .andExpect(jsonPath("$.[*].angeschafftAm").value(hasItem(DEFAULT_ANGESCHAFFT_AM.toString())))
            .andExpect(jsonPath("$.[*].ausgemustertAm").value(hasItem(DEFAULT_AUSGEMUSTERT_AM.toString())));
    }

    @Test
    @Transactional
    public void getAtemschutzInventar() throws Exception {
        // Initialize the database
        atemschutzInventarRepository.saveAndFlush(atemschutzInventar);

        // Get the atemschutzInventar
        restAtemschutzInventarMockMvc.perform(get("/api/atemschutz-inventars/{id}", atemschutzInventar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(atemschutzInventar.getId().intValue()))
            .andExpect(jsonPath("$.nummer").value(DEFAULT_NUMMER))
            .andExpect(jsonPath("$.angeschafftAm").value(DEFAULT_ANGESCHAFFT_AM.toString()))
            .andExpect(jsonPath("$.ausgemustertAm").value(DEFAULT_AUSGEMUSTERT_AM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAtemschutzInventar() throws Exception {
        // Get the atemschutzInventar
        restAtemschutzInventarMockMvc.perform(get("/api/atemschutz-inventars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAtemschutzInventar() throws Exception {
        // Initialize the database
        atemschutzInventarRepository.saveAndFlush(atemschutzInventar);
        int databaseSizeBeforeUpdate = atemschutzInventarRepository.findAll().size();

        // Update the atemschutzInventar
        AtemschutzInventar updatedAtemschutzInventar = atemschutzInventarRepository.findOne(atemschutzInventar.getId());
        updatedAtemschutzInventar
            .nummer(UPDATED_NUMMER)
            .angeschafftAm(UPDATED_ANGESCHAFFT_AM)
            .ausgemustertAm(UPDATED_AUSGEMUSTERT_AM);

        restAtemschutzInventarMockMvc.perform(put("/api/atemschutz-inventars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAtemschutzInventar)))
            .andExpect(status().isOk());

        // Validate the AtemschutzInventar in the database
        List<AtemschutzInventar> atemschutzInventarList = atemschutzInventarRepository.findAll();
        assertThat(atemschutzInventarList).hasSize(databaseSizeBeforeUpdate);
        AtemschutzInventar testAtemschutzInventar = atemschutzInventarList.get(atemschutzInventarList.size() - 1);
        assertThat(testAtemschutzInventar.getNummer()).isEqualTo(UPDATED_NUMMER);
        assertThat(testAtemschutzInventar.getAngeschafftAm()).isEqualTo(UPDATED_ANGESCHAFFT_AM);
        assertThat(testAtemschutzInventar.getAusgemustertAm()).isEqualTo(UPDATED_AUSGEMUSTERT_AM);
    }

    @Test
    @Transactional
    public void updateNonExistingAtemschutzInventar() throws Exception {
        int databaseSizeBeforeUpdate = atemschutzInventarRepository.findAll().size();

        // Create the AtemschutzInventar

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAtemschutzInventarMockMvc.perform(put("/api/atemschutz-inventars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(atemschutzInventar)))
            .andExpect(status().isCreated());

        // Validate the AtemschutzInventar in the database
        List<AtemschutzInventar> atemschutzInventarList = atemschutzInventarRepository.findAll();
        assertThat(atemschutzInventarList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAtemschutzInventar() throws Exception {
        // Initialize the database
        atemschutzInventarRepository.saveAndFlush(atemschutzInventar);
        int databaseSizeBeforeDelete = atemschutzInventarRepository.findAll().size();

        // Get the atemschutzInventar
        restAtemschutzInventarMockMvc.perform(delete("/api/atemschutz-inventars/{id}", atemschutzInventar.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AtemschutzInventar> atemschutzInventarList = atemschutzInventarRepository.findAll();
        assertThat(atemschutzInventarList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AtemschutzInventar.class);
        AtemschutzInventar atemschutzInventar1 = new AtemschutzInventar();
        atemschutzInventar1.setId(1L);
        AtemschutzInventar atemschutzInventar2 = new AtemschutzInventar();
        atemschutzInventar2.setId(atemschutzInventar1.getId());
        assertThat(atemschutzInventar1).isEqualTo(atemschutzInventar2);
        atemschutzInventar2.setId(2L);
        assertThat(atemschutzInventar1).isNotEqualTo(atemschutzInventar2);
        atemschutzInventar1.setId(null);
        assertThat(atemschutzInventar1).isNotEqualTo(atemschutzInventar2);
    }
}
