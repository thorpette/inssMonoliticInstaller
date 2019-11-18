package com.everis.salamanca.service;

import com.everis.salamanca.domain.Paso;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Paso> findAll(Pageable pageable);


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
