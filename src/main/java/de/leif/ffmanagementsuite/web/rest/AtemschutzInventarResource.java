package de.leif.ffmanagementsuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.leif.ffmanagementsuite.domain.AtemschutzInventar;

import de.leif.ffmanagementsuite.repository.AtemschutzInventarRepository;
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
 * REST controller for managing AtemschutzInventar.
 */
@RestController
@RequestMapping("/api")
public class AtemschutzInventarResource {

    private final Logger log = LoggerFactory.getLogger(AtemschutzInventarResource.class);

    private static final String ENTITY_NAME = "atemschutzInventar";

    private final AtemschutzInventarRepository atemschutzInventarRepository;

    public AtemschutzInventarResource(AtemschutzInventarRepository atemschutzInventarRepository) {
        this.atemschutzInventarRepository = atemschutzInventarRepository;
    }

    /**
     * POST  /atemschutz-inventars : Create a new atemschutzInventar.
     *
     * @param atemschutzInventar the atemschutzInventar to create
     * @return the ResponseEntity with status 201 (Created) and with body the new atemschutzInventar, or with status 400 (Bad Request) if the atemschutzInventar has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/atemschutz-inventars")
    @Timed
    public ResponseEntity<AtemschutzInventar> createAtemschutzInventar(@Valid @RequestBody AtemschutzInventar atemschutzInventar) throws URISyntaxException {
        log.debug("REST request to save AtemschutzInventar : {}", atemschutzInventar);
        if (atemschutzInventar.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new atemschutzInventar cannot already have an ID")).body(null);
        }
        AtemschutzInventar result = atemschutzInventarRepository.save(atemschutzInventar);
        return ResponseEntity.created(new URI("/api/atemschutz-inventars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /atemschutz-inventars : Updates an existing atemschutzInventar.
     *
     * @param atemschutzInventar the atemschutzInventar to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated atemschutzInventar,
     * or with status 400 (Bad Request) if the atemschutzInventar is not valid,
     * or with status 500 (Internal Server Error) if the atemschutzInventar couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/atemschutz-inventars")
    @Timed
    public ResponseEntity<AtemschutzInventar> updateAtemschutzInventar(@Valid @RequestBody AtemschutzInventar atemschutzInventar) throws URISyntaxException {
        log.debug("REST request to update AtemschutzInventar : {}", atemschutzInventar);
        if (atemschutzInventar.getId() == null) {
            return createAtemschutzInventar(atemschutzInventar);
        }
        AtemschutzInventar result = atemschutzInventarRepository.save(atemschutzInventar);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, atemschutzInventar.getId().toString()))
            .body(result);
    }

    /**
     * GET  /atemschutz-inventars : get all the atemschutzInventars.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of atemschutzInventars in body
     */
    @GetMapping("/atemschutz-inventars")
    @Timed
    public ResponseEntity<List<AtemschutzInventar>> getAllAtemschutzInventars(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of AtemschutzInventars");
        Page<AtemschutzInventar> page = atemschutzInventarRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/atemschutz-inventars");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /atemschutz-inventars/:id : get the "id" atemschutzInventar.
     *
     * @param id the id of the atemschutzInventar to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the atemschutzInventar, or with status 404 (Not Found)
     */
    @GetMapping("/atemschutz-inventars/{id}")
    @Timed
    public ResponseEntity<AtemschutzInventar> getAtemschutzInventar(@PathVariable Long id) {
        log.debug("REST request to get AtemschutzInventar : {}", id);
        AtemschutzInventar atemschutzInventar = atemschutzInventarRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(atemschutzInventar));
    }

    /**
     * DELETE  /atemschutz-inventars/:id : delete the "id" atemschutzInventar.
     *
     * @param id the id of the atemschutzInventar to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/atemschutz-inventars/{id}")
    @Timed
    public ResponseEntity<Void> deleteAtemschutzInventar(@PathVariable Long id) {
        log.debug("REST request to delete AtemschutzInventar : {}", id);
        atemschutzInventarRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
