package de.leif.ffmanagementsuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.leif.ffmanagementsuite.domain.Dienststellung;

import de.leif.ffmanagementsuite.repository.DienststellungRepository;
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
 * REST controller for managing Dienststellung.
 */
@RestController
@RequestMapping("/api")
public class DienststellungResource {

    private final Logger log = LoggerFactory.getLogger(DienststellungResource.class);

    private static final String ENTITY_NAME = "dienststellung";

    private final DienststellungRepository dienststellungRepository;

    public DienststellungResource(DienststellungRepository dienststellungRepository) {
        this.dienststellungRepository = dienststellungRepository;
    }

    /**
     * POST  /dienststellungs : Create a new dienststellung.
     *
     * @param dienststellung the dienststellung to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dienststellung, or with status 400 (Bad Request) if the dienststellung has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dienststellungs")
    @Timed
    public ResponseEntity<Dienststellung> createDienststellung(@Valid @RequestBody Dienststellung dienststellung) throws URISyntaxException {
        log.debug("REST request to save Dienststellung : {}", dienststellung);
        if (dienststellung.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new dienststellung cannot already have an ID")).body(null);
        }
        Dienststellung result = dienststellungRepository.save(dienststellung);
        return ResponseEntity.created(new URI("/api/dienststellungs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dienststellungs : Updates an existing dienststellung.
     *
     * @param dienststellung the dienststellung to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dienststellung,
     * or with status 400 (Bad Request) if the dienststellung is not valid,
     * or with status 500 (Internal Server Error) if the dienststellung couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dienststellungs")
    @Timed
    public ResponseEntity<Dienststellung> updateDienststellung(@Valid @RequestBody Dienststellung dienststellung) throws URISyntaxException {
        log.debug("REST request to update Dienststellung : {}", dienststellung);
        if (dienststellung.getId() == null) {
            return createDienststellung(dienststellung);
        }
        Dienststellung result = dienststellungRepository.save(dienststellung);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dienststellung.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dienststellungs : get all the dienststellungs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of dienststellungs in body
     */
    @GetMapping("/dienststellungs")
    @Timed
    public ResponseEntity<List<Dienststellung>> getAllDienststellungs(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Dienststellungs");
        Page<Dienststellung> page = dienststellungRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dienststellungs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /dienststellungs/:id : get the "id" dienststellung.
     *
     * @param id the id of the dienststellung to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dienststellung, or with status 404 (Not Found)
     */
    @GetMapping("/dienststellungs/{id}")
    @Timed
    public ResponseEntity<Dienststellung> getDienststellung(@PathVariable Long id) {
        log.debug("REST request to get Dienststellung : {}", id);
        Dienststellung dienststellung = dienststellungRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dienststellung));
    }

    /**
     * DELETE  /dienststellungs/:id : delete the "id" dienststellung.
     *
     * @param id the id of the dienststellung to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dienststellungs/{id}")
    @Timed
    public ResponseEntity<Void> deleteDienststellung(@PathVariable Long id) {
        log.debug("REST request to delete Dienststellung : {}", id);
        dienststellungRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
