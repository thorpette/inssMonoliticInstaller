package com.everis.salamanca.service;

import com.everis.salamanca.domain.Instalacion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Instalacion}.
 */
public interface InstalacionService {

    /**
     * Save a instalacion.
     *
     * @param instalacion the entity to save.
     * @return the persisted entity.
     */
    Instalacion save(Instalacion instalacion);

    /**
     * Get all the instalacions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Instalacion> findAll(Pageable pageable);


    /**
     * Get the "id" instalacion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Instalacion> findOne(Long id);

    /**
     * Delete the "id" instalacion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
