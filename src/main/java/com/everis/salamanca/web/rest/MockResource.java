package com.everis.salamanca.web.rest;

import com.everis.salamanca.domain.Mock;
import com.everis.salamanca.service.MockService;
import com.everis.salamanca.web.rest.errors.BadRequestAlertException;
import com.everis.salamanca.service.dto.MockCriteria;
import com.everis.salamanca.service.MockQueryService;

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
 * REST controller for managing {@link com.everis.salamanca.domain.Mock}.
 */
@RestController
@RequestMapping("/api")
public class MockResource {

    private final Logger log = LoggerFactory.getLogger(MockResource.class);

    private static final String ENTITY_NAME = "mock";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MockService mockService;

    private final MockQueryService mockQueryService;

    public MockResource(MockService mockService, MockQueryService mockQueryService) {
        this.mockService = mockService;
        this.mockQueryService = mockQueryService;
    }

    /**
     * {@code POST  /mocks} : Create a new mock.
     *
     * @param mock the mock to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mock, or with status {@code 400 (Bad Request)} if the mock has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mocks")
    public ResponseEntity<Mock> createMock(@Valid @RequestBody Mock mock) throws URISyntaxException {
        log.debug("REST request to save Mock : {}", mock);
        if (mock.getId() != null) {
            throw new BadRequestAlertException("A new mock cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Mock result = mockService.save(mock);
        return ResponseEntity.created(new URI("/api/mocks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mocks} : Updates an existing mock.
     *
     * @param mock the mock to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mock,
     * or with status {@code 400 (Bad Request)} if the mock is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mock couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mocks")
    public ResponseEntity<Mock> updateMock(@Valid @RequestBody Mock mock) throws URISyntaxException {
        log.debug("REST request to update Mock : {}", mock);
        if (mock.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Mock result = mockService.save(mock);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mock.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /mocks} : get all the mocks.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mocks in body.
     */
    @GetMapping("/mocks")
    public ResponseEntity<List<Mock>> getAllMocks(MockCriteria criteria) {
        log.debug("REST request to get Mocks by criteria: {}", criteria);
        List<Mock> entityList = mockQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /mocks/count} : count all the mocks.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/mocks/count")
    public ResponseEntity<Long> countMocks(MockCriteria criteria) {
        log.debug("REST request to count Mocks by criteria: {}", criteria);
        return ResponseEntity.ok().body(mockQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /mocks/:id} : get the "id" mock.
     *
     * @param id the id of the mock to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mock, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mocks/{id}")
    public ResponseEntity<Mock> getMock(@PathVariable Long id) {
        log.debug("REST request to get Mock : {}", id);
        Optional<Mock> mock = mockService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mock);
    }

    /**
     * {@code DELETE  /mocks/:id} : delete the "id" mock.
     *
     * @param id the id of the mock to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mocks/{id}")
    public ResponseEntity<Void> deleteMock(@PathVariable Long id) {
        log.debug("REST request to delete Mock : {}", id);
        mockService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
