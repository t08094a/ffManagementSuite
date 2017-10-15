package de.leif.ffmanagementsuite.web.rest;

import de.leif.ffmanagementsuite.FfManagementSuiteApp;

import de.leif.ffmanagementsuite.domain.Organisationsstruktur;
import de.leif.ffmanagementsuite.repository.OrganisationsstrukturRepository;
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
 * Test class for the OrganisationsstrukturResource REST controller.
 *
 * @see OrganisationsstrukturResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FfManagementSuiteApp.class)
public class OrganisationsstrukturResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private OrganisationsstrukturRepository organisationsstrukturRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOrganisationsstrukturMockMvc;

    private Organisationsstruktur organisationsstruktur;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrganisationsstrukturResource organisationsstrukturResource = new OrganisationsstrukturResource(organisationsstrukturRepository);
        this.restOrganisationsstrukturMockMvc = MockMvcBuilders.standaloneSetup(organisationsstrukturResource)
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
    public static Organisationsstruktur createEntity(EntityManager em) {
        Organisationsstruktur organisationsstruktur = new Organisationsstruktur()
            .name(DEFAULT_NAME);
        return organisationsstruktur;
    }

    @Before
    public void initTest() {
        organisationsstruktur = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrganisationsstruktur() throws Exception {
        int databaseSizeBeforeCreate = organisationsstrukturRepository.findAll().size();

        // Create the Organisationsstruktur
        restOrganisationsstrukturMockMvc.perform(post("/api/organisationsstrukturs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organisationsstruktur)))
            .andExpect(status().isCreated());

        // Validate the Organisationsstruktur in the database
        List<Organisationsstruktur> organisationsstrukturList = organisationsstrukturRepository.findAll();
        assertThat(organisationsstrukturList).hasSize(databaseSizeBeforeCreate + 1);
        Organisationsstruktur testOrganisationsstruktur = organisationsstrukturList.get(organisationsstrukturList.size() - 1);
        assertThat(testOrganisationsstruktur.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createOrganisationsstrukturWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = organisationsstrukturRepository.findAll().size();

        // Create the Organisationsstruktur with an existing ID
        organisationsstruktur.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganisationsstrukturMockMvc.perform(post("/api/organisationsstrukturs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organisationsstruktur)))
            .andExpect(status().isBadRequest());

        // Validate the Organisationsstruktur in the database
        List<Organisationsstruktur> organisationsstrukturList = organisationsstrukturRepository.findAll();
        assertThat(organisationsstrukturList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = organisationsstrukturRepository.findAll().size();
        // set the field null
        organisationsstruktur.setName(null);

        // Create the Organisationsstruktur, which fails.

        restOrganisationsstrukturMockMvc.perform(post("/api/organisationsstrukturs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organisationsstruktur)))
            .andExpect(status().isBadRequest());

        List<Organisationsstruktur> organisationsstrukturList = organisationsstrukturRepository.findAll();
        assertThat(organisationsstrukturList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOrganisationsstrukturs() throws Exception {
        // Initialize the database
        organisationsstrukturRepository.saveAndFlush(organisationsstruktur);

        // Get all the organisationsstrukturList
        restOrganisationsstrukturMockMvc.perform(get("/api/organisationsstrukturs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organisationsstruktur.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getOrganisationsstruktur() throws Exception {
        // Initialize the database
        organisationsstrukturRepository.saveAndFlush(organisationsstruktur);

        // Get the organisationsstruktur
        restOrganisationsstrukturMockMvc.perform(get("/api/organisationsstrukturs/{id}", organisationsstruktur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(organisationsstruktur.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrganisationsstruktur() throws Exception {
        // Get the organisationsstruktur
        restOrganisationsstrukturMockMvc.perform(get("/api/organisationsstrukturs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrganisationsstruktur() throws Exception {
        // Initialize the database
        organisationsstrukturRepository.saveAndFlush(organisationsstruktur);
        int databaseSizeBeforeUpdate = organisationsstrukturRepository.findAll().size();

        // Update the organisationsstruktur
        Organisationsstruktur updatedOrganisationsstruktur = organisationsstrukturRepository.findOne(organisationsstruktur.getId());
        updatedOrganisationsstruktur
            .name(UPDATED_NAME);

        restOrganisationsstrukturMockMvc.perform(put("/api/organisationsstrukturs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrganisationsstruktur)))
            .andExpect(status().isOk());

        // Validate the Organisationsstruktur in the database
        List<Organisationsstruktur> organisationsstrukturList = organisationsstrukturRepository.findAll();
        assertThat(organisationsstrukturList).hasSize(databaseSizeBeforeUpdate);
        Organisationsstruktur testOrganisationsstruktur = organisationsstrukturList.get(organisationsstrukturList.size() - 1);
        assertThat(testOrganisationsstruktur.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingOrganisationsstruktur() throws Exception {
        int databaseSizeBeforeUpdate = organisationsstrukturRepository.findAll().size();

        // Create the Organisationsstruktur

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOrganisationsstrukturMockMvc.perform(put("/api/organisationsstrukturs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organisationsstruktur)))
            .andExpect(status().isCreated());

        // Validate the Organisationsstruktur in the database
        List<Organisationsstruktur> organisationsstrukturList = organisationsstrukturRepository.findAll();
        assertThat(organisationsstrukturList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOrganisationsstruktur() throws Exception {
        // Initialize the database
        organisationsstrukturRepository.saveAndFlush(organisationsstruktur);
        int databaseSizeBeforeDelete = organisationsstrukturRepository.findAll().size();

        // Get the organisationsstruktur
        restOrganisationsstrukturMockMvc.perform(delete("/api/organisationsstrukturs/{id}", organisationsstruktur.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Organisationsstruktur> organisationsstrukturList = organisationsstrukturRepository.findAll();
        assertThat(organisationsstrukturList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Organisationsstruktur.class);
        Organisationsstruktur organisationsstruktur1 = new Organisationsstruktur();
        organisationsstruktur1.setId(1L);
        Organisationsstruktur organisationsstruktur2 = new Organisationsstruktur();
        organisationsstruktur2.setId(organisationsstruktur1.getId());
        assertThat(organisationsstruktur1).isEqualTo(organisationsstruktur2);
        organisationsstruktur2.setId(2L);
        assertThat(organisationsstruktur1).isNotEqualTo(organisationsstruktur2);
        organisationsstruktur1.setId(null);
        assertThat(organisationsstruktur1).isNotEqualTo(organisationsstruktur2);
    }
}
