package com.everis.salamanca.service.impl;

import com.everis.salamanca.service.InstalacionService;
import com.everis.salamanca.domain.Instalacion;
import com.everis.salamanca.repository.InstalacionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Instalacion}.
 */
@Service
@Transactional
public class InstalacionServiceImpl implements InstalacionService {

    private final Logger log = LoggerFactory.getLogger(InstalacionServiceImpl.class);

    private final InstalacionRepository instalacionRepository;

    public InstalacionServiceImpl(InstalacionRepository instalacionRepository) {
        this.instalacionRepository = instalacionRepository;
    }

    /**
     * Save a instalacion.
     *
     * @param instalacion the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Instalacion save(Instalacion instalacion) {
        log.debug("Request to save Instalacion : {}", instalacion);
        return instalacionRepository.save(instalacion);
    }

    /**
     * Get all the instalacions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Instalacion> findAll(Pageable pageable) {
        log.debug("Request to get all Instalacions");
        return instalacionRepository.findAll(pageable);
    }


    /**
     * Get one instalacion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Instalacion> findOne(Long id) {
        log.debug("Request to get Instalacion : {}", id);
        return instalacionRepository.findById(id);
    }

    /**
     * Delete the instalacion by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Instalacion : {}", id);
        instalacionRepository.deleteById(id);
    }
}
