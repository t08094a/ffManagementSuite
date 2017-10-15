package de.leif.ffmanagementsuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.leif.ffmanagementsuite.domain.Auspraegung;

import de.leif.ffmanagementsuite.repository.AuspraegungRepository;
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
 * REST controller for managing Auspraegung.
 */
@RestController
@RequestMapping("/api")
public class AuspraegungResource {

    private final Logger log = LoggerFactory.getLogger(AuspraegungResource.class);

    private static final String ENTITY_NAME = "auspraegung";

    private final AuspraegungRepository auspraegungRepository;

    public AuspraegungResource(AuspraegungRepository auspraegungRepository) {
        this.auspraegungRepository = auspraegungRepository;
    }

    /**
     * POST  /auspraegungs : Create a new auspraegung.
     *
     * @param auspraegung the auspraegung to create
     * @return the ResponseEntity with status 201 (Created) and with body the new auspraegung, or with status 400 (Bad Request) if the auspraegung has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/auspraegungs")
    @Timed
    public ResponseEntity<Auspraegung> createAuspraegung(@Valid @RequestBody Auspraegung auspraegung) throws URISyntaxException {
        log.debug("REST request to save Auspraegung : {}", auspraegung);
        if (auspraegung.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new auspraegung cannot already have an ID")).body(null);
        }
        Auspraegung result = auspraegungRepository.save(auspraegung);
        return ResponseEntity.created(new URI("/api/auspraegungs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /auspraegungs : Updates an existing auspraegung.
     *
     * @param auspraegung the auspraegung to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated auspraegung,
     * or with status 400 (Bad Request) if the auspraegung is not valid,
     * or with status 500 (Internal Server Error) if the auspraegung couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/auspraegungs")
    @Timed
    public ResponseEntity<Auspraegung> updateAuspraegung(@Valid @RequestBody Auspraegung auspraegung) throws URISyntaxException {
        log.debug("REST request to update Auspraegung : {}", auspraegung);
        if (auspraegung.getId() == null) {
            return createAuspraegung(auspraegung);
        }
        Auspraegung result = auspraegungRepository.save(auspraegung);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, auspraegung.getId().toString()))
            .body(result);
    }

    /**
     * GET  /auspraegungs : get all the auspraegungs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of auspraegungs in body
     */
    @GetMapping("/auspraegungs")
    @Timed
    public ResponseEntity<List<Auspraegung>> getAllAuspraegungs(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Auspraegungs");
        Page<Auspraegung> page = auspraegungRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/auspraegungs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /auspraegungs/:id : get the "id" auspraegung.
     *
     * @param id the id of the auspraegung to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the auspraegung, or with status 404 (Not Found)
     */
    @GetMapping("/auspraegungs/{id}")
    @Timed
    public ResponseEntity<Auspraegung> getAuspraegung(@PathVariable Long id) {
        log.debug("REST request to get Auspraegung : {}", id);
        Auspraegung auspraegung = auspraegungRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(auspraegung));
    }

    /**
     * DELETE  /auspraegungs/:id : delete the "id" auspraegung.
     *
     * @param id the id of the auspraegung to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/auspraegungs/{id}")
    @Timed
    public ResponseEntity<Void> deleteAuspraegung(@PathVariable Long id) {
        log.debug("REST request to delete Auspraegung : {}", id);
        auspraegungRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
