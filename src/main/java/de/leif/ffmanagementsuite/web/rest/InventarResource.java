package de.leif.ffmanagementsuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.leif.ffmanagementsuite.domain.Inventar;

import de.leif.ffmanagementsuite.repository.InventarRepository;
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
 * REST controller for managing Inventar.
 */
@RestController
@RequestMapping("/api")
public class InventarResource {

    private final Logger log = LoggerFactory.getLogger(InventarResource.class);

    private static final String ENTITY_NAME = "inventar";

    private final InventarRepository inventarRepository;

    public InventarResource(InventarRepository inventarRepository) {
        this.inventarRepository = inventarRepository;
    }

    /**
     * POST  /inventars : Create a new inventar.
     *
     * @param inventar the inventar to create
     * @return the ResponseEntity with status 201 (Created) and with body the new inventar, or with status 400 (Bad Request) if the inventar has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/inventars")
    @Timed
    public ResponseEntity<Inventar> createInventar(@Valid @RequestBody Inventar inventar) throws URISyntaxException {
        log.debug("REST request to save Inventar : {}", inventar);
        if (inventar.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new inventar cannot already have an ID")).body(null);
        }
        Inventar result = inventarRepository.save(inventar);
        return ResponseEntity.created(new URI("/api/inventars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /inventars : Updates an existing inventar.
     *
     * @param inventar the inventar to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated inventar,
     * or with status 400 (Bad Request) if the inventar is not valid,
     * or with status 500 (Internal Server Error) if the inventar couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/inventars")
    @Timed
    public ResponseEntity<Inventar> updateInventar(@Valid @RequestBody Inventar inventar) throws URISyntaxException {
        log.debug("REST request to update Inventar : {}", inventar);
        if (inventar.getId() == null) {
            return createInventar(inventar);
        }
        Inventar result = inventarRepository.save(inventar);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, inventar.getId().toString()))
            .body(result);
    }

    /**
     * GET  /inventars : get all the inventars.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of inventars in body
     */
    @GetMapping("/inventars")
    @Timed
    public ResponseEntity<List<Inventar>> getAllInventars(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Inventars");
        Page<Inventar> page = inventarRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/inventars");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /inventars/:id : get the "id" inventar.
     *
     * @param id the id of the inventar to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the inventar, or with status 404 (Not Found)
     */
    @GetMapping("/inventars/{id}")
    @Timed
    public ResponseEntity<Inventar> getInventar(@PathVariable Long id) {
        log.debug("REST request to get Inventar : {}", id);
        Inventar inventar = inventarRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(inventar));
    }

    /**
     * DELETE  /inventars/:id : delete the "id" inventar.
     *
     * @param id the id of the inventar to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/inventars/{id}")
    @Timed
    public ResponseEntity<Void> deleteInventar(@PathVariable Long id) {
        log.debug("REST request to delete Inventar : {}", id);
        inventarRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
