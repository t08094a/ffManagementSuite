package de.leif.ffmanagementsuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.leif.ffmanagementsuite.domain.DurchfuehrungWartung;

import de.leif.ffmanagementsuite.repository.DurchfuehrungWartungRepository;
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
 * REST controller for managing DurchfuehrungWartung.
 */
@RestController
@RequestMapping("/api")
public class DurchfuehrungWartungResource {

    private final Logger log = LoggerFactory.getLogger(DurchfuehrungWartungResource.class);

    private static final String ENTITY_NAME = "durchfuehrungWartung";

    private final DurchfuehrungWartungRepository durchfuehrungWartungRepository;

    public DurchfuehrungWartungResource(DurchfuehrungWartungRepository durchfuehrungWartungRepository) {
        this.durchfuehrungWartungRepository = durchfuehrungWartungRepository;
    }

    /**
     * POST  /durchfuehrung-wartungs : Create a new durchfuehrungWartung.
     *
     * @param durchfuehrungWartung the durchfuehrungWartung to create
     * @return the ResponseEntity with status 201 (Created) and with body the new durchfuehrungWartung, or with status 400 (Bad Request) if the durchfuehrungWartung has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/durchfuehrung-wartungs")
    @Timed
    public ResponseEntity<DurchfuehrungWartung> createDurchfuehrungWartung(@Valid @RequestBody DurchfuehrungWartung durchfuehrungWartung) throws URISyntaxException {
        log.debug("REST request to save DurchfuehrungWartung : {}", durchfuehrungWartung);
        if (durchfuehrungWartung.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new durchfuehrungWartung cannot already have an ID")).body(null);
        }
        DurchfuehrungWartung result = durchfuehrungWartungRepository.save(durchfuehrungWartung);
        return ResponseEntity.created(new URI("/api/durchfuehrung-wartungs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /durchfuehrung-wartungs : Updates an existing durchfuehrungWartung.
     *
     * @param durchfuehrungWartung the durchfuehrungWartung to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated durchfuehrungWartung,
     * or with status 400 (Bad Request) if the durchfuehrungWartung is not valid,
     * or with status 500 (Internal Server Error) if the durchfuehrungWartung couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/durchfuehrung-wartungs")
    @Timed
    public ResponseEntity<DurchfuehrungWartung> updateDurchfuehrungWartung(@Valid @RequestBody DurchfuehrungWartung durchfuehrungWartung) throws URISyntaxException {
        log.debug("REST request to update DurchfuehrungWartung : {}", durchfuehrungWartung);
        if (durchfuehrungWartung.getId() == null) {
            return createDurchfuehrungWartung(durchfuehrungWartung);
        }
        DurchfuehrungWartung result = durchfuehrungWartungRepository.save(durchfuehrungWartung);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, durchfuehrungWartung.getId().toString()))
            .body(result);
    }

    /**
     * GET  /durchfuehrung-wartungs : get all the durchfuehrungWartungs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of durchfuehrungWartungs in body
     */
    @GetMapping("/durchfuehrung-wartungs")
    @Timed
    public ResponseEntity<List<DurchfuehrungWartung>> getAllDurchfuehrungWartungs(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of DurchfuehrungWartungs");
        Page<DurchfuehrungWartung> page = durchfuehrungWartungRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/durchfuehrung-wartungs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /durchfuehrung-wartungs/:id : get the "id" durchfuehrungWartung.
     *
     * @param id the id of the durchfuehrungWartung to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the durchfuehrungWartung, or with status 404 (Not Found)
     */
    @GetMapping("/durchfuehrung-wartungs/{id}")
    @Timed
    public ResponseEntity<DurchfuehrungWartung> getDurchfuehrungWartung(@PathVariable Long id) {
        log.debug("REST request to get DurchfuehrungWartung : {}", id);
        DurchfuehrungWartung durchfuehrungWartung = durchfuehrungWartungRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(durchfuehrungWartung));
    }

    /**
     * DELETE  /durchfuehrung-wartungs/:id : delete the "id" durchfuehrungWartung.
     *
     * @param id the id of the durchfuehrungWartung to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/durchfuehrung-wartungs/{id}")
    @Timed
    public ResponseEntity<Void> deleteDurchfuehrungWartung(@PathVariable Long id) {
        log.debug("REST request to delete DurchfuehrungWartung : {}", id);
        durchfuehrungWartungRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
