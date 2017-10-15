package de.leif.ffmanagementsuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.leif.ffmanagementsuite.domain.Leistungspruefung;

import de.leif.ffmanagementsuite.repository.LeistungspruefungRepository;
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
 * REST controller for managing Leistungspruefung.
 */
@RestController
@RequestMapping("/api")
public class LeistungspruefungResource {

    private final Logger log = LoggerFactory.getLogger(LeistungspruefungResource.class);

    private static final String ENTITY_NAME = "leistungspruefung";

    private final LeistungspruefungRepository leistungspruefungRepository;

    public LeistungspruefungResource(LeistungspruefungRepository leistungspruefungRepository) {
        this.leistungspruefungRepository = leistungspruefungRepository;
    }

    /**
     * POST  /leistungspruefungs : Create a new leistungspruefung.
     *
     * @param leistungspruefung the leistungspruefung to create
     * @return the ResponseEntity with status 201 (Created) and with body the new leistungspruefung, or with status 400 (Bad Request) if the leistungspruefung has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/leistungspruefungs")
    @Timed
    public ResponseEntity<Leistungspruefung> createLeistungspruefung(@Valid @RequestBody Leistungspruefung leistungspruefung) throws URISyntaxException {
        log.debug("REST request to save Leistungspruefung : {}", leistungspruefung);
        if (leistungspruefung.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new leistungspruefung cannot already have an ID")).body(null);
        }
        Leistungspruefung result = leistungspruefungRepository.save(leistungspruefung);
        return ResponseEntity.created(new URI("/api/leistungspruefungs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /leistungspruefungs : Updates an existing leistungspruefung.
     *
     * @param leistungspruefung the leistungspruefung to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated leistungspruefung,
     * or with status 400 (Bad Request) if the leistungspruefung is not valid,
     * or with status 500 (Internal Server Error) if the leistungspruefung couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/leistungspruefungs")
    @Timed
    public ResponseEntity<Leistungspruefung> updateLeistungspruefung(@Valid @RequestBody Leistungspruefung leistungspruefung) throws URISyntaxException {
        log.debug("REST request to update Leistungspruefung : {}", leistungspruefung);
        if (leistungspruefung.getId() == null) {
            return createLeistungspruefung(leistungspruefung);
        }
        Leistungspruefung result = leistungspruefungRepository.save(leistungspruefung);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, leistungspruefung.getId().toString()))
            .body(result);
    }

    /**
     * GET  /leistungspruefungs : get all the leistungspruefungs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of leistungspruefungs in body
     */
    @GetMapping("/leistungspruefungs")
    @Timed
    public ResponseEntity<List<Leistungspruefung>> getAllLeistungspruefungs(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Leistungspruefungs");
        Page<Leistungspruefung> page = leistungspruefungRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/leistungspruefungs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /leistungspruefungs/:id : get the "id" leistungspruefung.
     *
     * @param id the id of the leistungspruefung to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the leistungspruefung, or with status 404 (Not Found)
     */
    @GetMapping("/leistungspruefungs/{id}")
    @Timed
    public ResponseEntity<Leistungspruefung> getLeistungspruefung(@PathVariable Long id) {
        log.debug("REST request to get Leistungspruefung : {}", id);
        Leistungspruefung leistungspruefung = leistungspruefungRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(leistungspruefung));
    }

    /**
     * DELETE  /leistungspruefungs/:id : delete the "id" leistungspruefung.
     *
     * @param id the id of the leistungspruefung to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/leistungspruefungs/{id}")
    @Timed
    public ResponseEntity<Void> deleteLeistungspruefung(@PathVariable Long id) {
        log.debug("REST request to delete Leistungspruefung : {}", id);
        leistungspruefungRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
