package com.everis.salamanca.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.everis.salamanca.domain.enumeration.Command;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.everis.salamanca.domain.Paso} entity. This class is used
 * in {@link com.everis.salamanca.web.rest.PasoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /pasos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PasoCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Command
     */
    public static class CommandFilter extends Filter<Command> {

        public CommandFilter() {
        }

        public CommandFilter(CommandFilter filter) {
            super(filter);
        }

        @Override
        public CommandFilter copy() {
            return new CommandFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private CommandFilter command;

    private StringFilter origen;

    private StringFilter destino;

    private StringFilter parametro;

    private LongFilter instalacionId;

    public PasoCriteria(){
    }

    public PasoCriteria(PasoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.command = other.command == null ? null : other.command.copy();
        this.origen = other.origen == null ? null : other.origen.copy();
        this.destino = other.destino == null ? null : other.destino.copy();
        this.parametro = other.parametro == null ? null : other.parametro.copy();
        this.instalacionId = other.instalacionId == null ? null : other.instalacionId.copy();
    }

    @Override
    public PasoCriteria copy() {
        return new PasoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public CommandFilter getCommand() {
        return command;
    }

    public void setCommand(CommandFilter command) {
        this.command = command;
    }

    public StringFilter getOrigen() {
        return origen;
    }

    public void setOrigen(StringFilter origen) {
        this.origen = origen;
    }

    public StringFilter getDestino() {
        return destino;
    }

    public void setDestino(StringFilter destino) {
        this.destino = destino;
    }

    public StringFilter getParametro() {
        return parametro;
    }

    public void setParametro(StringFilter parametro) {
        this.parametro = parametro;
    }

    public LongFilter getInstalacionId() {
        return instalacionId;
    }

    public void setInstalacionId(LongFilter instalacionId) {
        this.instalacionId = instalacionId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PasoCriteria that = (PasoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(command, that.command) &&
            Objects.equals(origen, that.origen) &&
            Objects.equals(destino, that.destino) &&
            Objects.equals(parametro, that.parametro) &&
            Objects.equals(instalacionId, that.instalacionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        command,
        origen,
        destino,
        parametro,
        instalacionId
        );
    }

    @Override
    public String toString() {
        return "PasoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (command != null ? "command=" + command + ", " : "") +
                (origen != null ? "origen=" + origen + ", " : "") +
                (destino != null ? "destino=" + destino + ", " : "") +
                (parametro != null ? "parametro=" + parametro + ", " : "") +
                (instalacionId != null ? "instalacionId=" + instalacionId + ", " : "") +
            "}";
    }

}
