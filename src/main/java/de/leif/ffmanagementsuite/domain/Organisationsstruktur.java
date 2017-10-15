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
 * A Organisationsstruktur.
 */
@Entity
@Table(name = "organisationsstruktur")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Organisationsstruktur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3)
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "parent")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Organisationsstruktur> gegliedertIns = new HashSet<>();

    @ManyToOne
    private Organisationsstruktur parent;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Organisationsstruktur name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Organisationsstruktur> getGegliedertIns() {
        return gegliedertIns;
    }

    public Organisationsstruktur gegliedertIns(Set<Organisationsstruktur> organisationsstrukturs) {
        this.gegliedertIns = organisationsstrukturs;
        return this;
    }

    public Organisationsstruktur addGegliedertIn(Organisationsstruktur organisationsstruktur) {
        this.gegliedertIns.add(organisationsstruktur);
        organisationsstruktur.setParent(this);
        return this;
    }

    public Organisationsstruktur removeGegliedertIn(Organisationsstruktur organisationsstruktur) {
        this.gegliedertIns.remove(organisationsstruktur);
        organisationsstruktur.setParent(null);
        return this;
    }

    public void setGegliedertIns(Set<Organisationsstruktur> organisationsstrukturs) {
        this.gegliedertIns = organisationsstrukturs;
    }

    public Organisationsstruktur getParent() {
        return parent;
    }

    public Organisationsstruktur parent(Organisationsstruktur organisationsstruktur) {
        this.parent = organisationsstruktur;
        return this;
    }

    public void setParent(Organisationsstruktur organisationsstruktur) {
        this.parent = organisationsstruktur;
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
        Organisationsstruktur organisationsstruktur = (Organisationsstruktur) o;
        if (organisationsstruktur.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), organisationsstruktur.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Organisationsstruktur{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
