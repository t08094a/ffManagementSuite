package de.leif.ffmanagementsuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.leif.ffmanagementsuite.domain.Fahrzeug;

import de.leif.ffmanagementsuite.repository.FahrzeugRepository;
import de.leif.ffmanagementsuite.web.rest.util.HeaderUtil;
import de.leif.ffmanagementsuite.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Fahrzeug.
 */
@RestController
@RequestMapping("/api")
public class FahrzeugResource {

    private final Logger log = LoggerFactory.getLogger(FahrzeugResource.class);

    private static final String ENTITY_NAME = "fahrzeug";

    private final FahrzeugRepository fahrzeugRepository;

    public FahrzeugResource(FahrzeugRepository fahrzeugRepository) {
        this.fahrzeugRepository = fahrzeugRepository;
    }

    /**
     * POST  /fahrzeugs : Create a new fahrzeug.
     *
     * @param fahrzeug the fahrzeug to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fahrzeug, or with status 400 (Bad Request) if the fahrzeug has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fahrzeugs")
    @Timed
    public ResponseEntity<Fahrzeug> createFahrzeug(@Valid @RequestBody Fahrzeug fahrzeug) throws URISyntaxException {
        log.debug("REST request to save Fahrzeug : {}", fahrzeug);
        if (fahrzeug.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new fahrzeug cannot already have an ID")).body(null);
        }
        Fahrzeug result = fahrzeugRepository.save(fahrzeug);
        return ResponseEntity.created(new URI("/api/fahrzeugs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fahrzeugs : Updates an existing fahrzeug.
     *
     * @param fahrzeug the fahrzeug to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fahrzeug,
     * or with status 400 (Bad Request) if the fahrzeug is not valid,
     * or with status 500 (Internal Server Error) if the fahrzeug couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fahrzeugs")
    @Timed
    public ResponseEntity<Fahrzeug> updateFahrzeug(@Valid @RequestBody Fahrzeug fahrzeug) throws URISyntaxException {
        log.debug("REST request to update Fahrzeug : {}", fahrzeug);
        if (fahrzeug.getId() == null) {
            return createFahrzeug(fahrzeug);
        }
        Fahrzeug result = fahrzeugRepository.save(fahrzeug);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fahrzeug.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fahrzeugs : get all the fahrzeugs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of fahrzeugs in body
     */
    @GetMapping("/fahrzeugs")
    @Timed
    public ResponseEntity<List<Fahrzeug>> getAllFahrzeugs(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Fahrzeugs");
        Page<Fahrzeug> page = fahrzeugRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/fahrzeugs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /fahrzeugs/:id : get the "id" fahrzeug.
     *
     * @param id the id of the fahrzeug to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fahrzeug, or with status 404 (Not Found)
     */
    @GetMapping("/fahrzeugs/{id}")
    @Timed
    public ResponseEntity<Fahrzeug> getFahrzeug(@PathVariable Long id) {
        log.debug("REST request to get Fahrzeug : {}", id);
        Fahrzeug fahrzeug = fahrzeugRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(fahrzeug));
    }

    /**
     * DELETE  /fahrzeugs/:id : delete the "id" fahrzeug.
     *
     * @param id the id of the fahrzeug to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fahrzeugs/{id}")
    @Timed
    public ResponseEntity<Void> deleteFahrzeug(@PathVariable Long id) {
        log.debug("REST request to delete Fahrzeug : {}", id);
        fahrzeugRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
