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
 * A Inventar.
 */
@Entity
@Table(name = "inventar")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Inventar implements Serializable {

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

    @OneToOne
    @JoinColumn(unique = true)
    private InventarKategorie kategorie;

    @OneToMany(mappedBy = "inventar")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DurchfuehrungWartung> durchgefuehrteWartungens = new HashSet<>();

    @OneToMany(mappedBy = "inventar")
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

    public Inventar nummer(Integer nummer) {
        this.nummer = nummer;
        return this;
    }

    public void setNummer(Integer nummer) {
        this.nummer = nummer;
    }

    public LocalDate getAngeschafftAm() {
        return angeschafftAm;
    }

    public Inventar angeschafftAm(LocalDate angeschafftAm) {
        this.angeschafftAm = angeschafftAm;
        return this;
    }

    public void setAngeschafftAm(LocalDate angeschafftAm) {
        this.angeschafftAm = angeschafftAm;
    }

    public LocalDate getAusgemustertAm() {
        return ausgemustertAm;
    }

    public Inventar ausgemustertAm(LocalDate ausgemustertAm) {
        this.ausgemustertAm = ausgemustertAm;
        return this;
    }

    public void setAusgemustertAm(LocalDate ausgemustertAm) {
        this.ausgemustertAm = ausgemustertAm;
    }

    public InventarKategorie getKategorie() {
        return kategorie;
    }

    public Inventar kategorie(InventarKategorie inventarKategorie) {
        this.kategorie = inventarKategorie;
        return this;
    }

    public void setKategorie(InventarKategorie inventarKategorie) {
        this.kategorie = inventarKategorie;
    }

    public Set<DurchfuehrungWartung> getDurchgefuehrteWartungens() {
        return durchgefuehrteWartungens;
    }

    public Inventar durchgefuehrteWartungens(Set<DurchfuehrungWartung> durchfuehrungWartungs) {
        this.durchgefuehrteWartungens = durchfuehrungWartungs;
        return this;
    }

    public Inventar addDurchgefuehrteWartungen(DurchfuehrungWartung durchfuehrungWartung) {
        this.durchgefuehrteWartungens.add(durchfuehrungWartung);
        durchfuehrungWartung.setInventar(this);
        return this;
    }

    public Inventar removeDurchgefuehrteWartungen(DurchfuehrungWartung durchfuehrungWartung) {
        this.durchgefuehrteWartungens.remove(durchfuehrungWartung);
        durchfuehrungWartung.setInventar(null);
        return this;
    }

    public void setDurchgefuehrteWartungens(Set<DurchfuehrungWartung> durchfuehrungWartungs) {
        this.durchgefuehrteWartungens = durchfuehrungWartungs;
    }

    public Set<DurchfuehrungPruefung> getDurchgefuehrtePruefungens() {
        return durchgefuehrtePruefungens;
    }

    public Inventar durchgefuehrtePruefungens(Set<DurchfuehrungPruefung> durchfuehrungPruefungs) {
        this.durchgefuehrtePruefungens = durchfuehrungPruefungs;
        return this;
    }

    public Inventar addDurchgefuehrtePruefungen(DurchfuehrungPruefung durchfuehrungPruefung) {
        this.durchgefuehrtePruefungens.add(durchfuehrungPruefung);
        durchfuehrungPruefung.setInventar(this);
        return this;
    }

    public Inventar removeDurchgefuehrtePruefungen(DurchfuehrungPruefung durchfuehrungPruefung) {
        this.durchgefuehrtePruefungens.remove(durchfuehrungPruefung);
        durchfuehrungPruefung.setInventar(null);
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
        Inventar inventar = (Inventar) o;
        if (inventar.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), inventar.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Inventar{" +
            "id=" + getId() +
            ", nummer='" + getNummer() + "'" +
            ", angeschafftAm='" + getAngeschafftAm() + "'" +
            ", ausgemustertAm='" + getAusgemustertAm() + "'" +
            "}";
    }
}
