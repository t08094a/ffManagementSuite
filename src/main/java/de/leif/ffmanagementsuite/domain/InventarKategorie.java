package de.leif.ffmanagementsuite.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A InventarKategorie.
 */
@Entity
@Table(name = "inventar_kategorie")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class InventarKategorie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3)
    @Column(name = "titel", nullable = false)
    private String titel;

    @OneToMany(mappedBy = "uebergeordneteKategorie")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<InventarKategorie> unterkategories = new HashSet<>();

    @OneToMany(mappedBy = "inventarKategorie")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Wartung> wartungens = new HashSet<>();

    @OneToMany(mappedBy = "inventarKategorie")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Pruefung> pruefungens = new HashSet<>();

    @ManyToOne
    private InventarKategorie uebergeordneteKategorie;

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

    public InventarKategorie titel(String titel) {
        this.titel = titel;
        return this;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public Set<InventarKategorie> getUnterkategories() {
        return unterkategories;
    }

    public InventarKategorie unterkategories(Set<InventarKategorie> inventarKategories) {
        this.unterkategories = inventarKategories;
        return this;
    }

    public InventarKategorie addUnterkategorie(InventarKategorie inventarKategorie) {
        this.unterkategories.add(inventarKategorie);
        inventarKategorie.setUebergeordneteKategorie(this);
        return this;
    }

    public InventarKategorie removeUnterkategorie(InventarKategorie inventarKategorie) {
        this.unterkategories.remove(inventarKategorie);
        inventarKategorie.setUebergeordneteKategorie(null);
        return this;
    }

    public void setUnterkategories(Set<InventarKategorie> inventarKategories) {
        this.unterkategories = inventarKategories;
    }

    public Set<Wartung> getWartungens() {
        return wartungens;
    }

    public InventarKategorie wartungens(Set<Wartung> wartungs) {
        this.wartungens = wartungs;
        return this;
    }

    public InventarKategorie addWartungen(Wartung wartung) {
        this.wartungens.add(wartung);
        wartung.setInventarKategorie(this);
        return this;
    }

    public InventarKategorie removeWartungen(Wartung wartung) {
        this.wartungens.remove(wartung);
        wartung.setInventarKategorie(null);
        return this;
    }

    public void setWartungens(Set<Wartung> wartungs) {
        this.wartungens = wartungs;
    }

    public Set<Pruefung> getPruefungens() {
        return pruefungens;
    }

    public InventarKategorie pruefungens(Set<Pruefung> pruefungs) {
        this.pruefungens = pruefungs;
        return this;
    }

    public InventarKategorie addPruefungen(Pruefung pruefung) {
        this.pruefungens.add(pruefung);
        pruefung.setInventarKategorie(this);
        return this;
    }

    public InventarKategorie removePruefungen(Pruefung pruefung) {
        this.pruefungens.remove(pruefung);
        pruefung.setInventarKategorie(null);
        return this;
    }

    public void setPruefungens(Set<Pruefung> pruefungs) {
        this.pruefungens = pruefungs;
    }

    public InventarKategorie getUebergeordneteKategorie() {
        return uebergeordneteKategorie;
    }

    public InventarKategorie uebergeordneteKategorie(InventarKategorie inventarKategorie) {
        this.uebergeordneteKategorie = inventarKategorie;
        return this;
    }

    public void setUebergeordneteKategorie(InventarKategorie inventarKategorie) {
        this.uebergeordneteKategorie = inventarKategorie;
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
        InventarKategorie inventarKategorie = (InventarKategorie) o;
        if (inventarKategorie.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), inventarKategorie.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InventarKategorie{" +
            "id=" + getId() +
            ", titel='" + getTitel() + "'" +
            "}";
    }
}
