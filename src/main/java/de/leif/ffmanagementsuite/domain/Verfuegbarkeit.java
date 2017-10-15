package de.leif.ffmanagementsuite.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import de.leif.ffmanagementsuite.domain.enumeration.Wocheneinteilung;

import de.leif.ffmanagementsuite.domain.enumeration.Tageszeit;

/**
 * A Verfuegbarkeit.
 */
@Entity
@Table(name = "verfuegbarkeit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Verfuegbarkeit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1)
    @Column(name = "titel", nullable = false)
    private String titel;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "wocheneinteilung", nullable = false)
    private Wocheneinteilung wocheneinteilung;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tageszeit", nullable = false)
    private Tageszeit tageszeit;

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

    public Verfuegbarkeit titel(String titel) {
        this.titel = titel;
        return this;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public Wocheneinteilung getWocheneinteilung() {
        return wocheneinteilung;
    }

    public Verfuegbarkeit wocheneinteilung(Wocheneinteilung wocheneinteilung) {
        this.wocheneinteilung = wocheneinteilung;
        return this;
    }

    public void setWocheneinteilung(Wocheneinteilung wocheneinteilung) {
        this.wocheneinteilung = wocheneinteilung;
    }

    public Tageszeit getTageszeit() {
        return tageszeit;
    }

    public Verfuegbarkeit tageszeit(Tageszeit tageszeit) {
        this.tageszeit = tageszeit;
        return this;
    }

    public void setTageszeit(Tageszeit tageszeit) {
        this.tageszeit = tageszeit;
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
        Verfuegbarkeit verfuegbarkeit = (Verfuegbarkeit) o;
        if (verfuegbarkeit.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), verfuegbarkeit.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Verfuegbarkeit{" +
            "id=" + getId() +
            ", titel='" + getTitel() + "'" +
            ", wocheneinteilung='" + getWocheneinteilung() + "'" +
            ", tageszeit='" + getTageszeit() + "'" +
            "}";
    }
}
