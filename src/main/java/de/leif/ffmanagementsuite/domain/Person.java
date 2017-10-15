package de.leif.ffmanagementsuite.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import de.leif.ffmanagementsuite.domain.enumeration.Mitgliedsstatus;

/**
 * A Person.
 */
@Entity
@Table(name = "person")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3)
    @Column(name = "vorname", nullable = false)
    private String vorname;

    @NotNull
    @Size(min = 3)
    @Column(name = "nachname", nullable = false)
    private String nachname;

    @NotNull
    @Column(name = "geburtsdatum", nullable = false)
    private LocalDate geburtsdatum;

    @NotNull
    @Size(min = 3)
    @Column(name = "strasse", nullable = false)
    private String strasse;

    @NotNull
    @Min(value = 1)
    @Column(name = "hausnummer", nullable = false)
    private Integer hausnummer;

    @Column(name = "appendix")
    private String appendix;

    @NotNull
    @Size(min = 5, max = 5)
    @Column(name = "postleitzahl", length = 5, nullable = false)
    private String postleitzahl;

    @NotNull
    @Size(min = 3)
    @Column(name = "ort", nullable = false)
    private String ort;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Mitgliedsstatus status;

    @Column(name = "ehrung_25_jahre")
    private LocalDate ehrung25Jahre;

    @Column(name = "ehrung_40_jahre")
    private LocalDate ehrung40Jahre;

    @OneToOne
    @JoinColumn(unique = true)
    private Organisationsstruktur zugehoerigkeit;

    @OneToMany(mappedBy = "person")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Erreichbarkeit> erreichbarkeitens = new HashSet<>();

    @OneToMany(mappedBy = "person")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Email> emails = new HashSet<>();

    @OneToMany(mappedBy = "person")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Dienstzeit> dienstzeitens = new HashSet<>();

    @OneToMany(mappedBy = "person")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Ausbildung> ausbildungens = new HashSet<>();

    @OneToMany(mappedBy = "besitzer")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Schutzausruestung> schutzausruestungs = new HashSet<>();

    @ManyToOne
    private Dienststellung inDienststellung;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "person_fuehrerscheine",
               joinColumns = @JoinColumn(name="people_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="fuehrerscheines_id", referencedColumnName="id"))
    private Set<Fuehrerschein> fuehrerscheines = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "person_verfuegbarkeiten",
               joinColumns = @JoinColumn(name="people_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="verfuegbarkeitens_id", referencedColumnName="id"))
    private Set<Verfuegbarkeit> verfuegbarkeitens = new HashSet<>();

    @ManyToMany(mappedBy = "teilnehmers")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Leistungspruefung> leistungspruefungens = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVorname() {
        return vorname;
    }

    public Person vorname(String vorname) {
        this.vorname = vorname;
        return this;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public Person nachname(String nachname) {
        this.nachname = nachname;
        return this;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public LocalDate getGeburtsdatum() {
        return geburtsdatum;
    }

    public Person geburtsdatum(LocalDate geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
        return this;
    }

    public void setGeburtsdatum(LocalDate geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public String getStrasse() {
        return strasse;
    }

    public Person strasse(String strasse) {
        this.strasse = strasse;
        return this;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public Integer getHausnummer() {
        return hausnummer;
    }

    public Person hausnummer(Integer hausnummer) {
        this.hausnummer = hausnummer;
        return this;
    }

    public void setHausnummer(Integer hausnummer) {
        this.hausnummer = hausnummer;
    }

    public String getAppendix() {
        return appendix;
    }

    public Person appendix(String appendix) {
        this.appendix = appendix;
        return this;
    }

    public void setAppendix(String appendix) {
        this.appendix = appendix;
    }

    public String getPostleitzahl() {
        return postleitzahl;
    }

    public Person postleitzahl(String postleitzahl) {
        this.postleitzahl = postleitzahl;
        return this;
    }

    public void setPostleitzahl(String postleitzahl) {
        this.postleitzahl = postleitzahl;
    }

    public String getOrt() {
        return ort;
    }

    public Person ort(String ort) {
        this.ort = ort;
        return this;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public Mitgliedsstatus getStatus() {
        return status;
    }

    public Person status(Mitgliedsstatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(Mitgliedsstatus status) {
        this.status = status;
    }

    public LocalDate getEhrung25Jahre() {
        return ehrung25Jahre;
    }

    public Person ehrung25Jahre(LocalDate ehrung25Jahre) {
        this.ehrung25Jahre = ehrung25Jahre;
        return this;
    }

    public void setEhrung25Jahre(LocalDate ehrung25Jahre) {
        this.ehrung25Jahre = ehrung25Jahre;
    }

    public LocalDate getEhrung40Jahre() {
        return ehrung40Jahre;
    }

    public Person ehrung40Jahre(LocalDate ehrung40Jahre) {
        this.ehrung40Jahre = ehrung40Jahre;
        return this;
    }

    public void setEhrung40Jahre(LocalDate ehrung40Jahre) {
        this.ehrung40Jahre = ehrung40Jahre;
    }

    public Organisationsstruktur getZugehoerigkeit() {
        return zugehoerigkeit;
    }

    public Person zugehoerigkeit(Organisationsstruktur organisationsstruktur) {
        this.zugehoerigkeit = organisationsstruktur;
        return this;
    }

    public void setZugehoerigkeit(Organisationsstruktur organisationsstruktur) {
        this.zugehoerigkeit = organisationsstruktur;
    }

    public Set<Erreichbarkeit> getErreichbarkeitens() {
        return erreichbarkeitens;
    }

    public Person erreichbarkeitens(Set<Erreichbarkeit> erreichbarkeits) {
        this.erreichbarkeitens = erreichbarkeits;
        return this;
    }

    public Person addErreichbarkeiten(Erreichbarkeit erreichbarkeit) {
        this.erreichbarkeitens.add(erreichbarkeit);
        erreichbarkeit.setPerson(this);
        return this;
    }

    public Person removeErreichbarkeiten(Erreichbarkeit erreichbarkeit) {
        this.erreichbarkeitens.remove(erreichbarkeit);
        erreichbarkeit.setPerson(null);
        return this;
    }

    public void setErreichbarkeitens(Set<Erreichbarkeit> erreichbarkeits) {
        this.erreichbarkeitens = erreichbarkeits;
    }

    public Set<Email> getEmails() {
        return emails;
    }

    public Person emails(Set<Email> emails) {
        this.emails = emails;
        return this;
    }

    public Person addEmails(Email email) {
        this.emails.add(email);
        email.setPerson(this);
        return this;
    }

    public Person removeEmails(Email email) {
        this.emails.remove(email);
        email.setPerson(null);
        return this;
    }

    public void setEmails(Set<Email> emails) {
        this.emails = emails;
    }

    public Set<Dienstzeit> getDienstzeitens() {
        return dienstzeitens;
    }

    public Person dienstzeitens(Set<Dienstzeit> dienstzeits) {
        this.dienstzeitens = dienstzeits;
        return this;
    }

    public Person addDienstzeiten(Dienstzeit dienstzeit) {
        this.dienstzeitens.add(dienstzeit);
        dienstzeit.setPerson(this);
        return this;
    }

    public Person removeDienstzeiten(Dienstzeit dienstzeit) {
        this.dienstzeitens.remove(dienstzeit);
        dienstzeit.setPerson(null);
        return this;
    }

    public void setDienstzeitens(Set<Dienstzeit> dienstzeits) {
        this.dienstzeitens = dienstzeits;
    }

    public Set<Ausbildung> getAusbildungens() {
        return ausbildungens;
    }

    public Person ausbildungens(Set<Ausbildung> ausbildungs) {
        this.ausbildungens = ausbildungs;
        return this;
    }

    public Person addAusbildungen(Ausbildung ausbildung) {
        this.ausbildungens.add(ausbildung);
        ausbildung.setPerson(this);
        return this;
    }

    public Person removeAusbildungen(Ausbildung ausbildung) {
        this.ausbildungens.remove(ausbildung);
        ausbildung.setPerson(null);
        return this;
    }

    public void setAusbildungens(Set<Ausbildung> ausbildungs) {
        this.ausbildungens = ausbildungs;
    }

    public Set<Schutzausruestung> getSchutzausruestungs() {
        return schutzausruestungs;
    }

    public Person schutzausruestungs(Set<Schutzausruestung> schutzausruestungs) {
        this.schutzausruestungs = schutzausruestungs;
        return this;
    }

    public Person addSchutzausruestung(Schutzausruestung schutzausruestung) {
        this.schutzausruestungs.add(schutzausruestung);
        schutzausruestung.setBesitzer(this);
        return this;
    }

    public Person removeSchutzausruestung(Schutzausruestung schutzausruestung) {
        this.schutzausruestungs.remove(schutzausruestung);
        schutzausruestung.setBesitzer(null);
        return this;
    }

    public void setSchutzausruestungs(Set<Schutzausruestung> schutzausruestungs) {
        this.schutzausruestungs = schutzausruestungs;
    }

    public Dienststellung getInDienststellung() {
        return inDienststellung;
    }

    public Person inDienststellung(Dienststellung dienststellung) {
        this.inDienststellung = dienststellung;
        return this;
    }

    public void setInDienststellung(Dienststellung dienststellung) {
        this.inDienststellung = dienststellung;
    }

    public Set<Fuehrerschein> getFuehrerscheines() {
        return fuehrerscheines;
    }

    public Person fuehrerscheines(Set<Fuehrerschein> fuehrerscheins) {
        this.fuehrerscheines = fuehrerscheins;
        return this;
    }

    public Person addFuehrerscheine(Fuehrerschein fuehrerschein) {
        this.fuehrerscheines.add(fuehrerschein);
        return this;
    }

    public Person removeFuehrerscheine(Fuehrerschein fuehrerschein) {
        this.fuehrerscheines.remove(fuehrerschein);
        return this;
    }

    public void setFuehrerscheines(Set<Fuehrerschein> fuehrerscheins) {
        this.fuehrerscheines = fuehrerscheins;
    }

    public Set<Verfuegbarkeit> getVerfuegbarkeitens() {
        return verfuegbarkeitens;
    }

    public Person verfuegbarkeitens(Set<Verfuegbarkeit> verfuegbarkeits) {
        this.verfuegbarkeitens = verfuegbarkeits;
        return this;
    }

    public Person addVerfuegbarkeiten(Verfuegbarkeit verfuegbarkeit) {
        this.verfuegbarkeitens.add(verfuegbarkeit);
        return this;
    }

    public Person removeVerfuegbarkeiten(Verfuegbarkeit verfuegbarkeit) {
        this.verfuegbarkeitens.remove(verfuegbarkeit);
        return this;
    }

    public void setVerfuegbarkeitens(Set<Verfuegbarkeit> verfuegbarkeits) {
        this.verfuegbarkeitens = verfuegbarkeits;
    }

    public Set<Leistungspruefung> getLeistungspruefungens() {
        return leistungspruefungens;
    }

    public Person leistungspruefungens(Set<Leistungspruefung> leistungspruefungs) {
        this.leistungspruefungens = leistungspruefungs;
        return this;
    }

    public Person addLeistungspruefungen(Leistungspruefung leistungspruefung) {
        this.leistungspruefungens.add(leistungspruefung);
        leistungspruefung.getTeilnehmers().add(this);
        return this;
    }

    public Person removeLeistungspruefungen(Leistungspruefung leistungspruefung) {
        this.leistungspruefungens.remove(leistungspruefung);
        leistungspruefung.getTeilnehmers().remove(this);
        return this;
    }

    public void setLeistungspruefungens(Set<Leistungspruefung> leistungspruefungs) {
        this.leistungspruefungens = leistungspruefungs;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Person person = (Person) o;
        if (person.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), person.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Person{" +
            "id=" + getId() +
            ", vorname='" + getVorname() + "'" +
            ", nachname='" + getNachname() + "'" +
            ", geburtsdatum='" + getGeburtsdatum() + "'" +
            ", strasse='" + getStrasse() + "'" +
            ", hausnummer='" + getHausnummer() + "'" +
            ", appendix='" + getAppendix() + "'" +
            ", postleitzahl='" + getPostleitzahl() + "'" +
            ", ort='" + getOrt() + "'" +
            ", status='" + getStatus() + "'" +
            ", ehrung25Jahre='" + getEhrung25Jahre() + "'" +
            ", ehrung40Jahre='" + getEhrung40Jahre() + "'" +
            "}";
    }
}
