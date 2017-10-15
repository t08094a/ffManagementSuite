package de.leif.ffmanagementsuite.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Erreichbarkeit.
 */
@Entity
@Table(name = "erreichbarkeit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Erreichbarkeit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3)
    @Column(name = "typ", nullable = false)
    private String typ;

    @NotNull
    @Size(min = 4)
    @Column(name = "vorwahl", nullable = false)
    private String vorwahl;

    @NotNull
    @Size(min = 3)
    @Column(name = "rufnummer", nullable = false)
    private String rufnummer;

    @ManyToOne
    private Person person;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTyp() {
        return typ;
    }

    public Erreichbarkeit typ(String typ) {
        this.typ = typ;
        return this;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public String getVorwahl() {
        return vorwahl;
    }

    public Erreichbarkeit vorwahl(String vorwahl) {
        this.vorwahl = vorwahl;
        return this;
    }

    public void setVorwahl(String vorwahl) {
        this.vorwahl = vorwahl;
    }

    public String getRufnummer() {
        return rufnummer;
    }

    public Erreichbarkeit rufnummer(String rufnummer) {
        this.rufnummer = rufnummer;
        return this;
    }

    public void setRufnummer(String rufnummer) {
        this.rufnummer = rufnummer;
    }

    public Person getPerson() {
        return person;
    }

    public Erreichbarkeit person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
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
        Erreichbarkeit erreichbarkeit = (Erreichbarkeit) o;
        if (erreichbarkeit.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), erreichbarkeit.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Erreichbarkeit{" +
            "id=" + getId() +
            ", typ='" + getTyp() + "'" +
            ", vorwahl='" + getVorwahl() + "'" +
            ", rufnummer='" + getRufnummer() + "'" +
            "}";
    }
}
