package com.everis.salamanca.web.rest;

import com.everis.salamanca.domain.Instalacion;
import com.everis.salamanca.service.InstalacionService;
import com.everis.salamanca.web.rest.errors.BadRequestAlertException;

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
 * REST controller for managing {@link com.everis.salamanca.domain.Instalacion}.
 */
@RestController
@RequestMapping("/api")
public class InstalacionResource {

    private final Logger log = LoggerFactory.getLogger(InstalacionResource.class);

    private static final String ENTITY_NAME = "instalacion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InstalacionService instalacionService;

    public InstalacionResource(InstalacionService instalacionService) {
        this.instalacionService = instalacionService;
    }

    /**
     * {@code POST  /instalacions} : Create a new instalacion.
     *
     * @param instalacion the instalacion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new instalacion, or with status {@code 400 (Bad Request)} if the instalacion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/instalacions")
    public ResponseEntity<Instalacion> createInstalacion(@Valid @RequestBody Instalacion instalacion) throws URISyntaxException {
        log.debug("REST request to save Instalacion : {}", instalacion);
        if (instalacion.getId() != null) {
            throw new BadRequestAlertException("A new instalacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Instalacion result = instalacionService.save(instalacion);
        return ResponseEntity.created(new URI("/api/instalacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /instalacions} : Updates an existing instalacion.
     *
     * @param instalacion the instalacion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated instalacion,
     * or with status {@code 400 (Bad Request)} if the instalacion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the instalacion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/instalacions")
    public ResponseEntity<Instalacion> updateInstalacion(@Valid @RequestBody Instalacion instalacion) throws URISyntaxException {
        log.debug("REST request to update Instalacion : {}", instalacion);
        if (instalacion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Instalacion result = instalacionService.save(instalacion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, instalacion.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /instalacions} : get all the instalacions.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of instalacions in body.
     */
    @GetMapping("/instalacions")
    public ResponseEntity<List<Instalacion>> getAllInstalacions(Pageable pageable) {
        log.debug("REST request to get a page of Instalacions");
        Page<Instalacion> page = instalacionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /instalacions/:id} : get the "id" instalacion.
     *
     * @param id the id of the instalacion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the instalacion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/instalacions/{id}")
    public ResponseEntity<Instalacion> getInstalacion(@PathVariable Long id) {
        log.debug("REST request to get Instalacion : {}", id);
        Optional<Instalacion> instalacion = instalacionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(instalacion);
    }

    /**
     * {@code DELETE  /instalacions/:id} : delete the "id" instalacion.
     *
     * @param id the id of the instalacion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/instalacions/{id}")
    public ResponseEntity<Void> deleteInstalacion(@PathVariable Long id) {
        log.debug("REST request to delete Instalacion : {}", id);
        instalacionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
