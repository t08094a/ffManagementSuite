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
 * A Lehrgang.
 */
@Entity
@Table(name = "lehrgang")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Lehrgang implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3)
    @Column(name = "titel", nullable = false)
    private String titel;

    @Size(min = 2)
    @Column(name = "abkuerzung")
    private String abkuerzung;

    @ManyToOne
    private Lehrgang lehrgang;

    @OneToMany(mappedBy = "lehrgang")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Lehrgang> voraussetzungens = new HashSet<>();

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

    public Lehrgang titel(String titel) {
        this.titel = titel;
        return this;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getAbkuerzung() {
        return abkuerzung;
    }

    public Lehrgang abkuerzung(String abkuerzung) {
        this.abkuerzung = abkuerzung;
        return this;
    }

    public void setAbkuerzung(String abkuerzung) {
        this.abkuerzung = abkuerzung;
    }

    public Lehrgang getLehrgang() {
        return lehrgang;
    }

    public Lehrgang lehrgang(Lehrgang lehrgang) {
        this.lehrgang = lehrgang;
        return this;
    }

    public void setLehrgang(Lehrgang lehrgang) {
        this.lehrgang = lehrgang;
    }

    public Set<Lehrgang> getVoraussetzungens() {
        return voraussetzungens;
    }

    public Lehrgang voraussetzungens(Set<Lehrgang> lehrgangs) {
        this.voraussetzungens = lehrgangs;
        return this;
    }

    public Lehrgang addVoraussetzungen(Lehrgang lehrgang) {
        this.voraussetzungens.add(lehrgang);
        lehrgang.setLehrgang(this);
        return this;
    }

    public Lehrgang removeVoraussetzungen(Lehrgang lehrgang) {
        this.voraussetzungens.remove(lehrgang);
        lehrgang.setLehrgang(null);
        return this;
    }

    public void setVoraussetzungens(Set<Lehrgang> lehrgangs) {
        this.voraussetzungens = lehrgangs;
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
        Lehrgang lehrgang = (Lehrgang) o;
        if (lehrgang.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lehrgang.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Lehrgang{" +
            "id=" + getId() +
            ", titel='" + getTitel() + "'" +
            ", abkuerzung='" + getAbkuerzung() + "'" +
            "}";
    }
}
