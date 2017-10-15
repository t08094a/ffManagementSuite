package de.leif.ffmanagementsuite.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import de.leif.ffmanagementsuite.domain.enumeration.Berichtskategorie;

/**
 * A Berichtselement.
 */
@Entity
@Table(name = "berichtselement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Berichtselement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "beginn", nullable = false)
    private Instant beginn;

    @NotNull
    @Column(name = "ende", nullable = false)
    private Instant ende;

    @NotNull
    @Column(name = "titel", nullable = false)
    private String titel;

    @NotNull
    @Column(name = "beschreibung", nullable = false)
    private String beschreibung;

    @NotNull
    @Min(value = 0)
    @Column(name = "stunden", nullable = false)
    private Integer stunden;

    @Enumerated(EnumType.STRING)
    @Column(name = "kategorie")
    private Berichtskategorie kategorie;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getBeginn() {
        return beginn;
    }

    public Berichtselement beginn(Instant beginn) {
        this.beginn = beginn;
        return this;
    }

    public void setBeginn(Instant beginn) {
        this.beginn = beginn;
    }

    public Instant getEnde() {
        return ende;
    }

    public Berichtselement ende(Instant ende) {
        this.ende = ende;
        return this;
    }

    public void setEnde(Instant ende) {
        this.ende = ende;
    }

    public String getTitel() {
        return titel;
    }

    public Berichtselement titel(String titel) {
        this.titel = titel;
        return this;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public Berichtselement beschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
        return this;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public Integer getStunden() {
        return stunden;
    }

    public Berichtselement stunden(Integer stunden) {
        this.stunden = stunden;
        return this;
    }

    public void setStunden(Integer stunden) {
        this.stunden = stunden;
    }

    public Berichtskategorie getKategorie() {
        return kategorie;
    }

    public Berichtselement kategorie(Berichtskategorie kategorie) {
        this.kategorie = kategorie;
        return this;
    }

    public void setKategorie(Berichtskategorie kategorie) {
        this.kategorie = kategorie;
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
        Berichtselement berichtselement = (Berichtselement) o;
        if (berichtselement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), berichtselement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Berichtselement{" +
            "id=" + getId() +
            ", beginn='" + getBeginn() + "'" +
            ", ende='" + getEnde() + "'" +
            ", titel='" + getTitel() + "'" +
            ", beschreibung='" + getBeschreibung() + "'" +
            ", stunden='" + getStunden() + "'" +
            ", kategorie='" + getKategorie() + "'" +
            "}";
    }
}
