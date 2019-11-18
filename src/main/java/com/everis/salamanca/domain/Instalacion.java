package com.everis.salamanca.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Instalacion.
 */
@Entity
@Table(name = "instalacion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Instalacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "descripcion")
    private String descripcion;

    @OneToMany(mappedBy = "instalacion")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Paso> pasos = new HashSet<>();

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

    public Instalacion name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Instalacion descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Paso> getPasos() {
        return pasos;
    }

    public Instalacion pasos(Set<Paso> pasos) {
        this.pasos = pasos;
        return this;
    }

    public Instalacion addPaso(Paso paso) {
        this.pasos.add(paso);
        paso.setInstalacion(this);
        return this;
    }

    public Instalacion removePaso(Paso paso) {
        this.pasos.remove(paso);
        paso.setInstalacion(null);
        return this;
    }

    public void setPasos(Set<Paso> pasos) {
        this.pasos = pasos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Instalacion)) {
            return false;
        }
        return id != null && id.equals(((Instalacion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Instalacion{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
