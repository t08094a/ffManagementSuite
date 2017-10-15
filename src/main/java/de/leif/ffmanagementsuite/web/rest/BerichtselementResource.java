package de.leif.ffmanagementsuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.leif.ffmanagementsuite.domain.Berichtselement;

import de.leif.ffmanagementsuite.repository.BerichtselementRepository;
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
 * REST controller for managing Berichtselement.
 */
@RestController
@RequestMapping("/api")
public class BerichtselementResource {

    private final Logger log = LoggerFactory.getLogger(BerichtselementResource.class);

    private static final String ENTITY_NAME = "berichtselement";

    private final BerichtselementRepository berichtselementRepository;

    public BerichtselementResource(BerichtselementRepository berichtselementRepository) {
        this.berichtselementRepository = berichtselementRepository;
    }

    /**
     * POST  /berichtselements : Create a new berichtselement.
     *
     * @param berichtselement the berichtselement to create
     * @return the ResponseEntity with status 201 (Created) and with body the new berichtselement, or with status 400 (Bad Request) if the berichtselement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/berichtselements")
    @Timed
    public ResponseEntity<Berichtselement> createBerichtselement(@Valid @RequestBody Berichtselement berichtselement) throws URISyntaxException {
        log.debug("REST request to save Berichtselement : {}", berichtselement);
        if (berichtselement.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new berichtselement cannot already have an ID")).body(null);
        }
        Berichtselement result = berichtselementRepository.save(berichtselement);
        return ResponseEntity.created(new URI("/api/berichtselements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /berichtselements : Updates an existing berichtselement.
     *
     * @param berichtselement the berichtselement to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated berichtselement,
     * or with status 400 (Bad Request) if the berichtselement is not valid,
     * or with status 500 (Internal Server Error) if the berichtselement couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/berichtselements")
    @Timed
    public ResponseEntity<Berichtselement> updateBerichtselement(@Valid @RequestBody Berichtselement berichtselement) throws URISyntaxException {
        log.debug("REST request to update Berichtselement : {}", berichtselement);
        if (berichtselement.getId() == null) {
            return createBerichtselement(berichtselement);
        }
        Berichtselement result = berichtselementRepository.save(berichtselement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, berichtselement.getId().toString()))
            .body(result);
    }

    /**
     * GET  /berichtselements : get all the berichtselements.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of berichtselements in body
     */
    @GetMapping("/berichtselements")
    @Timed
    public ResponseEntity<List<Berichtselement>> getAllBerichtselements(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Berichtselements");
        Page<Berichtselement> page = berichtselementRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/berichtselements");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /berichtselements/:id : get the "id" berichtselement.
     *
     * @param id the id of the berichtselement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the berichtselement, or with status 404 (Not Found)
     */
    @GetMapping("/berichtselements/{id}")
    @Timed
    public ResponseEntity<Berichtselement> getBerichtselement(@PathVariable Long id) {
        log.debug("REST request to get Berichtselement : {}", id);
        Berichtselement berichtselement = berichtselementRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(berichtselement));
    }

    /**
     * DELETE  /berichtselements/:id : delete the "id" berichtselement.
     *
     * @param id the id of the berichtselement to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/berichtselements/{id}")
    @Timed
    public ResponseEntity<Void> deleteBerichtselement(@PathVariable Long id) {
        log.debug("REST request to delete Berichtselement : {}", id);
        berichtselementRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
