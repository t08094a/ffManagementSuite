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
 * A Schutzausruestung.
 */
@Entity
@Table(name = "schutzausruestung")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Schutzausruestung implements Serializable {

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
    @Column(name = "groesse", nullable = false)
    private String groesse;

    @OneToOne
    @JoinColumn(unique = true)
    private InventarKategorie kategorie;

    @OneToOne
    @JoinColumn(unique = true)
    private Auspraegung auspraegung;

    @OneToMany(mappedBy = "schutzausruestung")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DurchfuehrungWartung> durchgefuehrteWartungens = new HashSet<>();

    @OneToMany(mappedBy = "schutzausruestung")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DurchfuehrungPruefung> durchgefuehrtePruefungens = new HashSet<>();

    @ManyToOne
    private Person besitzer;

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

    public Schutzausruestung nummer(Integer nummer) {
        this.nummer = nummer;
        return this;
    }

    public void setNummer(Integer nummer) {
        this.nummer = nummer;
    }

    public LocalDate getAngeschafftAm() {
        return angeschafftAm;
    }

    public Schutzausruestung angeschafftAm(LocalDate angeschafftAm) {
        this.angeschafftAm = angeschafftAm;
        return this;
    }

    public void setAngeschafftAm(LocalDate angeschafftAm) {
        this.angeschafftAm = angeschafftAm;
    }

    public LocalDate getAusgemustertAm() {
        return ausgemustertAm;
    }

    public Schutzausruestung ausgemustertAm(LocalDate ausgemustertAm) {
        this.ausgemustertAm = ausgemustertAm;
        return this;
    }

    public void setAusgemustertAm(LocalDate ausgemustertAm) {
        this.ausgemustertAm = ausgemustertAm;
    }

    public String getGroesse() {
        return groesse;
    }

    public Schutzausruestung groesse(String groesse) {
        this.groesse = groesse;
        return this;
    }

    public void setGroesse(String groesse) {
        this.groesse = groesse;
    }

    public InventarKategorie getKategorie() {
        return kategorie;
    }

    public Schutzausruestung kategorie(InventarKategorie inventarKategorie) {
        this.kategorie = inventarKategorie;
        return this;
    }

    public void setKategorie(InventarKategorie inventarKategorie) {
        this.kategorie = inventarKategorie;
    }

    public Auspraegung getAuspraegung() {
        return auspraegung;
    }

    public Schutzausruestung auspraegung(Auspraegung auspraegung) {
        this.auspraegung = auspraegung;
        return this;
    }

    public void setAuspraegung(Auspraegung auspraegung) {
        this.auspraegung = auspraegung;
    }

    public Set<DurchfuehrungWartung> getDurchgefuehrteWartungens() {
        return durchgefuehrteWartungens;
    }

    public Schutzausruestung durchgefuehrteWartungens(Set<DurchfuehrungWartung> durchfuehrungWartungs) {
        this.durchgefuehrteWartungens = durchfuehrungWartungs;
        return this;
    }

    public Schutzausruestung addDurchgefuehrteWartungen(DurchfuehrungWartung durchfuehrungWartung) {
        this.durchgefuehrteWartungens.add(durchfuehrungWartung);
        durchfuehrungWartung.setSchutzausruestung(this);
        return this;
    }

    public Schutzausruestung removeDurchgefuehrteWartungen(DurchfuehrungWartung durchfuehrungWartung) {
        this.durchgefuehrteWartungens.remove(durchfuehrungWartung);
        durchfuehrungWartung.setSchutzausruestung(null);
        return this;
    }

    public void setDurchgefuehrteWartungens(Set<DurchfuehrungWartung> durchfuehrungWartungs) {
        this.durchgefuehrteWartungens = durchfuehrungWartungs;
    }

    public Set<DurchfuehrungPruefung> getDurchgefuehrtePruefungens() {
        return durchgefuehrtePruefungens;
    }

    public Schutzausruestung durchgefuehrtePruefungens(Set<DurchfuehrungPruefung> durchfuehrungPruefungs) {
        this.durchgefuehrtePruefungens = durchfuehrungPruefungs;
        return this;
    }

    public Schutzausruestung addDurchgefuehrtePruefungen(DurchfuehrungPruefung durchfuehrungPruefung) {
        this.durchgefuehrtePruefungens.add(durchfuehrungPruefung);
        durchfuehrungPruefung.setSchutzausruestung(this);
        return this;
    }

    public Schutzausruestung removeDurchgefuehrtePruefungen(DurchfuehrungPruefung durchfuehrungPruefung) {
        this.durchgefuehrtePruefungens.remove(durchfuehrungPruefung);
        durchfuehrungPruefung.setSchutzausruestung(null);
        return this;
    }

    public void setDurchgefuehrtePruefungens(Set<DurchfuehrungPruefung> durchfuehrungPruefungs) {
        this.durchgefuehrtePruefungens = durchfuehrungPruefungs;
    }

    public Person getBesitzer() {
        return besitzer;
    }

    public Schutzausruestung besitzer(Person person) {
        this.besitzer = person;
        return this;
    }

    public void setBesitzer(Person person) {
        this.besitzer = person;
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
        Schutzausruestung schutzausruestung = (Schutzausruestung) o;
        if (schutzausruestung.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), schutzausruestung.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Schutzausruestung{" +
            "id=" + getId() +
            ", nummer='" + getNummer() + "'" +
            ", angeschafftAm='" + getAngeschafftAm() + "'" +
            ", ausgemustertAm='" + getAusgemustertAm() + "'" +
            ", groesse='" + getGroesse() + "'" +
            "}";
    }
}
