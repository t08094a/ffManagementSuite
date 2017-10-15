package de.leif.ffmanagementsuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.leif.ffmanagementsuite.domain.InventarKategorie;

import de.leif.ffmanagementsuite.repository.InventarKategorieRepository;
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
 * REST controller for managing InventarKategorie.
 */
@RestController
@RequestMapping("/api")
public class InventarKategorieResource {

    private final Logger log = LoggerFactory.getLogger(InventarKategorieResource.class);

    private static final String ENTITY_NAME = "inventarKategorie";

    private final InventarKategorieRepository inventarKategorieRepository;

    public InventarKategorieResource(InventarKategorieRepository inventarKategorieRepository) {
        this.inventarKategorieRepository = inventarKategorieRepository;
    }

    /**
     * POST  /inventar-kategories : Create a new inventarKategorie.
     *
     * @param inventarKategorie the inventarKategorie to create
     * @return the ResponseEntity with status 201 (Created) and with body the new inventarKategorie, or with status 400 (Bad Request) if the inventarKategorie has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/inventar-kategories")
    @Timed
    public ResponseEntity<InventarKategorie> createInventarKategorie(@Valid @RequestBody InventarKategorie inventarKategorie) throws URISyntaxException {
        log.debug("REST request to save InventarKategorie : {}", inventarKategorie);
        if (inventarKategorie.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new inventarKategorie cannot already have an ID")).body(null);
        }
        InventarKategorie result = inventarKategorieRepository.save(inventarKategorie);
        return ResponseEntity.created(new URI("/api/inventar-kategories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /inventar-kategories : Updates an existing inventarKategorie.
     *
     * @param inventarKategorie the inventarKategorie to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated inventarKategorie,
     * or with status 400 (Bad Request) if the inventarKategorie is not valid,
     * or with status 500 (Internal Server Error) if the inventarKategorie couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/inventar-kategories")
    @Timed
    public ResponseEntity<InventarKategorie> updateInventarKategorie(@Valid @RequestBody InventarKategorie inventarKategorie) throws URISyntaxException {
        log.debug("REST request to update InventarKategorie : {}", inventarKategorie);
        if (inventarKategorie.getId() == null) {
            return createInventarKategorie(inventarKategorie);
        }
        InventarKategorie result = inventarKategorieRepository.save(inventarKategorie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, inventarKategorie.getId().toString()))
            .body(result);
    }

    /**
     * GET  /inventar-kategories : get all the inventarKategories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of inventarKategories in body
     */
    @GetMapping("/inventar-kategories")
    @Timed
    public ResponseEntity<List<InventarKategorie>> getAllInventarKategories(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of InventarKategories");
        Page<InventarKategorie> page = inventarKategorieRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/inventar-kategories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /inventar-kategories/:id : get the "id" inventarKategorie.
     *
     * @param id the id of the inventarKategorie to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the inventarKategorie, or with status 404 (Not Found)
     */
    @GetMapping("/inventar-kategories/{id}")
    @Timed
    public ResponseEntity<InventarKategorie> getInventarKategorie(@PathVariable Long id) {
        log.debug("REST request to get InventarKategorie : {}", id);
        InventarKategorie inventarKategorie = inventarKategorieRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(inventarKategorie));
    }

    /**
     * DELETE  /inventar-kategories/:id : delete the "id" inventarKategorie.
     *
     * @param id the id of the inventarKategorie to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/inventar-kategories/{id}")
    @Timed
    public ResponseEntity<Void> deleteInventarKategorie(@PathVariable Long id) {
        log.debug("REST request to delete InventarKategorie : {}", id);
        inventarKategorieRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
