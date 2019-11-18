package com.everis.salamanca.service.impl;

import com.everis.salamanca.service.PasoService;
import com.everis.salamanca.domain.Paso;
import com.everis.salamanca.repository.PasoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Paso}.
 */
@Service
@Transactional
public class PasoServiceImpl implements PasoService {

    private final Logger log = LoggerFactory.getLogger(PasoServiceImpl.class);

    private final PasoRepository pasoRepository;

    public PasoServiceImpl(PasoRepository pasoRepository) {
        this.pasoRepository = pasoRepository;
    }

    /**
     * Save a paso.
     *
     * @param paso the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Paso save(Paso paso) {
        log.debug("Request to save Paso : {}", paso);
        return pasoRepository.save(paso);
    }

    /**
     * Get all the pasos.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Paso> findAll() {
        log.debug("Request to get all Pasos");
        return pasoRepository.findAll();
    }


    /**
     * Get one paso by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Paso> findOne(Long id) {
        log.debug("Request to get Paso : {}", id);
        return pasoRepository.findById(id);
    }

    /**
     * Delete the paso by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Paso : {}", id);
        pasoRepository.deleteById(id);
    }
}
