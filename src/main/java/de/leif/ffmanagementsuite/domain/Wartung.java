package de.leif.ffmanagementsuite.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import de.leif.ffmanagementsuite.domain.enumeration.IntervallEinheit;

/**
 * A Wartung.
 */
@Entity
@Table(name = "wartung")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Wartung implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "bezeichnung", nullable = false)
    private String bezeichnung;

    @NotNull
    @Column(name = "beginn", nullable = false)
    private LocalDate beginn;

    @Column(name = "letzte_wartung")
    private LocalDate letzteWartung;

    @NotNull
    @Min(value = 1)
    @Column(name = "intervall_wert", nullable = false)
    private Integer intervallWert;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "intervall_einheit", nullable = false)
    private IntervallEinheit intervallEinheit;

    @NotNull
    @Min(value = 0)
    @Column(name = "kosten", nullable = false)
    private Integer kosten;

    @ManyToOne
    private InventarKategorie inventarKategorie;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public Wartung bezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
        return this;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public LocalDate getBeginn() {
        return beginn;
    }

    public Wartung beginn(LocalDate beginn) {
        this.beginn = beginn;
        return this;
    }

    public void setBeginn(LocalDate beginn) {
        this.beginn = beginn;
    }

    public LocalDate getLetzteWartung() {
        return letzteWartung;
    }

    public Wartung letzteWartung(LocalDate letzteWartung) {
        this.letzteWartung = letzteWartung;
        return this;
    }

    public void setLetzteWartung(LocalDate letzteWartung) {
        this.letzteWartung = letzteWartung;
    }

    public Integer getIntervallWert() {
        return intervallWert;
    }

    public Wartung intervallWert(Integer intervallWert) {
        this.intervallWert = intervallWert;
        return this;
    }

    public void setIntervallWert(Integer intervallWert) {
        this.intervallWert = intervallWert;
    }

    public IntervallEinheit getIntervallEinheit() {
        return intervallEinheit;
    }

    public Wartung intervallEinheit(IntervallEinheit intervallEinheit) {
        this.intervallEinheit = intervallEinheit;
        return this;
    }

    public void setIntervallEinheit(IntervallEinheit intervallEinheit) {
        this.intervallEinheit = intervallEinheit;
    }

    public Integer getKosten() {
        return kosten;
    }

    public Wartung kosten(Integer kosten) {
        this.kosten = kosten;
        return this;
    }

    public void setKosten(Integer kosten) {
        this.kosten = kosten;
    }

    public InventarKategorie getInventarKategorie() {
        return inventarKategorie;
    }

    public Wartung inventarKategorie(InventarKategorie inventarKategorie) {
        this.inventarKategorie = inventarKategorie;
        return this;
    }

    public void setInventarKategorie(InventarKategorie inventarKategorie) {
        this.inventarKategorie = inventarKategorie;
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
        Wartung wartung = (Wartung) o;
        if (wartung.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), wartung.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Wartung{" +
            "id=" + getId() +
            ", bezeichnung='" + getBezeichnung() + "'" +
            ", beginn='" + getBeginn() + "'" +
            ", letzteWartung='" + getLetzteWartung() + "'" +
            ", intervallWert='" + getIntervallWert() + "'" +
            ", intervallEinheit='" + getIntervallEinheit() + "'" +
            ", kosten='" + getKosten() + "'" +
            "}";
    }
}
