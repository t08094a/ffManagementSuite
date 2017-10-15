package de.leif.ffmanagementsuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.leif.ffmanagementsuite.domain.Pruefung;

import de.leif.ffmanagementsuite.repository.PruefungRepository;
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
 * REST controller for managing Pruefung.
 */
@RestController
@RequestMapping("/api")
public class PruefungResource {

    private final Logger log = LoggerFactory.getLogger(PruefungResource.class);

    private static final String ENTITY_NAME = "pruefung";

    private final PruefungRepository pruefungRepository;

    public PruefungResource(PruefungRepository pruefungRepository) {
        this.pruefungRepository = pruefungRepository;
    }

    /**
     * POST  /pruefungs : Create a new pruefung.
     *
     * @param pruefung the pruefung to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pruefung, or with status 400 (Bad Request) if the pruefung has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pruefungs")
    @Timed
    public ResponseEntity<Pruefung> createPruefung(@Valid @RequestBody Pruefung pruefung) throws URISyntaxException {
        log.debug("REST request to save Pruefung : {}", pruefung);
        if (pruefung.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new pruefung cannot already have an ID")).body(null);
        }
        Pruefung result = pruefungRepository.save(pruefung);
        return ResponseEntity.created(new URI("/api/pruefungs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pruefungs : Updates an existing pruefung.
     *
     * @param pruefung the pruefung to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pruefung,
     * or with status 400 (Bad Request) if the pruefung is not valid,
     * or with status 500 (Internal Server Error) if the pruefung couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pruefungs")
    @Timed
    public ResponseEntity<Pruefung> updatePruefung(@Valid @RequestBody Pruefung pruefung) throws URISyntaxException {
        log.debug("REST request to update Pruefung : {}", pruefung);
        if (pruefung.getId() == null) {
            return createPruefung(pruefung);
        }
        Pruefung result = pruefungRepository.save(pruefung);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pruefung.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pruefungs : get all the pruefungs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pruefungs in body
     */
    @GetMapping("/pruefungs")
    @Timed
    public ResponseEntity<List<Pruefung>> getAllPruefungs(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Pruefungs");
        Page<Pruefung> page = pruefungRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pruefungs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pruefungs/:id : get the "id" pruefung.
     *
     * @param id the id of the pruefung to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pruefung, or with status 404 (Not Found)
     */
    @GetMapping("/pruefungs/{id}")
    @Timed
    public ResponseEntity<Pruefung> getPruefung(@PathVariable Long id) {
        log.debug("REST request to get Pruefung : {}", id);
        Pruefung pruefung = pruefungRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pruefung));
    }

    /**
     * DELETE  /pruefungs/:id : delete the "id" pruefung.
     *
     * @param id the id of the pruefung to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pruefungs/{id}")
    @Timed
    public ResponseEntity<Void> deletePruefung(@PathVariable Long id) {
        log.debug("REST request to delete Pruefung : {}", id);
        pruefungRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
