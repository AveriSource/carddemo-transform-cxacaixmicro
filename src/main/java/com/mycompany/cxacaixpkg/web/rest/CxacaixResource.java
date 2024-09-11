package com.mycompany.cxacaixpkg.web.rest;

import com.mycompany.cxacaixpkg.domain.Cxacaix;
import com.mycompany.cxacaixpkg.repository.CxacaixRepository;
import com.mycompany.cxacaixpkg.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.cxacaixpkg.domain.Cxacaix}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CxacaixResource {

    private final Logger log = LoggerFactory.getLogger(CxacaixResource.class);

    private static final String ENTITY_NAME = "cxacaixmicroCxacaix";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CxacaixRepository cxacaixRepository;

    public CxacaixResource(CxacaixRepository cxacaixRepository) {
        this.cxacaixRepository = cxacaixRepository;
    }

    /**
     * {@code POST  /cxacaixes} : Create a new cxacaix.
     *
     * @param cxacaix the cxacaix to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cxacaix, or with status {@code 400 (Bad Request)} if the cxacaix has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cxacaixes")
    public ResponseEntity<Cxacaix> createCxacaix(@Valid @RequestBody Cxacaix cxacaix) throws URISyntaxException {
        log.debug("REST request to save Cxacaix : {}", cxacaix);
        if (cxacaix.getId() != null) {
            throw new BadRequestAlertException("A new cxacaix cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Cxacaix result = cxacaixRepository.save(cxacaix);
        return ResponseEntity
            .created(new URI("/api/cxacaixes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cxacaixes/:id} : Updates an existing cxacaix.
     *
     * @param id the id of the cxacaix to save.
     * @param cxacaix the cxacaix to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cxacaix,
     * or with status {@code 400 (Bad Request)} if the cxacaix is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cxacaix couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cxacaixes/{id}")
    public ResponseEntity<Cxacaix> updateCxacaix(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Cxacaix cxacaix
    ) throws URISyntaxException {
        log.debug("REST request to update Cxacaix : {}, {}", id, cxacaix);
        if (cxacaix.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cxacaix.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cxacaixRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Cxacaix result = cxacaixRepository.save(cxacaix);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cxacaix.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cxacaixes/:id} : Partial updates given fields of an existing cxacaix, field will ignore if it is null
     *
     * @param id the id of the cxacaix to save.
     * @param cxacaix the cxacaix to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cxacaix,
     * or with status {@code 400 (Bad Request)} if the cxacaix is not valid,
     * or with status {@code 404 (Not Found)} if the cxacaix is not found,
     * or with status {@code 500 (Internal Server Error)} if the cxacaix couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cxacaixes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Cxacaix> partialUpdateCxacaix(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Cxacaix cxacaix
    ) throws URISyntaxException {
        log.debug("REST request to partial update Cxacaix partially : {}, {}", id, cxacaix);
        if (cxacaix.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cxacaix.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cxacaixRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Cxacaix> result = cxacaixRepository
            .findById(cxacaix.getId())
            .map(
                existingCxacaix -> {
                    if (cxacaix.getCxacaixId() != null) {
                        existingCxacaix.setCxacaixId(cxacaix.getCxacaixId());
                    }
                    if (cxacaix.getXrefCardNum() != null) {
                        existingCxacaix.setXrefCardNum(cxacaix.getXrefCardNum());
                    }
                    if (cxacaix.getXrefCustId() != null) {
                        existingCxacaix.setXrefCustId(cxacaix.getXrefCustId());
                    }
                    if (cxacaix.getXrefAcctId() != null) {
                        existingCxacaix.setXrefAcctId(cxacaix.getXrefAcctId());
                    }
                    if (cxacaix.getFiller() != null) {
                        existingCxacaix.setFiller(cxacaix.getFiller());
                    }

                    return existingCxacaix;
                }
            )
            .map(cxacaixRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cxacaix.getId().toString())
        );
    }

    /**
     * {@code GET  /cxacaixes} : get all the cxacaixes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cxacaixes in body.
     */
    @GetMapping("/cxacaixes")
    public ResponseEntity<List<Cxacaix>> getAllCxacaixes(Pageable pageable) {
        log.debug("REST request to get a page of Cxacaixes");
        Page<Cxacaix> page = cxacaixRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cxacaixes/:id} : get the "id" cxacaix.
     *
     * @param id the id of the cxacaix to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cxacaix, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cxacaixes/{id}")
    public ResponseEntity<Cxacaix> getCxacaix(@PathVariable Long id) {
        log.debug("REST request to get Cxacaix : {}", id);
        Optional<Cxacaix> cxacaix = cxacaixRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(cxacaix);
    }

    /**
     * {@code DELETE  /cxacaixes/:id} : delete the "id" cxacaix.
     *
     * @param id the id of the cxacaix to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cxacaixes/{id}")
    public ResponseEntity<Void> deleteCxacaix(@PathVariable Long id) {
        log.debug("REST request to delete Cxacaix : {}", id);
        cxacaixRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
