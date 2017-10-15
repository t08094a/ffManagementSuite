package de.leif.ffmanagementsuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.leif.ffmanagementsuite.domain.Verfuegbarkeit;

import de.leif.ffmanagementsuite.repository.VerfuegbarkeitRepository;
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
 * REST controller for managing Verfuegbarkeit.
 */
@RestController
@RequestMapping("/api")
public class VerfuegbarkeitResource {

    private final Logger log = LoggerFactory.getLogger(VerfuegbarkeitResource.class);

    private static final String ENTITY_NAME = "verfuegbarkeit";

    private final VerfuegbarkeitRepository verfuegbarkeitRepository;

    public VerfuegbarkeitResource(VerfuegbarkeitRepository verfuegbarkeitRepository) {
        this.verfuegbarkeitRepository = verfuegbarkeitRepository;
    }

    /**
     * POST  /verfuegbarkeits : Create a new verfuegbarkeit.
     *
     * @param verfuegbarkeit the verfuegbarkeit to create
     * @return the ResponseEntity with status 201 (Created) and with body the new verfuegbarkeit, or with status 400 (Bad Request) if the verfuegbarkeit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/verfuegbarkeits")
    @Timed
    public ResponseEntity<Verfuegbarkeit> createVerfuegbarkeit(@Valid @RequestBody Verfuegbarkeit verfuegbarkeit) throws URISyntaxException {
        log.debug("REST request to save Verfuegbarkeit : {}", verfuegbarkeit);
        if (verfuegbarkeit.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new verfuegbarkeit cannot already have an ID")).body(null);
        }
        Verfuegbarkeit result = verfuegbarkeitRepository.save(verfuegbarkeit);
        return ResponseEntity.created(new URI("/api/verfuegbarkeits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /verfuegbarkeits : Updates an existing verfuegbarkeit.
     *
     * @param verfuegbarkeit the verfuegbarkeit to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated verfuegbarkeit,
     * or with status 400 (Bad Request) if the verfuegbarkeit is not valid,
     * or with status 500 (Internal Server Error) if the verfuegbarkeit couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/verfuegbarkeits")
    @Timed
    public ResponseEntity<Verfuegbarkeit> updateVerfuegbarkeit(@Valid @RequestBody Verfuegbarkeit verfuegbarkeit) throws URISyntaxException {
        log.debug("REST request to update Verfuegbarkeit : {}", verfuegbarkeit);
        if (verfuegbarkeit.getId() == null) {
            return createVerfuegbarkeit(verfuegbarkeit);
        }
        Verfuegbarkeit result = verfuegbarkeitRepository.save(verfuegbarkeit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, verfuegbarkeit.getId().toString()))
            .body(result);
    }

    /**
     * GET  /verfuegbarkeits : get all the verfuegbarkeits.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of verfuegbarkeits in body
     */
    @GetMapping("/verfuegbarkeits")
    @Timed
    public ResponseEntity<List<Verfuegbarkeit>> getAllVerfuegbarkeits(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Verfuegbarkeits");
        Page<Verfuegbarkeit> page = verfuegbarkeitRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/verfuegbarkeits");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /verfuegbarkeits/:id : get the "id" verfuegbarkeit.
     *
     * @param id the id of the verfuegbarkeit to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the verfuegbarkeit, or with status 404 (Not Found)
     */
    @GetMapping("/verfuegbarkeits/{id}")
    @Timed
    public ResponseEntity<Verfuegbarkeit> getVerfuegbarkeit(@PathVariable Long id) {
        log.debug("REST request to get Verfuegbarkeit : {}", id);
        Verfuegbarkeit verfuegbarkeit = verfuegbarkeitRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(verfuegbarkeit));
    }

    /**
     * DELETE  /verfuegbarkeits/:id : delete the "id" verfuegbarkeit.
     *
     * @param id the id of the verfuegbarkeit to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/verfuegbarkeits/{id}")
    @Timed
    public ResponseEntity<Void> deleteVerfuegbarkeit(@PathVariable Long id) {
        log.debug("REST request to delete Verfuegbarkeit : {}", id);
        verfuegbarkeitRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
