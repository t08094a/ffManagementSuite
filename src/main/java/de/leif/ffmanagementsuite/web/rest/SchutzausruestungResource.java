package de.leif.ffmanagementsuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.leif.ffmanagementsuite.domain.Schutzausruestung;

import de.leif.ffmanagementsuite.repository.SchutzausruestungRepository;
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
 * REST controller for managing Schutzausruestung.
 */
@RestController
@RequestMapping("/api")
public class SchutzausruestungResource {

    private final Logger log = LoggerFactory.getLogger(SchutzausruestungResource.class);

    private static final String ENTITY_NAME = "schutzausruestung";

    private final SchutzausruestungRepository schutzausruestungRepository;

    public SchutzausruestungResource(SchutzausruestungRepository schutzausruestungRepository) {
        this.schutzausruestungRepository = schutzausruestungRepository;
    }

    /**
     * POST  /schutzausruestungs : Create a new schutzausruestung.
     *
     * @param schutzausruestung the schutzausruestung to create
     * @return the ResponseEntity with status 201 (Created) and with body the new schutzausruestung, or with status 400 (Bad Request) if the schutzausruestung has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/schutzausruestungs")
    @Timed
    public ResponseEntity<Schutzausruestung> createSchutzausruestung(@Valid @RequestBody Schutzausruestung schutzausruestung) throws URISyntaxException {
        log.debug("REST request to save Schutzausruestung : {}", schutzausruestung);
        if (schutzausruestung.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new schutzausruestung cannot already have an ID")).body(null);
        }
        Schutzausruestung result = schutzausruestungRepository.save(schutzausruestung);
        return ResponseEntity.created(new URI("/api/schutzausruestungs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /schutzausruestungs : Updates an existing schutzausruestung.
     *
     * @param schutzausruestung the schutzausruestung to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated schutzausruestung,
     * or with status 400 (Bad Request) if the schutzausruestung is not valid,
     * or with status 500 (Internal Server Error) if the schutzausruestung couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/schutzausruestungs")
    @Timed
    public ResponseEntity<Schutzausruestung> updateSchutzausruestung(@Valid @RequestBody Schutzausruestung schutzausruestung) throws URISyntaxException {
        log.debug("REST request to update Schutzausruestung : {}", schutzausruestung);
        if (schutzausruestung.getId() == null) {
            return createSchutzausruestung(schutzausruestung);
        }
        Schutzausruestung result = schutzausruestungRepository.save(schutzausruestung);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, schutzausruestung.getId().toString()))
            .body(result);
    }

    /**
     * GET  /schutzausruestungs : get all the schutzausruestungs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of schutzausruestungs in body
     */
    @GetMapping("/schutzausruestungs")
    @Timed
    public ResponseEntity<List<Schutzausruestung>> getAllSchutzausruestungs(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Schutzausruestungs");
        Page<Schutzausruestung> page = schutzausruestungRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/schutzausruestungs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /schutzausruestungs/:id : get the "id" schutzausruestung.
     *
     * @param id the id of the schutzausruestung to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the schutzausruestung, or with status 404 (Not Found)
     */
    @GetMapping("/schutzausruestungs/{id}")
    @Timed
    public ResponseEntity<Schutzausruestung> getSchutzausruestung(@PathVariable Long id) {
        log.debug("REST request to get Schutzausruestung : {}", id);
        Schutzausruestung schutzausruestung = schutzausruestungRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(schutzausruestung));
    }

    /**
     * DELETE  /schutzausruestungs/:id : delete the "id" schutzausruestung.
     *
     * @param id the id of the schutzausruestung to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/schutzausruestungs/{id}")
    @Timed
    public ResponseEntity<Void> deleteSchutzausruestung(@PathVariable Long id) {
        log.debug("REST request to delete Schutzausruestung : {}", id);
        schutzausruestungRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
