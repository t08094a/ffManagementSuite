package de.leif.ffmanagementsuite.web.rest;

import de.leif.ffmanagementsuite.FfManagementSuiteApp;

import de.leif.ffmanagementsuite.domain.Person;
import de.leif.ffmanagementsuite.repository.PersonRepository;
import de.leif.ffmanagementsuite.service.PersonService;
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

import de.leif.ffmanagementsuite.domain.enumeration.Mitgliedsstatus;
/**
 * Test class for the PersonResource REST controller.
 *
 * @see PersonResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FfManagementSuiteApp.class)
public class PersonResourceIntTest {

    private static final String DEFAULT_VORNAME = "AAAAAAAAAA";
    private static final String UPDATED_VORNAME = "BBBBBBBBBB";

    private static final String DEFAULT_NACHNAME = "AAAAAAAAAA";
    private static final String UPDATED_NACHNAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_GEBURTSDATUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_GEBURTSDATUM = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_STRASSE = "AAAAAAAAAA";
    private static final String UPDATED_STRASSE = "BBBBBBBBBB";

    private static final Integer DEFAULT_HAUSNUMMER = 1;
    private static final Integer UPDATED_HAUSNUMMER = 2;

    private static final String DEFAULT_APPENDIX = "AAAAAAAAAA";
    private static final String UPDATED_APPENDIX = "BBBBBBBBBB";

    private static final String DEFAULT_POSTLEITZAHL = "AAAAA";
    private static final String UPDATED_POSTLEITZAHL = "BBBBB";

    private static final String DEFAULT_ORT = "AAAAAAAAAA";
    private static final String UPDATED_ORT = "BBBBBBBBBB";

    private static final Mitgliedsstatus DEFAULT_STATUS = Mitgliedsstatus.AKTIV;
    private static final Mitgliedsstatus UPDATED_STATUS = Mitgliedsstatus.PASSIV;

    private static final LocalDate DEFAULT_EHRUNG_25_JAHRE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EHRUNG_25_JAHRE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_EHRUNG_40_JAHRE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EHRUNG_40_JAHRE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonService personService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPersonMockMvc;

    private Person person;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PersonResource personResource = new PersonResource(personService);
        this.restPersonMockMvc = MockMvcBuilders.standaloneSetup(personResource)
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
    public static Person createEntity(EntityManager em) {
        Person person = new Person()
            .vorname(DEFAULT_VORNAME)
            .nachname(DEFAULT_NACHNAME)
            .geburtsdatum(DEFAULT_GEBURTSDATUM)
            .strasse(DEFAULT_STRASSE)
            .hausnummer(DEFAULT_HAUSNUMMER)
            .appendix(DEFAULT_APPENDIX)
            .postleitzahl(DEFAULT_POSTLEITZAHL)
            .ort(DEFAULT_ORT)
            .status(DEFAULT_STATUS)
            .ehrung25Jahre(DEFAULT_EHRUNG_25_JAHRE)
            .ehrung40Jahre(DEFAULT_EHRUNG_40_JAHRE);
        return person;
    }

    @Before
    public void initTest() {
        person = createEntity(em);
    }

    @Test
    @Transactional
    public void createPerson() throws Exception {
        int databaseSizeBeforeCreate = personRepository.findAll().size();

        // Create the Person
        restPersonMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(person)))
            .andExpect(status().isCreated());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeCreate + 1);
        Person testPerson = personList.get(personList.size() - 1);
        assertThat(testPerson.getVorname()).isEqualTo(DEFAULT_VORNAME);
        assertThat(testPerson.getNachname()).isEqualTo(DEFAULT_NACHNAME);
        assertThat(testPerson.getGeburtsdatum()).isEqualTo(DEFAULT_GEBURTSDATUM);
        assertThat(testPerson.getStrasse()).isEqualTo(DEFAULT_STRASSE);
        assertThat(testPerson.getHausnummer()).isEqualTo(DEFAULT_HAUSNUMMER);
        assertThat(testPerson.getAppendix()).isEqualTo(DEFAULT_APPENDIX);
        assertThat(testPerson.getPostleitzahl()).isEqualTo(DEFAULT_POSTLEITZAHL);
        assertThat(testPerson.getOrt()).isEqualTo(DEFAULT_ORT);
        assertThat(testPerson.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPerson.getEhrung25Jahre()).isEqualTo(DEFAULT_EHRUNG_25_JAHRE);
        assertThat(testPerson.getEhrung40Jahre()).isEqualTo(DEFAULT_EHRUNG_40_JAHRE);
    }

    @Test
    @Transactional
    public void createPersonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personRepository.findAll().size();

        // Create the Person with an existing ID
        person.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(person)))
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkVornameIsRequired() throws Exception {
        int databaseSizeBeforeTest = personRepository.findAll().size();
        // set the field null
        person.setVorname(null);

        // Create the Person, which fails.

        restPersonMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(person)))
            .andExpect(status().isBadRequest());

        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNachnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = personRepository.findAll().size();
        // set the field null
        person.setNachname(null);

        // Create the Person, which fails.

        restPersonMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(person)))
            .andExpect(status().isBadRequest());

        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGeburtsdatumIsRequired() throws Exception {
        int databaseSizeBeforeTest = personRepository.findAll().size();
        // set the field null
        person.setGeburtsdatum(null);

        // Create the Person, which fails.

        restPersonMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(person)))
            .andExpect(status().isBadRequest());

        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStrasseIsRequired() throws Exception {
        int databaseSizeBeforeTest = personRepository.findAll().size();
        // set the field null
        person.setStrasse(null);

        // Create the Person, which fails.

        restPersonMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(person)))
            .andExpect(status().isBadRequest());

        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHausnummerIsRequired() throws Exception {
        int databaseSizeBeforeTest = personRepository.findAll().size();
        // set the field null
        person.setHausnummer(null);

        // Create the Person, which fails.

        restPersonMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(person)))
            .andExpect(status().isBadRequest());

        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPostleitzahlIsRequired() throws Exception {
        int databaseSizeBeforeTest = personRepository.findAll().size();
        // set the field null
        person.setPostleitzahl(null);

        // Create the Person, which fails.

        restPersonMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(person)))
            .andExpect(status().isBadRequest());

        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOrtIsRequired() throws Exception {
        int databaseSizeBeforeTest = personRepository.findAll().size();
        // set the field null
        person.setOrt(null);

        // Create the Person, which fails.

        restPersonMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(person)))
            .andExpect(status().isBadRequest());

        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = personRepository.findAll().size();
        // set the field null
        person.setStatus(null);

        // Create the Person, which fails.

        restPersonMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(person)))
            .andExpect(status().isBadRequest());

        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPeople() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get all the personList
        restPersonMockMvc.perform(get("/api/people?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(person.getId().intValue())))
            .andExpect(jsonPath("$.[*].vorname").value(hasItem(DEFAULT_VORNAME.toString())))
            .andExpect(jsonPath("$.[*].nachname").value(hasItem(DEFAULT_NACHNAME.toString())))
            .andExpect(jsonPath("$.[*].geburtsdatum").value(hasItem(DEFAULT_GEBURTSDATUM.toString())))
            .andExpect(jsonPath("$.[*].strasse").value(hasItem(DEFAULT_STRASSE.toString())))
            .andExpect(jsonPath("$.[*].hausnummer").value(hasItem(DEFAULT_HAUSNUMMER)))
            .andExpect(jsonPath("$.[*].appendix").value(hasItem(DEFAULT_APPENDIX.toString())))
            .andExpect(jsonPath("$.[*].postleitzahl").value(hasItem(DEFAULT_POSTLEITZAHL.toString())))
            .andExpect(jsonPath("$.[*].ort").value(hasItem(DEFAULT_ORT.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].ehrung25Jahre").value(hasItem(DEFAULT_EHRUNG_25_JAHRE.toString())))
            .andExpect(jsonPath("$.[*].ehrung40Jahre").value(hasItem(DEFAULT_EHRUNG_40_JAHRE.toString())));
    }

    @Test
    @Transactional
    public void getPerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get the person
        restPersonMockMvc.perform(get("/api/people/{id}", person.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(person.getId().intValue()))
            .andExpect(jsonPath("$.vorname").value(DEFAULT_VORNAME.toString()))
            .andExpect(jsonPath("$.nachname").value(DEFAULT_NACHNAME.toString()))
            .andExpect(jsonPath("$.geburtsdatum").value(DEFAULT_GEBURTSDATUM.toString()))
            .andExpect(jsonPath("$.strasse").value(DEFAULT_STRASSE.toString()))
            .andExpect(jsonPath("$.hausnummer").value(DEFAULT_HAUSNUMMER))
            .andExpect(jsonPath("$.appendix").value(DEFAULT_APPENDIX.toString()))
            .andExpect(jsonPath("$.postleitzahl").value(DEFAULT_POSTLEITZAHL.toString()))
            .andExpect(jsonPath("$.ort").value(DEFAULT_ORT.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.ehrung25Jahre").value(DEFAULT_EHRUNG_25_JAHRE.toString()))
            .andExpect(jsonPath("$.ehrung40Jahre").value(DEFAULT_EHRUNG_40_JAHRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPerson() throws Exception {
        // Get the person
        restPersonMockMvc.perform(get("/api/people/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePerson() throws Exception {
        // Initialize the database
        personService.save(person);

        int databaseSizeBeforeUpdate = personRepository.findAll().size();

        // Update the person
        Person updatedPerson = personRepository.findOne(person.getId());
        updatedPerson
            .vorname(UPDATED_VORNAME)
            .nachname(UPDATED_NACHNAME)
            .geburtsdatum(UPDATED_GEBURTSDATUM)
            .strasse(UPDATED_STRASSE)
            .hausnummer(UPDATED_HAUSNUMMER)
            .appendix(UPDATED_APPENDIX)
            .postleitzahl(UPDATED_POSTLEITZAHL)
            .ort(UPDATED_ORT)
            .status(UPDATED_STATUS)
            .ehrung25Jahre(UPDATED_EHRUNG_25_JAHRE)
            .ehrung40Jahre(UPDATED_EHRUNG_40_JAHRE);

        restPersonMockMvc.perform(put("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPerson)))
            .andExpect(status().isOk());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
        Person testPerson = personList.get(personList.size() - 1);
        assertThat(testPerson.getVorname()).isEqualTo(UPDATED_VORNAME);
        assertThat(testPerson.getNachname()).isEqualTo(UPDATED_NACHNAME);
        assertThat(testPerson.getGeburtsdatum()).isEqualTo(UPDATED_GEBURTSDATUM);
        assertThat(testPerson.getStrasse()).isEqualTo(UPDATED_STRASSE);
        assertThat(testPerson.getHausnummer()).isEqualTo(UPDATED_HAUSNUMMER);
        assertThat(testPerson.getAppendix()).isEqualTo(UPDATED_APPENDIX);
        assertThat(testPerson.getPostleitzahl()).isEqualTo(UPDATED_POSTLEITZAHL);
        assertThat(testPerson.getOrt()).isEqualTo(UPDATED_ORT);
        assertThat(testPerson.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPerson.getEhrung25Jahre()).isEqualTo(UPDATED_EHRUNG_25_JAHRE);
        assertThat(testPerson.getEhrung40Jahre()).isEqualTo(UPDATED_EHRUNG_40_JAHRE);
    }

    @Test
    @Transactional
    public void updateNonExistingPerson() throws Exception {
        int databaseSizeBeforeUpdate = personRepository.findAll().size();

        // Create the Person

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonMockMvc.perform(put("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(person)))
            .andExpect(status().isCreated());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePerson() throws Exception {
        // Initialize the database
        personService.save(person);

        int databaseSizeBeforeDelete = personRepository.findAll().size();

        // Get the person
        restPersonMockMvc.perform(delete("/api/people/{id}", person.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Person.class);
        Person person1 = new Person();
        person1.setId(1L);
        Person person2 = new Person();
        person2.setId(person1.getId());
        assertThat(person1).isEqualTo(person2);
        person2.setId(2L);
        assertThat(person1).isNotEqualTo(person2);
        person1.setId(null);
        assertThat(person1).isNotEqualTo(person2);
    }
}
