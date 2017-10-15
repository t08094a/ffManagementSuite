package de.leif.ffmanagementsuite.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Dienststellung.
 */
@Entity
@Table(name = "dienststellung")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Dienststellung implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3)
    @Column(name = "titel", nullable = false)
    private String titel;

    @NotNull
    @Size(min = 2)
    @Column(name = "abkuerzung", nullable = false)
    private String abkuerzung;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitel() {
        return titel;
    }

    public Dienststellung titel(String titel) {
        this.titel = titel;
        return this;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getAbkuerzung() {
        return abkuerzung;
    }

    public Dienststellung abkuerzung(String abkuerzung) {
        this.abkuerzung = abkuerzung;
        return this;
    }

    public void setAbkuerzung(String abkuerzung) {
        this.abkuerzung = abkuerzung;
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
        Dienststellung dienststellung = (Dienststellung) o;
        if (dienststellung.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dienststellung.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Dienststellung{" +
            "id=" + getId() +
            ", titel='" + getTitel() + "'" +
            ", abkuerzung='" + getAbkuerzung() + "'" +
            "}";
    }
}
