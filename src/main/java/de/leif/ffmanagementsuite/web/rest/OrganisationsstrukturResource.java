package de.leif.ffmanagementsuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.leif.ffmanagementsuite.domain.Organisationsstruktur;

import de.leif.ffmanagementsuite.repository.OrganisationsstrukturRepository;
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
 * REST controller for managing Organisationsstruktur.
 */
@RestController
@RequestMapping("/api")
public class OrganisationsstrukturResource {

    private final Logger log = LoggerFactory.getLogger(OrganisationsstrukturResource.class);

    private static final String ENTITY_NAME = "organisationsstruktur";

    private final OrganisationsstrukturRepository organisationsstrukturRepository;

    public OrganisationsstrukturResource(OrganisationsstrukturRepository organisationsstrukturRepository) {
        this.organisationsstrukturRepository = organisationsstrukturRepository;
    }

    /**
     * POST  /organisationsstrukturs : Create a new organisationsstruktur.
     *
     * @param organisationsstruktur the organisationsstruktur to create
     * @return the ResponseEntity with status 201 (Created) and with body the new organisationsstruktur, or with status 400 (Bad Request) if the organisationsstruktur has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/organisationsstrukturs")
    @Timed
    public ResponseEntity<Organisationsstruktur> createOrganisationsstruktur(@Valid @RequestBody Organisationsstruktur organisationsstruktur) throws URISyntaxException {
        log.debug("REST request to save Organisationsstruktur : {}", organisationsstruktur);
        if (organisationsstruktur.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new organisationsstruktur cannot already have an ID")).body(null);
        }
        Organisationsstruktur result = organisationsstrukturRepository.save(organisationsstruktur);
        return ResponseEntity.created(new URI("/api/organisationsstrukturs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /organisationsstrukturs : Updates an existing organisationsstruktur.
     *
     * @param organisationsstruktur the organisationsstruktur to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated organisationsstruktur,
     * or with status 400 (Bad Request) if the organisationsstruktur is not valid,
     * or with status 500 (Internal Server Error) if the organisationsstruktur couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/organisationsstrukturs")
    @Timed
    public ResponseEntity<Organisationsstruktur> updateOrganisationsstruktur(@Valid @RequestBody Organisationsstruktur organisationsstruktur) throws URISyntaxException {
        log.debug("REST request to update Organisationsstruktur : {}", organisationsstruktur);
        if (organisationsstruktur.getId() == null) {
            return createOrganisationsstruktur(organisationsstruktur);
        }
        Organisationsstruktur result = organisationsstrukturRepository.save(organisationsstruktur);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, organisationsstruktur.getId().toString()))
            .body(result);
    }

    /**
     * GET  /organisationsstrukturs : get all the organisationsstrukturs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of organisationsstrukturs in body
     */
    @GetMapping("/organisationsstrukturs")
    @Timed
    public ResponseEntity<List<Organisationsstruktur>> getAllOrganisationsstrukturs(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Organisationsstrukturs");
        Page<Organisationsstruktur> page = organisationsstrukturRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/organisationsstrukturs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /organisationsstrukturs/:id : get the "id" organisationsstruktur.
     *
     * @param id the id of the organisationsstruktur to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the organisationsstruktur, or with status 404 (Not Found)
     */
    @GetMapping("/organisationsstrukturs/{id}")
    @Timed
    public ResponseEntity<Organisationsstruktur> getOrganisationsstruktur(@PathVariable Long id) {
        log.debug("REST request to get Organisationsstruktur : {}", id);
        Organisationsstruktur organisationsstruktur = organisationsstrukturRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(organisationsstruktur));
    }

    /**
     * DELETE  /organisationsstrukturs/:id : delete the "id" organisationsstruktur.
     *
     * @param id the id of the organisationsstruktur to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/organisationsstrukturs/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrganisationsstruktur(@PathVariable Long id) {
        log.debug("REST request to delete Organisationsstruktur : {}", id);
        organisationsstrukturRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
