package com.everis.salamanca.web.rest;

import com.everis.salamanca.domain.Paso;
import com.everis.salamanca.service.PasoService;
import com.everis.salamanca.web.rest.errors.BadRequestAlertException;
import com.everis.salamanca.service.dto.PasoCriteria;
import com.everis.salamanca.service.PasoQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.everis.salamanca.domain.Paso}.
 */
@RestController
@RequestMapping("/api")
public class PasoResource {

    private final Logger log = LoggerFactory.getLogger(PasoResource.class);

    private static final String ENTITY_NAME = "paso";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PasoService pasoService;

    private final PasoQueryService pasoQueryService;

    public PasoResource(PasoService pasoService, PasoQueryService pasoQueryService) {
        this.pasoService = pasoService;
        this.pasoQueryService = pasoQueryService;
    }

    /**
     * {@code POST  /pasos} : Create a new paso.
     *
     * @param paso the paso to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paso, or with status {@code 400 (Bad Request)} if the paso has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pasos")
    public ResponseEntity<Paso> createPaso(@Valid @RequestBody Paso paso) throws URISyntaxException {
        log.debug("REST request to save Paso : {}", paso);
        if (paso.getId() != null) {
            throw new BadRequestAlertException("A new paso cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Paso result = pasoService.save(paso);
        return ResponseEntity.created(new URI("/api/pasos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pasos} : Updates an existing paso.
     *
     * @param paso the paso to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paso,
     * or with status {@code 400 (Bad Request)} if the paso is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paso couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pasos")
    public ResponseEntity<Paso> updatePaso(@Valid @RequestBody Paso paso) throws URISyntaxException {
        log.debug("REST request to update Paso : {}", paso);
        if (paso.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Paso result = pasoService.save(paso);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, paso.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pasos} : get all the pasos.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pasos in body.
     */
    @GetMapping("/pasos")
    public ResponseEntity<List<Paso>> getAllPasos(PasoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Pasos by criteria: {}", criteria);
        Page<Paso> page = pasoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /pasos/count} : count all the pasos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/pasos/count")
    public ResponseEntity<Long> countPasos(PasoCriteria criteria) {
        log.debug("REST request to count Pasos by criteria: {}", criteria);
        return ResponseEntity.ok().body(pasoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /pasos/:id} : get the "id" paso.
     *
     * @param id the id of the paso to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paso, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pasos/{id}")
    public ResponseEntity<Paso> getPaso(@PathVariable Long id) {
        log.debug("REST request to get Paso : {}", id);
        Optional<Paso> paso = pasoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paso);
    }

    /**
     * {@code DELETE  /pasos/:id} : delete the "id" paso.
     *
     * @param id the id of the paso to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pasos/{id}")
    public ResponseEntity<Void> deletePaso(@PathVariable Long id) {
        log.debug("REST request to delete Paso : {}", id);
        pasoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
