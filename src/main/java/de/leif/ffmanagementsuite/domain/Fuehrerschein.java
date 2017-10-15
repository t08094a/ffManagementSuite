package de.leif.ffmanagementsuite.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Fuehrerschein.
 */
@Entity
@Table(name = "fuehrerschein")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fuehrerschein implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1)
    @Column(name = "klasse", nullable = false)
    private String klasse;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKlasse() {
        return klasse;
    }

    public Fuehrerschein klasse(String klasse) {
        this.klasse = klasse;
        return this;
    }

    public void setKlasse(String klasse) {
        this.klasse = klasse;
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
        Fuehrerschein fuehrerschein = (Fuehrerschein) o;
        if (fuehrerschein.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fuehrerschein.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Fuehrerschein{" +
            "id=" + getId() +
            ", klasse='" + getKlasse() + "'" +
            "}";
    }
}
