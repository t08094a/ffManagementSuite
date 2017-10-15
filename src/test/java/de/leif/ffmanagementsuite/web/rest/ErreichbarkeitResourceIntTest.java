package de.leif.ffmanagementsuite.web.rest;

import de.leif.ffmanagementsuite.FfManagementSuiteApp;

import de.leif.ffmanagementsuite.domain.Erreichbarkeit;
import de.leif.ffmanagementsuite.repository.ErreichbarkeitRepository;
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
 * Test class for the ErreichbarkeitResource REST controller.
 *
 * @see ErreichbarkeitResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FfManagementSuiteApp.class)
public class ErreichbarkeitResourceIntTest {

    private static final String DEFAULT_TYP = "AAAAAAAAAA";
    private static final String UPDATED_TYP = "BBBBBBBBBB";

    private static final String DEFAULT_VORWAHL = "AAAAAAAAAA";
    private static final String UPDATED_VORWAHL = "BBBBBBBBBB";

    private static final String DEFAULT_RUFNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_RUFNUMMER = "BBBBBBBBBB";

    @Autowired
    private ErreichbarkeitRepository erreichbarkeitRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restErreichbarkeitMockMvc;

    private Erreichbarkeit erreichbarkeit;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ErreichbarkeitResource erreichbarkeitResource = new ErreichbarkeitResource(erreichbarkeitRepository);
        this.restErreichbarkeitMockMvc = MockMvcBuilders.standaloneSetup(erreichbarkeitResource)
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
    public static Erreichbarkeit createEntity(EntityManager em) {
        Erreichbarkeit erreichbarkeit = new Erreichbarkeit()
            .typ(DEFAULT_TYP)
            .vorwahl(DEFAULT_VORWAHL)
            .rufnummer(DEFAULT_RUFNUMMER);
        return erreichbarkeit;
    }

    @Before
    public void initTest() {
        erreichbarkeit = createEntity(em);
    }

