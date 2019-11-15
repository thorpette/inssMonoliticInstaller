package com.everis.salamanca.service;

import com.everis.salamanca.domain.Paso;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Paso}.
 */
public interface PasoService {

    /**
     * Save a paso.
     *
     * @param paso the entity to save.
     * @return the persisted entity.
     */
    Paso save(Paso paso);

    /**
     * Get all the pasos.
     *
     * @return the list of entities.
     */
    List<Paso> findAll();


    /**
     * Get the "id" paso.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Paso> findOne(Long id);

    /**
     * Delete the "id" paso.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
