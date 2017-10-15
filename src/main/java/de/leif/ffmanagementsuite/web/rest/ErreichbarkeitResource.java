package de.leif.ffmanagementsuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.leif.ffmanagementsuite.domain.Erreichbarkeit;

import de.leif.ffmanagementsuite.repository.ErreichbarkeitRepository;
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
 * REST controller for managing Erreichbarkeit.
 */
@RestController
@RequestMapping("/api")
public class ErreichbarkeitResource {

    private final Logger log = LoggerFactory.getLogger(ErreichbarkeitResource.class);

    private static final String ENTITY_NAME = "erreichbarkeit";

    private final ErreichbarkeitRepository erreichbarkeitRepository;

    public ErreichbarkeitResource(ErreichbarkeitRepository erreichbarkeitRepository) {
        this.erreichbarkeitRepository = erreichbarkeitRepository;
    }

    /**
     * POST  /erreichbarkeits : Create a new erreichbarkeit.
     *
     * @param erreichbarkeit the erreichbarkeit to create
     * @return the ResponseEntity with status 201 (Created) and with body the new erreichbarkeit, or with status 400 (Bad Request) if the erreichbarkeit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/erreichbarkeits")
    @Timed
    public ResponseEntity<Erreichbarkeit> createErreichbarkeit(@Valid @RequestBody Erreichbarkeit erreichbarkeit) throws URISyntaxException {
        log.debug("REST request to save Erreichbarkeit : {}", erreichbarkeit);
        if (erreichbarkeit.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new erreichbarkeit cannot already have an ID")).body(null);
        }
        Erreichbarkeit result = erreichbarkeitRepository.save(erreichbarkeit);
        return ResponseEntity.created(new URI("/api/erreichbarkeits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /erreichbarkeits : Updates an existing erreichbarkeit.
     *
     * @param erreichbarkeit the erreichbarkeit to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated erreichbarkeit,
     * or with status 400 (Bad Request) if the erreichbarkeit is not valid,
     * or with status 500 (Internal Server Error) if the erreichbarkeit couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/erreichbarkeits")
    @Timed
    public ResponseEntity<Erreichbarkeit> updateErreichbarkeit(@Valid @RequestBody Erreichbarkeit erreichbarkeit) throws URISyntaxException {
        log.debug("REST request to update Erreichbarkeit : {}", erreichbarkeit);
        if (erreichbarkeit.getId() == null) {
            return createErreichbarkeit(erreichbarkeit);
        }
        Erreichbarkeit result = erreichbarkeitRepository.save(erreichbarkeit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, erreichbarkeit.getId().toString()))
            .body(result);
    }

    /**
     * GET  /erreichbarkeits : get all the erreichbarkeits.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of erreichbarkeits in body
     */
    @GetMapping("/erreichbarkeits")
    @Timed
    public ResponseEntity<List<Erreichbarkeit>> getAllErreichbarkeits(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Erreichbarkeits");
        Page<Erreichbarkeit> page = erreichbarkeitRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/erreichbarkeits");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /erreichbarkeits/:id : get the "id" erreichbarkeit.
     *
     * @param id the id of the erreichbarkeit to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the erreichbarkeit, or with status 404 (Not Found)
     */
    @GetMapping("/erreichbarkeits/{id}")
    @Timed
    public ResponseEntity<Erreichbarkeit> getErreichbarkeit(@PathVariable Long id) {
        log.debug("REST request to get Erreichbarkeit : {}", id);
        Erreichbarkeit erreichbarkeit = erreichbarkeitRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(erreichbarkeit));
    }

    /**
     * DELETE  /erreichbarkeits/:id : delete the "id" erreichbarkeit.
     *
     * @param id the id of the erreichbarkeit to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/erreichbarkeits/{id}")
    @Timed
    public ResponseEntity<Void> deleteErreichbarkeit(@PathVariable Long id) {
        log.debug("REST request to delete Erreichbarkeit : {}", id);
        erreichbarkeitRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
