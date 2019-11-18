package com.everis.salamanca.web.rest;

import com.everis.salamanca.domain.Paso;
import com.everis.salamanca.service.PasoService;
import com.everis.salamanca.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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

    public PasoResource(PasoService pasoService) {
        this.pasoService = pasoService;
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

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pasos in body.
     */
    @GetMapping("/pasos")
    public List<Paso> getAllPasos() {
        log.debug("REST request to get all Pasos");
        return pasoService.findAll();
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
