package de.leif.ffmanagementsuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.leif.ffmanagementsuite.domain.Lehrgang;

import de.leif.ffmanagementsuite.repository.LehrgangRepository;
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
 * REST controller for managing Lehrgang.
 */
@RestController
@RequestMapping("/api")
public class LehrgangResource {

    private final Logger log = LoggerFactory.getLogger(LehrgangResource.class);

    private static final String ENTITY_NAME = "lehrgang";

    private final LehrgangRepository lehrgangRepository;

    public LehrgangResource(LehrgangRepository lehrgangRepository) {
        this.lehrgangRepository = lehrgangRepository;
    }

    /**
     * POST  /lehrgangs : Create a new lehrgang.
     *
     * @param lehrgang the lehrgang to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lehrgang, or with status 400 (Bad Request) if the lehrgang has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lehrgangs")
    @Timed
    public ResponseEntity<Lehrgang> createLehrgang(@Valid @RequestBody Lehrgang lehrgang) throws URISyntaxException {
        log.debug("REST request to save Lehrgang : {}", lehrgang);
        if (lehrgang.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new lehrgang cannot already have an ID")).body(null);
        }
        Lehrgang result = lehrgangRepository.save(lehrgang);
        return ResponseEntity.created(new URI("/api/lehrgangs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lehrgangs : Updates an existing lehrgang.
     *
     * @param lehrgang the lehrgang to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lehrgang,
     * or with status 400 (Bad Request) if the lehrgang is not valid,
     * or with status 500 (Internal Server Error) if the lehrgang couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lehrgangs")
    @Timed
    public ResponseEntity<Lehrgang> updateLehrgang(@Valid @RequestBody Lehrgang lehrgang) throws URISyntaxException {
        log.debug("REST request to update Lehrgang : {}", lehrgang);
        if (lehrgang.getId() == null) {
            return createLehrgang(lehrgang);
        }
        Lehrgang result = lehrgangRepository.save(lehrgang);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, lehrgang.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lehrgangs : get all the lehrgangs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of lehrgangs in body
     */
    @GetMapping("/lehrgangs")
    @Timed
    public ResponseEntity<List<Lehrgang>> getAllLehrgangs(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Lehrgangs");
        Page<Lehrgang> page = lehrgangRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/lehrgangs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /lehrgangs/:id : get the "id" lehrgang.
     *
     * @param id the id of the lehrgang to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lehrgang, or with status 404 (Not Found)
     */
    @GetMapping("/lehrgangs/{id}")
    @Timed
    public ResponseEntity<Lehrgang> getLehrgang(@PathVariable Long id) {
        log.debug("REST request to get Lehrgang : {}", id);
        Lehrgang lehrgang = lehrgangRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(lehrgang));
    }

    /**
     * DELETE  /lehrgangs/:id : delete the "id" lehrgang.
     *
     * @param id the id of the lehrgang to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lehrgangs/{id}")
    @Timed
    public ResponseEntity<Void> deleteLehrgang(@PathVariable Long id) {
        log.debug("REST request to delete Lehrgang : {}", id);
        lehrgangRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
