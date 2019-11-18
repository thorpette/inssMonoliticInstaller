package com.everis.salamanca.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.everis.salamanca.domain.Mock;
import com.everis.salamanca.domain.*; // for static metamodels
import com.everis.salamanca.repository.MockRepository;
import com.everis.salamanca.service.dto.MockCriteria;

/**
 * Service for executing complex queries for {@link Mock} entities in the database.
 * The main input is a {@link MockCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Mock} or a {@link Page} of {@link Mock} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MockQueryService extends QueryService<Mock> {

    private final Logger log = LoggerFactory.getLogger(MockQueryService.class);

    private final MockRepository mockRepository;

    public MockQueryService(MockRepository mockRepository) {
        this.mockRepository = mockRepository;
    }

    /**
     * Return a {@link List} of {@link Mock} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Mock> findByCriteria(MockCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Mock> specification = createSpecification(criteria);
        return mockRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Mock} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Mock> findByCriteria(MockCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Mock> specification = createSpecification(criteria);
        return mockRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MockCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Mock> specification = createSpecification(criteria);
        return mockRepository.count(specification);
    }

    /**
     * Function to convert {@link MockCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Mock> createSpecification(MockCriteria criteria) {
        Specification<Mock> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Mock_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Mock_.name));
            }
            if (criteria.getInput() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInput(), Mock_.input));
            }
            if (criteria.getOutput() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOutput(), Mock_.output));
            }
            if (criteria.getUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUrl(), Mock_.url));
            }
        }
        return specification;
    }
}
