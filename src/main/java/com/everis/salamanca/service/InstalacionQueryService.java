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

import com.everis.salamanca.domain.Instalacion;
import com.everis.salamanca.domain.*; // for static metamodels
import com.everis.salamanca.repository.InstalacionRepository;
import com.everis.salamanca.service.dto.InstalacionCriteria;

/**
 * Service for executing complex queries for {@link Instalacion} entities in the database.
 * The main input is a {@link InstalacionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Instalacion} or a {@link Page} of {@link Instalacion} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InstalacionQueryService extends QueryService<Instalacion> {

    private final Logger log = LoggerFactory.getLogger(InstalacionQueryService.class);

    private final InstalacionRepository instalacionRepository;

    public InstalacionQueryService(InstalacionRepository instalacionRepository) {
        this.instalacionRepository = instalacionRepository;
    }

    /**
     * Return a {@link List} of {@link Instalacion} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Instalacion> findByCriteria(InstalacionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Instalacion> specification = createSpecification(criteria);
        return instalacionRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Instalacion} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Instalacion> findByCriteria(InstalacionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Instalacion> specification = createSpecification(criteria);
        return instalacionRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InstalacionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Instalacion> specification = createSpecification(criteria);
        return instalacionRepository.count(specification);
    }

    /**
     * Function to convert {@link InstalacionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Instalacion> createSpecification(InstalacionCriteria criteria) {
        Specification<Instalacion> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Instalacion_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Instalacion_.name));
            }
            if (criteria.getDescripcion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescripcion(), Instalacion_.descripcion));
            }
            if (criteria.getPasoId() != null) {
                specification = specification.and(buildSpecification(criteria.getPasoId(),
                    root -> root.join(Instalacion_.pasos, JoinType.LEFT).get(Paso_.id)));
            }
        }
        return specification;
    }
}
