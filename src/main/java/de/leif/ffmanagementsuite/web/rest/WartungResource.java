package de.leif.ffmanagementsuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.leif.ffmanagementsuite.domain.Wartung;

import de.leif.ffmanagementsuite.repository.WartungRepository;
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
 * REST controller for managing Wartung.
 */
@RestController
@RequestMapping("/api")
public class WartungResource {

    private final Logger log = LoggerFactory.getLogger(WartungResource.class);

    private static final String ENTITY_NAME = "wartung";

    private final WartungRepository wartungRepository;

    public WartungResource(WartungRepository wartungRepository) {
        this.wartungRepository = wartungRepository;
    }

    /**
     * POST  /wartungs : Create a new wartung.
     *
     * @param wartung the wartung to create
     * @return the ResponseEntity with status 201 (Created) and with body the new wartung, or with status 400 (Bad Request) if the wartung has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/wartungs")
    @Timed
    public ResponseEntity<Wartung> createWartung(@Valid @RequestBody Wartung wartung) throws URISyntaxException {
        log.debug("REST request to save Wartung : {}", wartung);
        if (wartung.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new wartung cannot already have an ID")).body(null);
        }
        Wartung result = wartungRepository.save(wartung);
        return ResponseEntity.created(new URI("/api/wartungs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /wartungs : Updates an existing wartung.
     *
     * @param wartung the wartung to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated wartung,
     * or with status 400 (Bad Request) if the wartung is not valid,
     * or with status 500 (Internal Server Error) if the wartung couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/wartungs")
    @Timed
    public ResponseEntity<Wartung> updateWartung(@Valid @RequestBody Wartung wartung) throws URISyntaxException {
        log.debug("REST request to update Wartung : {}", wartung);
        if (wartung.getId() == null) {
            return createWartung(wartung);
        }
        Wartung result = wartungRepository.save(wartung);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, wartung.getId().toString()))
            .body(result);
    }

    /**
     * GET  /wartungs : get all the wartungs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of wartungs in body
     */
    @GetMapping("/wartungs")
    @Timed
    public ResponseEntity<List<Wartung>> getAllWartungs(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Wartungs");
        Page<Wartung> page = wartungRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/wartungs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /wartungs/:id : get the "id" wartung.
     *
     * @param id the id of the wartung to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the wartung, or with status 404 (Not Found)
     */
    @GetMapping("/wartungs/{id}")
    @Timed
    public ResponseEntity<Wartung> getWartung(@PathVariable Long id) {
        log.debug("REST request to get Wartung : {}", id);
        Wartung wartung = wartungRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(wartung));
    }

    /**
     * DELETE  /wartungs/:id : delete the "id" wartung.
     *
     * @param id the id of the wartung to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/wartungs/{id}")
    @Timed
    public ResponseEntity<Void> deleteWartung(@PathVariable Long id) {
        log.debug("REST request to delete Wartung : {}", id);
        wartungRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
