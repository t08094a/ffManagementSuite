package de.leif.ffmanagementsuite.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Dienstzeit.
 */
@Entity
@Table(name = "dienstzeit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Dienstzeit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "beginn", nullable = false)
    private LocalDate beginn;

    @Column(name = "ende")
    private LocalDate ende;

    @ManyToOne
    private Person person;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getBeginn() {
        return beginn;
    }

    public Dienstzeit beginn(LocalDate beginn) {
        this.beginn = beginn;
        return this;
    }

    public void setBeginn(LocalDate beginn) {
        this.beginn = beginn;
    }

    public LocalDate getEnde() {
        return ende;
    }

    public Dienstzeit ende(LocalDate ende) {
        this.ende = ende;
        return this;
    }

    public void setEnde(LocalDate ende) {
        this.ende = ende;
    }

    public Person getPerson() {
        return person;
    }

    public Dienstzeit person(Person person) {
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
        Dienstzeit dienstzeit = (Dienstzeit) o;
        if (dienstzeit.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dienstzeit.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Dienstzeit{" +
            "id=" + getId() +
            ", beginn='" + getBeginn() + "'" +
            ", ende='" + getEnde() + "'" +
            "}";
    }
}
