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
 * Criteria class for the {@link com.everis.salamanca.domain.Mock} entity. This class is used
 * in {@link com.everis.salamanca.web.rest.MockResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /mocks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MockCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter input;

    private StringFilter output;

    private StringFilter url;

    public MockCriteria(){
    }

    public MockCriteria(MockCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.input = other.input == null ? null : other.input.copy();
        this.output = other.output == null ? null : other.output.copy();
        this.url = other.url == null ? null : other.url.copy();
    }

    @Override
    public MockCriteria copy() {
        return new MockCriteria(this);
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

    public StringFilter getInput() {
        return input;
    }

    public void setInput(StringFilter input) {
        this.input = input;
    }

    public StringFilter getOutput() {
        return output;
    }

    public void setOutput(StringFilter output) {
        this.output = output;
    }

    public StringFilter getUrl() {
        return url;
    }

    public void setUrl(StringFilter url) {
        this.url = url;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MockCriteria that = (MockCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(input, that.input) &&
            Objects.equals(output, that.output) &&
            Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        input,
        output,
        url
        );
    }

    @Override
    public String toString() {
        return "MockCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (input != null ? "input=" + input + ", " : "") +
                (output != null ? "output=" + output + ", " : "") +
                (url != null ? "url=" + url + ", " : "") +
            "}";
    }

}
