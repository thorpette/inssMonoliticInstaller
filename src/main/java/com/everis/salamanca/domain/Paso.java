package com.everis.salamanca.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.everis.salamanca.domain.enumeration.Command;

/**
 * A Paso.
 */
@Entity
@Table(name = "paso")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Paso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "command")
    private Command command;

    @Column(name = "origen")
    private String origen;

    @Column(name = "destino")
    private String destino;

    @Column(name = "parametro")
    private String parametro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("pasos")
    private Instalacion instalacion;

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

    public Paso name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Command getCommand() {
        return command;
    }

    public Paso command(Command command) {
        this.command = command;
        return this;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public String getOrigen() {
        return origen;
    }

    public Paso origen(String origen) {
        this.origen = origen;
        return this;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public Paso destino(String destino) {
        this.destino = destino;
        return this;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getParametro() {
        return parametro;
    }

    public Paso parametro(String parametro) {
        this.parametro = parametro;
        return this;
    }

    public void setParametro(String parametro) {
        this.parametro = parametro;
    }

    public Instalacion getInstalacion() {
        return instalacion;
    }

    public Paso instalacion(Instalacion instalacion) {
        this.instalacion = instalacion;
        return this;
    }

    public void setInstalacion(Instalacion instalacion) {
        this.instalacion = instalacion;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Paso)) {
            return false;
        }
        return id != null && id.equals(((Paso) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Paso{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", command='" + getCommand() + "'" +
            ", origen='" + getOrigen() + "'" +
            ", destino='" + getDestino() + "'" +
            ", parametro='" + getParametro() + "'" +
            "}";
    }
}
