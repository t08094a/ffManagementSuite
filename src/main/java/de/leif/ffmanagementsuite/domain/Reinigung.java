package de.leif.ffmanagementsuite.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Reinigung.
 */
@Entity
@Table(name = "reinigung")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Reinigung implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "durchfuehrung")
    private LocalDate durchfuehrung;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDurchfuehrung() {
        return durchfuehrung;
    }

    public Reinigung durchfuehrung(LocalDate durchfuehrung) {
        this.durchfuehrung = durchfuehrung;
        return this;
    }

    public void setDurchfuehrung(LocalDate durchfuehrung) {
        this.durchfuehrung = durchfuehrung;
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
        Reinigung reinigung = (Reinigung) o;
        if (reinigung.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reinigung.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Reinigung{" +
            "id=" + getId() +
            ", durchfuehrung='" + getDurchfuehrung() + "'" +
            "}";
    }
}
