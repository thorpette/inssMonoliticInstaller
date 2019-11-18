package com.everis.salamanca.service.impl;

import com.everis.salamanca.service.MockService;
import com.everis.salamanca.domain.Mock;
import com.everis.salamanca.repository.MockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Mock}.
 */
@Service
@Transactional
public class MockServiceImpl implements MockService {

    private final Logger log = LoggerFactory.getLogger(MockServiceImpl.class);

    private final MockRepository mockRepository;

    public MockServiceImpl(MockRepository mockRepository) {
        this.mockRepository = mockRepository;
    }

    /**
     * Save a mock.
     *
     * @param mock the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Mock save(Mock mock) {
        log.debug("Request to save Mock : {}", mock);
        return mockRepository.save(mock);
    }

    /**
     * Get all the mocks.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Mock> findAll() {
        log.debug("Request to get all Mocks");
        return mockRepository.findAll();
    }


    /**
     * Get one mock by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Mock> findOne(Long id) {
        log.debug("Request to get Mock : {}", id);
        return mockRepository.findById(id);
    }

    /**
     * Delete the mock by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Mock : {}", id);
        mockRepository.deleteById(id);
    }
}
