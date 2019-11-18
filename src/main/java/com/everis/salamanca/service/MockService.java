package com.everis.salamanca.service;

import com.everis.salamanca.domain.Mock;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Mock}.
 */
public interface MockService {

    /**
     * Save a mock.
     *
     * @param mock the entity to save.
     * @return the persisted entity.
     */
    Mock save(Mock mock);

    /**
     * Get all the mocks.
     *
     * @return the list of entities.
     */
    List<Mock> findAll();


    /**
     * Get the "id" mock.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Mock> findOne(Long id);

    /**
     * Delete the "id" mock.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
