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

import com.everis.salamanca.domain.Paso;
import com.everis.salamanca.domain.*; // for static metamodels
import com.everis.salamanca.repository.PasoRepository;
import com.everis.salamanca.service.dto.PasoCriteria;

/**
 * Service for executing complex queries for {@link Paso} entities in the database.
 * The main input is a {@link PasoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Paso} or a {@link Page} of {@link Paso} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PasoQueryService extends QueryService<Paso> {

    private final Logger log = LoggerFactory.getLogger(PasoQueryService.class);

    private final PasoRepository pasoRepository;

    public PasoQueryService(PasoRepository pasoRepository) {
        this.pasoRepository = pasoRepository;
    }

    /**
     * Return a {@link List} of {@link Paso} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Paso> findByCriteria(PasoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Paso> specification = createSpecification(criteria);
        return pasoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Paso} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Paso> findByCriteria(PasoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Paso> specification = createSpecification(criteria);
        return pasoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PasoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Paso> specification = createSpecification(criteria);
        return pasoRepository.count(specification);
    }

    /**
     * Function to convert {@link PasoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Paso> createSpecification(PasoCriteria criteria) {
        Specification<Paso> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Paso_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Paso_.name));
            }
            if (criteria.getCommand() != null) {
                specification = specification.and(buildSpecification(criteria.getCommand(), Paso_.command));
            }
            if (criteria.getOrigen() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrigen(), Paso_.origen));
            }
            if (criteria.getDestino() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDestino(), Paso_.destino));
            }
            if (criteria.getParametro() != null) {
                specification = specification.and(buildStringSpecification(criteria.getParametro(), Paso_.parametro));
            }
            if (criteria.getInstalacionId() != null) {
                specification = specification.and(buildSpecification(criteria.getInstalacionId(),
                    root -> root.join(Paso_.instalacion, JoinType.LEFT).get(Instalacion_.id)));
            }
        }
        return specification;
    }
}
