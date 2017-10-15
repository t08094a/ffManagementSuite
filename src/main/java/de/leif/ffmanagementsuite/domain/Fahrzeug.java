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

/**
 * A Fahrzeug.
 */
@Entity
@Table(name = "fahrzeug")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fahrzeug implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(value = 1)
    @Column(name = "nummer", nullable = false)
    private Integer nummer;

    @Column(name = "angeschafft_am")
    private LocalDate angeschafftAm;

    @Column(name = "ausgemustert_am")
    private LocalDate ausgemustertAm;

    @NotNull
    @Pattern(regexp = "[A-Z]{1,3}\\-[A-Z]{0,2}\\s?[0-9]+")
    @Column(name = "nummernschild", nullable = false)
    private String nummernschild;

    @NotNull
    @Pattern(regexp = "([0-9]+\\/)?[0-9]{2}\\/[0-9]+")
    @Column(name = "funkrufname", nullable = false)
    private String funkrufname;

    @OneToOne
    @JoinColumn(unique = true)
    private InventarKategorie kategorie;

    @OneToMany(mappedBy = "fahrzeug")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DurchfuehrungWartung> durchgefuehrteWartungens = new HashSet<>();

    @OneToMany(mappedBy = "fahrzeug")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DurchfuehrungPruefung> durchgefuehrtePruefungens = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNummer() {
        return nummer;
    }

    public Fahrzeug nummer(Integer nummer) {
        this.nummer = nummer;
        return this;
    }

    public void setNummer(Integer nummer) {
        this.nummer = nummer;
    }

    public LocalDate getAngeschafftAm() {
        return angeschafftAm;
    }

    public Fahrzeug angeschafftAm(LocalDate angeschafftAm) {
        this.angeschafftAm = angeschafftAm;
        return this;
    }

    public void setAngeschafftAm(LocalDate angeschafftAm) {
        this.angeschafftAm = angeschafftAm;
    }

    public LocalDate getAusgemustertAm() {
        return ausgemustertAm;
    }

    public Fahrzeug ausgemustertAm(LocalDate ausgemustertAm) {
        this.ausgemustertAm = ausgemustertAm;
        return this;
    }

    public void setAusgemustertAm(LocalDate ausgemustertAm) {
        this.ausgemustertAm = ausgemustertAm;
    }

    public String getNummernschild() {
        return nummernschild;
    }

    public Fahrzeug nummernschild(String nummernschild) {
        this.nummernschild = nummernschild;
        return this;
    }

    public void setNummernschild(String nummernschild) {
        this.nummernschild = nummernschild;
    }

    public String getFunkrufname() {
        return funkrufname;
    }

    public Fahrzeug funkrufname(String funkrufname) {
        this.funkrufname = funkrufname;
        return this;
    }

    public void setFunkrufname(String funkrufname) {
        this.funkrufname = funkrufname;
    }

    public InventarKategorie getKategorie() {
        return kategorie;
    }

    public Fahrzeug kategorie(InventarKategorie inventarKategorie) {
        this.kategorie = inventarKategorie;
        return this;
    }

    public void setKategorie(InventarKategorie inventarKategorie) {
        this.kategorie = inventarKategorie;
    }

    public Set<DurchfuehrungWartung> getDurchgefuehrteWartungens() {
        return durchgefuehrteWartungens;
    }

    public Fahrzeug durchgefuehrteWartungens(Set<DurchfuehrungWartung> durchfuehrungWartungs) {
        this.durchgefuehrteWartungens = durchfuehrungWartungs;
        return this;
    }

    public Fahrzeug addDurchgefuehrteWartungen(DurchfuehrungWartung durchfuehrungWartung) {
        this.durchgefuehrteWartungens.add(durchfuehrungWartung);
        durchfuehrungWartung.setFahrzeug(this);
        return this;
    }

    public Fahrzeug removeDurchgefuehrteWartungen(DurchfuehrungWartung durchfuehrungWartung) {
        this.durchgefuehrteWartungens.remove(durchfuehrungWartung);
        durchfuehrungWartung.setFahrzeug(null);
        return this;
    }

    public void setDurchgefuehrteWartungens(Set<DurchfuehrungWartung> durchfuehrungWartungs) {
        this.durchgefuehrteWartungens = durchfuehrungWartungs;
    }

    public Set<DurchfuehrungPruefung> getDurchgefuehrtePruefungens() {
        return durchgefuehrtePruefungens;
    }

    public Fahrzeug durchgefuehrtePruefungens(Set<DurchfuehrungPruefung> durchfuehrungPruefungs) {
        this.durchgefuehrtePruefungens = durchfuehrungPruefungs;
        return this;
    }

    public Fahrzeug addDurchgefuehrtePruefungen(DurchfuehrungPruefung durchfuehrungPruefung) {
        this.durchgefuehrtePruefungens.add(durchfuehrungPruefung);
        durchfuehrungPruefung.setFahrzeug(this);
        return this;
    }

    public Fahrzeug removeDurchgefuehrtePruefungen(DurchfuehrungPruefung durchfuehrungPruefung) {
        this.durchgefuehrtePruefungens.remove(durchfuehrungPruefung);
        durchfuehrungPruefung.setFahrzeug(null);
        return this;
    }

    public void setDurchgefuehrtePruefungens(Set<DurchfuehrungPruefung> durchfuehrungPruefungs) {
        this.durchgefuehrtePruefungens = durchfuehrungPruefungs;
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
        Fahrzeug fahrzeug = (Fahrzeug) o;
        if (fahrzeug.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fahrzeug.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Fahrzeug{" +
            "id=" + getId() +
            ", nummer='" + getNummer() + "'" +
            ", angeschafftAm='" + getAngeschafftAm() + "'" +
            ", ausgemustertAm='" + getAusgemustertAm() + "'" +
            ", nummernschild='" + getNummernschild() + "'" +
            ", funkrufname='" + getFunkrufname() + "'" +
            "}";
    }
}
