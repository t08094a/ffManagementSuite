package de.leif.ffmanagementsuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.leif.ffmanagementsuite.domain.Fuehrerschein;

import de.leif.ffmanagementsuite.repository.FuehrerscheinRepository;
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
 * REST controller for managing Fuehrerschein.
 */
@RestController
@RequestMapping("/api")
public class FuehrerscheinResource {

    private final Logger log = LoggerFactory.getLogger(FuehrerscheinResource.class);

    private static final String ENTITY_NAME = "fuehrerschein";

    private final FuehrerscheinRepository fuehrerscheinRepository;

    public FuehrerscheinResource(FuehrerscheinRepository fuehrerscheinRepository) {
        this.fuehrerscheinRepository = fuehrerscheinRepository;
    }

    /**
     * POST  /fuehrerscheins : Create a new fuehrerschein.
     *
     * @param fuehrerschein the fuehrerschein to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fuehrerschein, or with status 400 (Bad Request) if the fuehrerschein has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fuehrerscheins")
    @Timed
    public ResponseEntity<Fuehrerschein> createFuehrerschein(@Valid @RequestBody Fuehrerschein fuehrerschein) throws URISyntaxException {
        log.debug("REST request to save Fuehrerschein : {}", fuehrerschein);
        if (fuehrerschein.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new fuehrerschein cannot already have an ID")).body(null);
        }
        Fuehrerschein result = fuehrerscheinRepository.save(fuehrerschein);
        return ResponseEntity.created(new URI("/api/fuehrerscheins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fuehrerscheins : Updates an existing fuehrerschein.
     *
     * @param fuehrerschein the fuehrerschein to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fuehrerschein,
     * or with status 400 (Bad Request) if the fuehrerschein is not valid,
     * or with status 500 (Internal Server Error) if the fuehrerschein couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fuehrerscheins")
    @Timed
    public ResponseEntity<Fuehrerschein> updateFuehrerschein(@Valid @RequestBody Fuehrerschein fuehrerschein) throws URISyntaxException {
        log.debug("REST request to update Fuehrerschein : {}", fuehrerschein);
        if (fuehrerschein.getId() == null) {
            return createFuehrerschein(fuehrerschein);
        }
        Fuehrerschein result = fuehrerscheinRepository.save(fuehrerschein);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fuehrerschein.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fuehrerscheins : get all the fuehrerscheins.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of fuehrerscheins in body
     */
    @GetMapping("/fuehrerscheins")
    @Timed
    public ResponseEntity<List<Fuehrerschein>> getAllFuehrerscheins(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Fuehrerscheins");
        Page<Fuehrerschein> page = fuehrerscheinRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/fuehrerscheins");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /fuehrerscheins/:id : get the "id" fuehrerschein.
     *
     * @param id the id of the fuehrerschein to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fuehrerschein, or with status 404 (Not Found)
     */
    @GetMapping("/fuehrerscheins/{id}")
    @Timed
    public ResponseEntity<Fuehrerschein> getFuehrerschein(@PathVariable Long id) {
        log.debug("REST request to get Fuehrerschein : {}", id);
        Fuehrerschein fuehrerschein = fuehrerscheinRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(fuehrerschein));
    }

    /**
     * DELETE  /fuehrerscheins/:id : delete the "id" fuehrerschein.
     *
     * @param id the id of the fuehrerschein to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fuehrerscheins/{id}")
    @Timed
    public ResponseEntity<Void> deleteFuehrerschein(@PathVariable Long id) {
        log.debug("REST request to delete Fuehrerschein : {}", id);
        fuehrerscheinRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
