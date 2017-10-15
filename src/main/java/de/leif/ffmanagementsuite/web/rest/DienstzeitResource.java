package de.leif.ffmanagementsuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.leif.ffmanagementsuite.domain.Dienstzeit;

import de.leif.ffmanagementsuite.repository.DienstzeitRepository;
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
 * REST controller for managing Dienstzeit.
 */
@RestController
@RequestMapping("/api")
public class DienstzeitResource {

    private final Logger log = LoggerFactory.getLogger(DienstzeitResource.class);

    private static final String ENTITY_NAME = "dienstzeit";

    private final DienstzeitRepository dienstzeitRepository;

    public DienstzeitResource(DienstzeitRepository dienstzeitRepository) {
        this.dienstzeitRepository = dienstzeitRepository;
    }

    /**
     * POST  /dienstzeits : Create a new dienstzeit.
     *
     * @param dienstzeit the dienstzeit to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dienstzeit, or with status 400 (Bad Request) if the dienstzeit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dienstzeits")
    @Timed
    public ResponseEntity<Dienstzeit> createDienstzeit(@Valid @RequestBody Dienstzeit dienstzeit) throws URISyntaxException {
        log.debug("REST request to save Dienstzeit : {}", dienstzeit);
        if (dienstzeit.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new dienstzeit cannot already have an ID")).body(null);
        }
        Dienstzeit result = dienstzeitRepository.save(dienstzeit);
        return ResponseEntity.created(new URI("/api/dienstzeits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dienstzeits : Updates an existing dienstzeit.
     *
     * @param dienstzeit the dienstzeit to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dienstzeit,
     * or with status 400 (Bad Request) if the dienstzeit is not valid,
     * or with status 500 (Internal Server Error) if the dienstzeit couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dienstzeits")
    @Timed
    public ResponseEntity<Dienstzeit> updateDienstzeit(@Valid @RequestBody Dienstzeit dienstzeit) throws URISyntaxException {
        log.debug("REST request to update Dienstzeit : {}", dienstzeit);
        if (dienstzeit.getId() == null) {
            return createDienstzeit(dienstzeit);
        }
        Dienstzeit result = dienstzeitRepository.save(dienstzeit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dienstzeit.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dienstzeits : get all the dienstzeits.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of dienstzeits in body
     */
    @GetMapping("/dienstzeits")
    @Timed
    public ResponseEntity<List<Dienstzeit>> getAllDienstzeits(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Dienstzeits");
        Page<Dienstzeit> page = dienstzeitRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dienstzeits");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /dienstzeits/:id : get the "id" dienstzeit.
     *
     * @param id the id of the dienstzeit to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dienstzeit, or with status 404 (Not Found)
     */
    @GetMapping("/dienstzeits/{id}")
    @Timed
    public ResponseEntity<Dienstzeit> getDienstzeit(@PathVariable Long id) {
        log.debug("REST request to get Dienstzeit : {}", id);
        Dienstzeit dienstzeit = dienstzeitRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dienstzeit));
    }

    /**
     * DELETE  /dienstzeits/:id : delete the "id" dienstzeit.
     *
     * @param id the id of the dienstzeit to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dienstzeits/{id}")
    @Timed
    public ResponseEntity<Void> deleteDienstzeit(@PathVariable Long id) {
        log.debug("REST request to delete Dienstzeit : {}", id);
        dienstzeitRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