    @Test
    @Transactional
    public void createErreichbarkeit() throws Exception {
        int databaseSizeBeforeCreate = erreichbarkeitRepository.findAll().size();

        // Create the Erreichbarkeit
        restErreichbarkeitMockMvc.perform(post("/api/erreichbarkeits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(erreichbarkeit)))
            .andExpect(status().isCreated());

        // Validate the Erreichbarkeit in the database
        List<Erreichbarkeit> erreichbarkeitList = erreichbarkeitRepository.findAll();
        assertThat(erreichbarkeitList).hasSize(databaseSizeBeforeCreate + 1);
        Erreichbarkeit testErreichbarkeit = erreichbarkeitList.get(erreichbarkeitList.size() - 1);
        assertThat(testErreichbarkeit.getTyp()).isEqualTo(DEFAULT_TYP);
        assertThat(testErreichbarkeit.getVorwahl()).isEqualTo(DEFAULT_VORWAHL);
        assertThat(testErreichbarkeit.getRufnummer()).isEqualTo(DEFAULT_RUFNUMMER);
    }

    @Test
    @Transactional
    public void createErreichbarkeitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = erreichbarkeitRepository.findAll().size();

        // Create the Erreichbarkeit with an existing ID
        erreichbarkeit.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restErreichbarkeitMockMvc.perform(post("/api/erreichbarkeits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(erreichbarkeit)))
            .andExpect(status().isBadRequest());

        // Validate the Erreichbarkeit in the database
        List<Erreichbarkeit> erreichbarkeitList = erreichbarkeitRepository.findAll();
        assertThat(erreichbarkeitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTypIsRequired() throws Exception {
        int databaseSizeBeforeTest = erreichbarkeitRepository.findAll().size();
        // set the field null
        erreichbarkeit.setTyp(null);

        // Create the Erreichbarkeit, which fails.

        restErreichbarkeitMockMvc.perform(post("/api/erreichbarkeits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(erreichbarkeit)))
            .andExpect(status().isBadRequest());

        List<Erreichbarkeit> erreichbarkeitList = erreichbarkeitRepository.findAll();
        assertThat(erreichbarkeitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVorwahlIsRequired() throws Exception {
        int databaseSizeBeforeTest = erreichbarkeitRepository.findAll().size();
        // set the field null
        erreichbarkeit.setVorwahl(null);

        // Create the Erreichbarkeit, which fails.

        restErreichbarkeitMockMvc.perform(post("/api/erreichbarkeits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(erreichbarkeit)))
            .andExpect(status().isBadRequest());

        List<Erreichbarkeit> erreichbarkeitList = erreichbarkeitRepository.findAll();
        assertThat(erreichbarkeitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRufnummerIsRequired() throws Exception {
        int databaseSizeBeforeTest = erreichbarkeitRepository.findAll().size();
        // set the field null
        erreichbarkeit.setRufnummer(null);

        // Create the Erreichbarkeit, which fails.

        restErreichbarkeitMockMvc.perform(post("/api/erreichbarkeits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(erreichbarkeit)))
            .andExpect(status().isBadRequest());

        List<Erreichbarkeit> erreichbarkeitList = erreichbarkeitRepository.findAll();
        assertThat(erreichbarkeitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllErreichbarkeits() throws Exception {
        // Initialize the database
        erreichbarkeitRepository.saveAndFlush(erreichbarkeit);

        // Get all the erreichbarkeitList
        restErreichbarkeitMockMvc.perform(get("/api/erreichbarkeits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(erreichbarkeit.getId().intValue())))
            .andExpect(jsonPath("$.[*].typ").value(hasItem(DEFAULT_TYP.toString())))
            .andExpect(jsonPath("$.[*].vorwahl").value(hasItem(DEFAULT_VORWAHL.toString())))
            .andExpect(jsonPath("$.[*].rufnummer").value(hasItem(DEFAULT_RUFNUMMER.toString())));
    }

    @Test
    @Transactional
    public void getErreichbarkeit() throws Exception {
        // Initialize the database
        erreichbarkeitRepository.saveAndFlush(erreichbarkeit);

        // Get the erreichbarkeit
        restErreichbarkeitMockMvc.perform(get("/api/erreichbarkeits/{id}", erreichbarkeit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(erreichbarkeit.getId().intValue()))
            .andExpect(jsonPath("$.typ").value(DEFAULT_TYP.toString()))
            .andExpect(jsonPath("$.vorwahl").value(DEFAULT_VORWAHL.toString()))
            .andExpect(jsonPath("$.rufnummer").value(DEFAULT_RUFNUMMER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingErreichbarkeit() throws Exception {
        // Get the erreichbarkeit
        restErreichbarkeitMockMvc.perform(get("/api/erreichbarkeits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateErreichbarkeit() throws Exception {
        // Initialize the database
        erreichbarkeitRepository.saveAndFlush(erreichbarkeit);
        int databaseSizeBeforeUpdate = erreichbarkeitRepository.findAll().size();

        // Update the erreichbarkeit
        Erreichbarkeit updatedErreichbarkeit = erreichbarkeitRepository.findOne(erreichbarkeit.getId());
        updatedErreichbarkeit
            .typ(UPDATED_TYP)
            .vorwahl(UPDATED_VORWAHL)
            .rufnummer(UPDATED_RUFNUMMER);

        restErreichbarkeitMockMvc.perform(put("/api/erreichbarkeits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedErreichbarkeit)))
            .andExpect(status().isOk());

        // Validate the Erreichbarkeit in the database
        List<Erreichbarkeit> erreichbarkeitList = erreichbarkeitRepository.findAll();
        assertThat(erreichbarkeitList).hasSize(databaseSizeBeforeUpdate);
        Erreichbarkeit testErreichbarkeit = erreichbarkeitList.get(erreichbarkeitList.size() - 1);
        assertThat(testErreichbarkeit.getTyp()).isEqualTo(UPDATED_TYP);
        assertThat(testErreichbarkeit.getVorwahl()).isEqualTo(UPDATED_VORWAHL);
        assertThat(testErreichbarkeit.getRufnummer()).isEqualTo(UPDATED_RUFNUMMER);
    }

    @Test
    @Transactional
    public void updateNonExistingErreichbarkeit() throws Exception {
        int databaseSizeBeforeUpdate = erreichbarkeitRepository.findAll().size();

        // Create the Erreichbarkeit

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restErreichbarkeitMockMvc.perform(put("/api/erreichbarkeits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(erreichbarkeit)))
            .andExpect(status().isCreated());

        // Validate the Erreichbarkeit in the database
        List<Erreichbarkeit> erreichbarkeitList = erreichbarkeitRepository.findAll();
        assertThat(erreichbarkeitList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteErreichbarkeit() throws Exception {
        // Initialize the database
        erreichbarkeitRepository.saveAndFlush(erreichbarkeit);
        int databaseSizeBeforeDelete = erreichbarkeitRepository.findAll().size();

        // Get the erreichbarkeit
        restErreichbarkeitMockMvc.perform(delete("/api/erreichbarkeits/{id}", erreichbarkeit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Erreichbarkeit> erreichbarkeitList = erreichbarkeitRepository.findAll();
        assertThat(erreichbarkeitList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Erreichbarkeit.class);
        Erreichbarkeit erreichbarkeit1 = new Erreichbarkeit();
        erreichbarkeit1.setId(1L);
        Erreichbarkeit erreichbarkeit2 = new Erreichbarkeit();
        erreichbarkeit2.setId(erreichbarkeit1.getId());
        assertThat(erreichbarkeit1).isEqualTo(erreichbarkeit2);
        erreichbarkeit2.setId(2L);
        assertThat(erreichbarkeit1).isNotEqualTo(erreichbarkeit2);
        erreichbarkeit1.setId(null);
        assertThat(erreichbarkeit1).isNotEqualTo(erreichbarkeit2);
    }
}
