package de.leif.ffmanagementsuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.leif.ffmanagementsuite.domain.DurchfuehrungPruefung;

import de.leif.ffmanagementsuite.repository.DurchfuehrungPruefungRepository;
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
 * REST controller for managing DurchfuehrungPruefung.
 */
@RestController
@RequestMapping("/api")
public class DurchfuehrungPruefungResource {

    private final Logger log = LoggerFactory.getLogger(DurchfuehrungPruefungResource.class);

    private static final String ENTITY_NAME = "durchfuehrungPruefung";

    private final DurchfuehrungPruefungRepository durchfuehrungPruefungRepository;

    public DurchfuehrungPruefungResource(DurchfuehrungPruefungRepository durchfuehrungPruefungRepository) {
        this.durchfuehrungPruefungRepository = durchfuehrungPruefungRepository;
    }

    /**
     * POST  /durchfuehrung-pruefungs : Create a new durchfuehrungPruefung.
     *
     * @param durchfuehrungPruefung the durchfuehrungPruefung to create
     * @return the ResponseEntity with status 201 (Created) and with body the new durchfuehrungPruefung, or with status 400 (Bad Request) if the durchfuehrungPruefung has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/durchfuehrung-pruefungs")
    @Timed
    public ResponseEntity<DurchfuehrungPruefung> createDurchfuehrungPruefung(@Valid @RequestBody DurchfuehrungPruefung durchfuehrungPruefung) throws URISyntaxException {
        log.debug("REST request to save DurchfuehrungPruefung : {}", durchfuehrungPruefung);
        if (durchfuehrungPruefung.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new durchfuehrungPruefung cannot already have an ID")).body(null);
        }
        DurchfuehrungPruefung result = durchfuehrungPruefungRepository.save(durchfuehrungPruefung);
        return ResponseEntity.created(new URI("/api/durchfuehrung-pruefungs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /durchfuehrung-pruefungs : Updates an existing durchfuehrungPruefung.
     *
     * @param durchfuehrungPruefung the durchfuehrungPruefung to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated durchfuehrungPruefung,
     * or with status 400 (Bad Request) if the durchfuehrungPruefung is not valid,
     * or with status 500 (Internal Server Error) if the durchfuehrungPruefung couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/durchfuehrung-pruefungs")
    @Timed
    public ResponseEntity<DurchfuehrungPruefung> updateDurchfuehrungPruefung(@Valid @RequestBody DurchfuehrungPruefung durchfuehrungPruefung) throws URISyntaxException {
        log.debug("REST request to update DurchfuehrungPruefung : {}", durchfuehrungPruefung);
        if (durchfuehrungPruefung.getId() == null) {
            return createDurchfuehrungPruefung(durchfuehrungPruefung);
        }
        DurchfuehrungPruefung result = durchfuehrungPruefungRepository.save(durchfuehrungPruefung);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, durchfuehrungPruefung.getId().toString()))
            .body(result);
    }

    /**
     * GET  /durchfuehrung-pruefungs : get all the durchfuehrungPruefungs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of durchfuehrungPruefungs in body
     */
    @GetMapping("/durchfuehrung-pruefungs")
    @Timed
    public ResponseEntity<List<DurchfuehrungPruefung>> getAllDurchfuehrungPruefungs(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of DurchfuehrungPruefungs");
        Page<DurchfuehrungPruefung> page = durchfuehrungPruefungRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/durchfuehrung-pruefungs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /durchfuehrung-pruefungs/:id : get the "id" durchfuehrungPruefung.
     *
     * @param id the id of the durchfuehrungPruefung to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the durchfuehrungPruefung, or with status 404 (Not Found)
     */
    @GetMapping("/durchfuehrung-pruefungs/{id}")
    @Timed
    public ResponseEntity<DurchfuehrungPruefung> getDurchfuehrungPruefung(@PathVariable Long id) {
        log.debug("REST request to get DurchfuehrungPruefung : {}", id);
        DurchfuehrungPruefung durchfuehrungPruefung = durchfuehrungPruefungRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(durchfuehrungPruefung));
    }

    /**
     * DELETE  /durchfuehrung-pruefungs/:id : delete the "id" durchfuehrungPruefung.
     *
     * @param id the id of the durchfuehrungPruefung to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/durchfuehrung-pruefungs/{id}")
    @Timed
    public ResponseEntity<Void> deleteDurchfuehrungPruefung(@PathVariable Long id) {
        log.debug("REST request to delete DurchfuehrungPruefung : {}", id);
        durchfuehrungPruefungRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
