package de.leif.ffmanagementsuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.leif.ffmanagementsuite.domain.Reinigung;

import de.leif.ffmanagementsuite.repository.ReinigungRepository;
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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Reinigung.
 */
@RestController
@RequestMapping("/api")
public class ReinigungResource {

    private final Logger log = LoggerFactory.getLogger(ReinigungResource.class);

    private static final String ENTITY_NAME = "reinigung";

    private final ReinigungRepository reinigungRepository;

    public ReinigungResource(ReinigungRepository reinigungRepository) {
        this.reinigungRepository = reinigungRepository;
    }

    /**
     * POST  /reinigungs : Create a new reinigung.
     *
     * @param reinigung the reinigung to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reinigung, or with status 400 (Bad Request) if the reinigung has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/reinigungs")
    @Timed
    public ResponseEntity<Reinigung> createReinigung(@RequestBody Reinigung reinigung) throws URISyntaxException {
        log.debug("REST request to save Reinigung : {}", reinigung);
        if (reinigung.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new reinigung cannot already have an ID")).body(null);
        }
        Reinigung result = reinigungRepository.save(reinigung);
        return ResponseEntity.created(new URI("/api/reinigungs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /reinigungs : Updates an existing reinigung.
     *
     * @param reinigung the reinigung to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reinigung,
     * or with status 400 (Bad Request) if the reinigung is not valid,
     * or with status 500 (Internal Server Error) if the reinigung couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/reinigungs")
    @Timed
    public ResponseEntity<Reinigung> updateReinigung(@RequestBody Reinigung reinigung) throws URISyntaxException {
        log.debug("REST request to update Reinigung : {}", reinigung);
        if (reinigung.getId() == null) {
            return createReinigung(reinigung);
        }
        Reinigung result = reinigungRepository.save(reinigung);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, reinigung.getId().toString()))
            .body(result);
    }

    /**
     * GET  /reinigungs : get all the reinigungs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of reinigungs in body
     */
    @GetMapping("/reinigungs")
    @Timed
    public ResponseEntity<List<Reinigung>> getAllReinigungs(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Reinigungs");
        Page<Reinigung> page = reinigungRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/reinigungs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /reinigungs/:id : get the "id" reinigung.
     *
     * @param id the id of the reinigung to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reinigung, or with status 404 (Not Found)
     */
    @GetMapping("/reinigungs/{id}")
    @Timed
    public ResponseEntity<Reinigung> getReinigung(@PathVariable Long id) {
        log.debug("REST request to get Reinigung : {}", id);
        Reinigung reinigung = reinigungRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(reinigung));
    }

    /**
     * DELETE  /reinigungs/:id : delete the "id" reinigung.
     *
     * @param id the id of the reinigung to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/reinigungs/{id}")
    @Timed
    public ResponseEntity<Void> deleteReinigung(@PathVariable Long id) {
        log.debug("REST request to delete Reinigung : {}", id);
        reinigungRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
