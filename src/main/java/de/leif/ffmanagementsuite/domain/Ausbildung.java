package de.leif.ffmanagementsuite.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Ausbildung.
 */
@Entity
@Table(name = "ausbildung")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ausbildung implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "beginn", nullable = false)
    private LocalDate beginn;

    @NotNull
    @Column(name = "ende", nullable = false)
    private LocalDate ende;

    @Lob
    @Column(name = "zeugnis")
    private byte[] zeugnis;

    @Column(name = "zeugnis_content_type")
    private String zeugnisContentType;

    @ManyToOne
    private Person person;

    @ManyToOne
    private Lehrgang lehrgang;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getBeginn() {
        return beginn;
    }

    public Ausbildung beginn(LocalDate beginn) {
        this.beginn = beginn;
        return this;
    }

    public void setBeginn(LocalDate beginn) {
        this.beginn = beginn;
    }

    public LocalDate getEnde() {
        return ende;
    }

    public Ausbildung ende(LocalDate ende) {
        this.ende = ende;
        return this;
    }

    public void setEnde(LocalDate ende) {
        this.ende = ende;
    }

    public byte[] getZeugnis() {
        return zeugnis;
    }

    public Ausbildung zeugnis(byte[] zeugnis) {
        this.zeugnis = zeugnis;
        return this;
    }

    public void setZeugnis(byte[] zeugnis) {
        this.zeugnis = zeugnis;
    }

    public String getZeugnisContentType() {
        return zeugnisContentType;
    }

    public Ausbildung zeugnisContentType(String zeugnisContentType) {
        this.zeugnisContentType = zeugnisContentType;
        return this;
    }

    public void setZeugnisContentType(String zeugnisContentType) {
        this.zeugnisContentType = zeugnisContentType;
    }

    public Person getPerson() {
        return person;
    }

    public Ausbildung person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Lehrgang getLehrgang() {
        return lehrgang;
    }

    public Ausbildung lehrgang(Lehrgang lehrgang) {
        this.lehrgang = lehrgang;
        return this;
    }

    public void setLehrgang(Lehrgang lehrgang) {
        this.lehrgang = lehrgang;
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
        Ausbildung ausbildung = (Ausbildung) o;
        if (ausbildung.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ausbildung.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Ausbildung{" +
            "id=" + getId() +
            ", beginn='" + getBeginn() + "'" +
            ", ende='" + getEnde() + "'" +
            ", zeugnis='" + getZeugnis() + "'" +
            ", zeugnisContentType='" + zeugnisContentType + "'" +
            "}";
    }
}
