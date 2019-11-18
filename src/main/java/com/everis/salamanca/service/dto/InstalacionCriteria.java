package com.everis.salamanca.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.everis.salamanca.domain.Instalacion} entity. This class is used
 * in {@link com.everis.salamanca.web.rest.InstalacionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /instalacions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class InstalacionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter descripcion;

    private LongFilter pasoId;

    public InstalacionCriteria(){
    }

    public InstalacionCriteria(InstalacionCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.descripcion = other.descripcion == null ? null : other.descripcion.copy();
        this.pasoId = other.pasoId == null ? null : other.pasoId.copy();
    }

    @Override
    public InstalacionCriteria copy() {
        return new InstalacionCriteria(this);
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

    public StringFilter getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(StringFilter descripcion) {
        this.descripcion = descripcion;
    }

    public LongFilter getPasoId() {
        return pasoId;
    }

    public void setPasoId(LongFilter pasoId) {
        this.pasoId = pasoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final InstalacionCriteria that = (InstalacionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(descripcion, that.descripcion) &&
            Objects.equals(pasoId, that.pasoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        descripcion,
        pasoId
        );
    }

    @Override
    public String toString() {
        return "InstalacionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (descripcion != null ? "descripcion=" + descripcion + ", " : "") +
                (pasoId != null ? "pasoId=" + pasoId + ", " : "") +
            "}";
    }

}
