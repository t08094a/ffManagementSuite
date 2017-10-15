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
 * A AtemschutzInventar.
 */
@Entity
@Table(name = "atemschutz_inventar")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AtemschutzInventar implements Serializable {

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

    @OneToMany(mappedBy = "atemschutzInventar")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DurchfuehrungWartung> durchgefuehrteWartungens = new HashSet<>();

    @OneToMany(mappedBy = "atemschutzInventar")
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

    public AtemschutzInventar nummer(Integer nummer) {
        this.nummer = nummer;
        return this;
    }

    public void setNummer(Integer nummer) {
        this.nummer = nummer;
    }

    public LocalDate getAngeschafftAm() {
        return angeschafftAm;
    }

    public AtemschutzInventar angeschafftAm(LocalDate angeschafftAm) {
        this.angeschafftAm = angeschafftAm;
        return this;
    }

    public void setAngeschafftAm(LocalDate angeschafftAm) {
        this.angeschafftAm = angeschafftAm;
    }

    public LocalDate getAusgemustertAm() {
        return ausgemustertAm;
    }

    public AtemschutzInventar ausgemustertAm(LocalDate ausgemustertAm) {
        this.ausgemustertAm = ausgemustertAm;
        return this;
    }

    public void setAusgemustertAm(LocalDate ausgemustertAm) {
        this.ausgemustertAm = ausgemustertAm;
    }

    public InventarKategorie getKategorie() {
        return kategorie;
    }

    public AtemschutzInventar kategorie(InventarKategorie inventarKategorie) {
        this.kategorie = inventarKategorie;
        return this;
    }

    public void setKategorie(InventarKategorie inventarKategorie) {
        this.kategorie = inventarKategorie;
    }

    public Set<DurchfuehrungWartung> getDurchgefuehrteWartungens() {
        return durchgefuehrteWartungens;
    }

    public AtemschutzInventar durchgefuehrteWartungens(Set<DurchfuehrungWartung> durchfuehrungWartungs) {
        this.durchgefuehrteWartungens = durchfuehrungWartungs;
        return this;
    }

    public AtemschutzInventar addDurchgefuehrteWartungen(DurchfuehrungWartung durchfuehrungWartung) {
        this.durchgefuehrteWartungens.add(durchfuehrungWartung);
        durchfuehrungWartung.setAtemschutzInventar(this);
        return this;
    }

    public AtemschutzInventar removeDurchgefuehrteWartungen(DurchfuehrungWartung durchfuehrungWartung) {
        this.durchgefuehrteWartungens.remove(durchfuehrungWartung);
        durchfuehrungWartung.setAtemschutzInventar(null);
        return this;
    }

    public void setDurchgefuehrteWartungens(Set<DurchfuehrungWartung> durchfuehrungWartungs) {
        this.durchgefuehrteWartungens = durchfuehrungWartungs;
    }

    public Set<DurchfuehrungPruefung> getDurchgefuehrtePruefungens() {
        return durchgefuehrtePruefungens;
    }

    public AtemschutzInventar durchgefuehrtePruefungens(Set<DurchfuehrungPruefung> durchfuehrungPruefungs) {
        this.durchgefuehrtePruefungens = durchfuehrungPruefungs;
        return this;
    }

    public AtemschutzInventar addDurchgefuehrtePruefungen(DurchfuehrungPruefung durchfuehrungPruefung) {
        this.durchgefuehrtePruefungens.add(durchfuehrungPruefung);
        durchfuehrungPruefung.setAtemschutzInventar(this);
        return this;
    }

    public AtemschutzInventar removeDurchgefuehrtePruefungen(DurchfuehrungPruefung durchfuehrungPruefung) {
        this.durchgefuehrtePruefungens.remove(durchfuehrungPruefung);
        durchfuehrungPruefung.setAtemschutzInventar(null);
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
        AtemschutzInventar atemschutzInventar = (AtemschutzInventar) o;
        if (atemschutzInventar.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), atemschutzInventar.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AtemschutzInventar{" +
            "id=" + getId() +
            ", nummer='" + getNummer() + "'" +
            ", angeschafftAm='" + getAngeschafftAm() + "'" +
            ", ausgemustertAm='" + getAusgemustertAm() + "'" +
            "}";
    }
}
