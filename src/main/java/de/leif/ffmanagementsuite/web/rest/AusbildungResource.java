package de.leif.ffmanagementsuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.leif.ffmanagementsuite.domain.Ausbildung;

import de.leif.ffmanagementsuite.repository.AusbildungRepository;
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
 * REST controller for managing Ausbildung.
 */
@RestController
@RequestMapping("/api")
public class AusbildungResource {

    private final Logger log = LoggerFactory.getLogger(AusbildungResource.class);

    private static final String ENTITY_NAME = "ausbildung";

    private final AusbildungRepository ausbildungRepository;

    public AusbildungResource(AusbildungRepository ausbildungRepository) {
        this.ausbildungRepository = ausbildungRepository;
    }

    /**
     * POST  /ausbildungs : Create a new ausbildung.
     *
     * @param ausbildung the ausbildung to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ausbildung, or with status 400 (Bad Request) if the ausbildung has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ausbildungs")
    @Timed
    public ResponseEntity<Ausbildung> createAusbildung(@Valid @RequestBody Ausbildung ausbildung) throws URISyntaxException {
        log.debug("REST request to save Ausbildung : {}", ausbildung);
        if (ausbildung.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new ausbildung cannot already have an ID")).body(null);
        }
        Ausbildung result = ausbildungRepository.save(ausbildung);
        return ResponseEntity.created(new URI("/api/ausbildungs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ausbildungs : Updates an existing ausbildung.
     *
     * @param ausbildung the ausbildung to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ausbildung,
     * or with status 400 (Bad Request) if the ausbildung is not valid,
     * or with status 500 (Internal Server Error) if the ausbildung couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ausbildungs")
    @Timed
    public ResponseEntity<Ausbildung> updateAusbildung(@Valid @RequestBody Ausbildung ausbildung) throws URISyntaxException {
        log.debug("REST request to update Ausbildung : {}", ausbildung);
        if (ausbildung.getId() == null) {
            return createAusbildung(ausbildung);
        }
        Ausbildung result = ausbildungRepository.save(ausbildung);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ausbildung.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ausbildungs : get all the ausbildungs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ausbildungs in body
     */
    @GetMapping("/ausbildungs")
    @Timed
    public ResponseEntity<List<Ausbildung>> getAllAusbildungs(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Ausbildungs");
        Page<Ausbildung> page = ausbildungRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ausbildungs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ausbildungs/:id : get the "id" ausbildung.
     *
     * @param id the id of the ausbildung to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ausbildung, or with status 404 (Not Found)
     */
    @GetMapping("/ausbildungs/{id}")
    @Timed
    public ResponseEntity<Ausbildung> getAusbildung(@PathVariable Long id) {
        log.debug("REST request to get Ausbildung : {}", id);
        Ausbildung ausbildung = ausbildungRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ausbildung));
    }

    /**
     * DELETE  /ausbildungs/:id : delete the "id" ausbildung.
     *
     * @param id the id of the ausbildung to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ausbildungs/{id}")
    @Timed
    public ResponseEntity<Void> deleteAusbildung(@PathVariable Long id) {
        log.debug("REST request to delete Ausbildung : {}", id);
        ausbildungRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
