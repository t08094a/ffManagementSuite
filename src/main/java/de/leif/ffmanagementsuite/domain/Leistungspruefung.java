package de.leif.ffmanagementsuite.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import de.leif.ffmanagementsuite.domain.enumeration.LeistungspruefungTyp;

/**
 * A Leistungspruefung.
 */
@Entity
@Table(name = "leistungspruefung")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Leistungspruefung implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "typ", nullable = false)
    private LeistungspruefungTyp typ;

    @NotNull
    @Min(value = 1)
    @Max(value = 6)
    @Column(name = "stufe", nullable = false)
    private Integer stufe;

    @NotNull
    @Column(name = "abgelegt_am", nullable = false)
    private LocalDate abgelegtAm;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "leistungspruefung_teilnehmer",
               joinColumns = @JoinColumn(name="leistungspruefungs_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="teilnehmers_id", referencedColumnName="id"))
    private Set<Person> teilnehmers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LeistungspruefungTyp getTyp() {
        return typ;
    }

    public Leistungspruefung typ(LeistungspruefungTyp typ) {
        this.typ = typ;
        return this;
    }

    public void setTyp(LeistungspruefungTyp typ) {
        this.typ = typ;
    }

    public Integer getStufe() {
        return stufe;
    }

    public Leistungspruefung stufe(Integer stufe) {
        this.stufe = stufe;
        return this;
    }

    public void setStufe(Integer stufe) {
        this.stufe = stufe;
    }

    public LocalDate getAbgelegtAm() {
        return abgelegtAm;
    }

    public Leistungspruefung abgelegtAm(LocalDate abgelegtAm) {
        this.abgelegtAm = abgelegtAm;
        return this;
    }

    public void setAbgelegtAm(LocalDate abgelegtAm) {
        this.abgelegtAm = abgelegtAm;
    }

    public Set<Person> getTeilnehmers() {
        return teilnehmers;
    }

    public Leistungspruefung teilnehmers(Set<Person> people) {
        this.teilnehmers = people;
        return this;
    }

    public Leistungspruefung addTeilnehmer(Person person) {
        this.teilnehmers.add(person);
        person.getLeistungspruefungens().add(this);
        return this;
    }

    public Leistungspruefung removeTeilnehmer(Person person) {
        this.teilnehmers.remove(person);
        person.getLeistungspruefungens().remove(this);
        return this;
    }

    public void setTeilnehmers(Set<Person> people) {
        this.teilnehmers = people;
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
        Leistungspruefung leistungspruefung = (Leistungspruefung) o;
        if (leistungspruefung.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), leistungspruefung.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Leistungspruefung{" +
            "id=" + getId() +
            ", typ='" + getTyp() + "'" +
            ", stufe='" + getStufe() + "'" +
            ", abgelegtAm='" + getAbgelegtAm() + "'" +
            "}";
    }
}
