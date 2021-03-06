package de.leif.ffmanagementsuite.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DurchfuehrungWartung.
 */
@Entity
@Table(name = "durchfuehrung_wartung")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DurchfuehrungWartung implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "datum", nullable = false)
    private LocalDate datum;

    @Column(name = "kosten")
    private Integer kosten;

    @ManyToOne
    private Inventar inventar;

    @ManyToOne
    private Schutzausruestung schutzausruestung;

    @ManyToOne
    private Fahrzeug fahrzeug;

    @ManyToOne
    private AtemschutzInventar atemschutzInventar;

    @OneToOne
    @JoinColumn(unique = true)
    private Wartung definition;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public DurchfuehrungWartung datum(LocalDate datum) {
        this.datum = datum;
        return this;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public Integer getKosten() {
        return kosten;
    }

    public DurchfuehrungWartung kosten(Integer kosten) {
        this.kosten = kosten;
        return this;
    }

    public void setKosten(Integer kosten) {
        this.kosten = kosten;
    }

    public Inventar getInventar() {
        return inventar;
    }

    public DurchfuehrungWartung inventar(Inventar inventar) {
        this.inventar = inventar;
        return this;
    }

    public void setInventar(Inventar inventar) {
        this.inventar = inventar;
    }

    public Schutzausruestung getSchutzausruestung() {
        return schutzausruestung;
    }

    public DurchfuehrungWartung schutzausruestung(Schutzausruestung schutzausruestung) {
        this.schutzausruestung = schutzausruestung;
        return this;
    }

    public void setSchutzausruestung(Schutzausruestung schutzausruestung) {
        this.schutzausruestung = schutzausruestung;
    }

    public Fahrzeug getFahrzeug() {
        return fahrzeug;
    }

    public DurchfuehrungWartung fahrzeug(Fahrzeug fahrzeug) {
        this.fahrzeug = fahrzeug;
        return this;
    }

    public void setFahrzeug(Fahrzeug fahrzeug) {
        this.fahrzeug = fahrzeug;
    }

    public AtemschutzInventar getAtemschutzInventar() {
        return atemschutzInventar;
    }

    public DurchfuehrungWartung atemschutzInventar(AtemschutzInventar atemschutzInventar) {
        this.atemschutzInventar = atemschutzInventar;
        return this;
    }

    public void setAtemschutzInventar(AtemschutzInventar atemschutzInventar) {
        this.atemschutzInventar = atemschutzInventar;
    }

    public Wartung getDefinition() {
        return definition;
    }

    public DurchfuehrungWartung definition(Wartung wartung) {
        this.definition = wartung;
        return this;
    }

    public void setDefinition(Wartung wartung) {
        this.definition = wartung;
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
        DurchfuehrungWartung durchfuehrungWartung = (DurchfuehrungWartung) o;
        if (durchfuehrungWartung.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), durchfuehrungWartung.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DurchfuehrungWartung{" +
            "id=" + getId() +
            ", datum='" + getDatum() + "'" +
            ", kosten='" + getKosten() + "'" +
            "}";
    }
}
