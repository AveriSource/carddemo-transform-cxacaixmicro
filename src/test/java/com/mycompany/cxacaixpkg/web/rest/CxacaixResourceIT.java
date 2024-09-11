package com.mycompany.cxacaixpkg.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.cxacaixpkg.IntegrationTest;
import com.mycompany.cxacaixpkg.domain.Cxacaix;
import com.mycompany.cxacaixpkg.repository.CxacaixRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CxacaixResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CxacaixResourceIT {

    private static final Long DEFAULT_CXACAIX_ID = 1L;
    private static final Long UPDATED_CXACAIX_ID = 2L;

    private static final String DEFAULT_XREF_CARD_NUM = "AAAAAAAAAA";
    private static final String UPDATED_XREF_CARD_NUM = "BBBBBBBBBB";

    private static final Integer DEFAULT_XREF_CUST_ID = 9;
    private static final Integer UPDATED_XREF_CUST_ID = 8;

    private static final Integer DEFAULT_XREF_ACCT_ID = 11;
    private static final Integer UPDATED_XREF_ACCT_ID = 10;

    private static final String DEFAULT_FILLER = "AAAAAAAAAA";
    private static final String UPDATED_FILLER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cxacaixes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CxacaixRepository cxacaixRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCxacaixMockMvc;

    private Cxacaix cxacaix;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cxacaix createEntity(EntityManager em) {
        Cxacaix cxacaix = new Cxacaix()
            .cxacaixId(DEFAULT_CXACAIX_ID)
            .xrefCardNum(DEFAULT_XREF_CARD_NUM)
            .xrefCustId(DEFAULT_XREF_CUST_ID)
            .xrefAcctId(DEFAULT_XREF_ACCT_ID)
            .filler(DEFAULT_FILLER);
        return cxacaix;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cxacaix createUpdatedEntity(EntityManager em) {
        Cxacaix cxacaix = new Cxacaix()
            .cxacaixId(UPDATED_CXACAIX_ID)
            .xrefCardNum(UPDATED_XREF_CARD_NUM)
            .xrefCustId(UPDATED_XREF_CUST_ID)
            .xrefAcctId(UPDATED_XREF_ACCT_ID)
            .filler(UPDATED_FILLER);
        return cxacaix;
    }

    @BeforeEach
    public void initTest() {
        cxacaix = createEntity(em);
    }

    @Test
    @Transactional
    void createCxacaix() throws Exception {
        int databaseSizeBeforeCreate = cxacaixRepository.findAll().size();
        // Create the Cxacaix
        restCxacaixMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cxacaix)))
            .andExpect(status().isCreated());

        // Validate the Cxacaix in the database
        List<Cxacaix> cxacaixList = cxacaixRepository.findAll();
        assertThat(cxacaixList).hasSize(databaseSizeBeforeCreate + 1);
        Cxacaix testCxacaix = cxacaixList.get(cxacaixList.size() - 1);
        assertThat(testCxacaix.getCxacaixId()).isEqualTo(DEFAULT_CXACAIX_ID);
        assertThat(testCxacaix.getXrefCardNum()).isEqualTo(DEFAULT_XREF_CARD_NUM);
        assertThat(testCxacaix.getXrefCustId()).isEqualTo(DEFAULT_XREF_CUST_ID);
        assertThat(testCxacaix.getXrefAcctId()).isEqualTo(DEFAULT_XREF_ACCT_ID);
        assertThat(testCxacaix.getFiller()).isEqualTo(DEFAULT_FILLER);
    }

    @Test
    @Transactional
    void createCxacaixWithExistingId() throws Exception {
        // Create the Cxacaix with an existing ID
        cxacaix.setId(1L);

        int databaseSizeBeforeCreate = cxacaixRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCxacaixMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cxacaix)))
            .andExpect(status().isBadRequest());

        // Validate the Cxacaix in the database
        List<Cxacaix> cxacaixList = cxacaixRepository.findAll();
        assertThat(cxacaixList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCxacaixes() throws Exception {
        // Initialize the database
        cxacaixRepository.saveAndFlush(cxacaix);

        // Get all the cxacaixList
        restCxacaixMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cxacaix.getId().intValue())))
            .andExpect(jsonPath("$.[*].cxacaixId").value(hasItem(DEFAULT_CXACAIX_ID.intValue())))
            .andExpect(jsonPath("$.[*].xrefCardNum").value(hasItem(DEFAULT_XREF_CARD_NUM)))
            .andExpect(jsonPath("$.[*].xrefCustId").value(hasItem(DEFAULT_XREF_CUST_ID)))
            .andExpect(jsonPath("$.[*].xrefAcctId").value(hasItem(DEFAULT_XREF_ACCT_ID)))
            .andExpect(jsonPath("$.[*].filler").value(hasItem(DEFAULT_FILLER)));
    }

    @Test
    @Transactional
    void getCxacaix() throws Exception {
        // Initialize the database
        cxacaixRepository.saveAndFlush(cxacaix);

        // Get the cxacaix
        restCxacaixMockMvc
            .perform(get(ENTITY_API_URL_ID, cxacaix.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cxacaix.getId().intValue()))
            .andExpect(jsonPath("$.cxacaixId").value(DEFAULT_CXACAIX_ID.intValue()))
            .andExpect(jsonPath("$.xrefCardNum").value(DEFAULT_XREF_CARD_NUM))
            .andExpect(jsonPath("$.xrefCustId").value(DEFAULT_XREF_CUST_ID))
            .andExpect(jsonPath("$.xrefAcctId").value(DEFAULT_XREF_ACCT_ID))
            .andExpect(jsonPath("$.filler").value(DEFAULT_FILLER));
    }

    @Test
    @Transactional
    void getNonExistingCxacaix() throws Exception {
        // Get the cxacaix
        restCxacaixMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCxacaix() throws Exception {
        // Initialize the database
        cxacaixRepository.saveAndFlush(cxacaix);

        int databaseSizeBeforeUpdate = cxacaixRepository.findAll().size();

        // Update the cxacaix
        Cxacaix updatedCxacaix = cxacaixRepository.findById(cxacaix.getId()).get();
        // Disconnect from session so that the updates on updatedCxacaix are not directly saved in db
        em.detach(updatedCxacaix);
        updatedCxacaix
            .cxacaixId(UPDATED_CXACAIX_ID)
            .xrefCardNum(UPDATED_XREF_CARD_NUM)
            .xrefCustId(UPDATED_XREF_CUST_ID)
            .xrefAcctId(UPDATED_XREF_ACCT_ID)
            .filler(UPDATED_FILLER);

        restCxacaixMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCxacaix.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCxacaix))
            )
            .andExpect(status().isOk());

        // Validate the Cxacaix in the database
        List<Cxacaix> cxacaixList = cxacaixRepository.findAll();
        assertThat(cxacaixList).hasSize(databaseSizeBeforeUpdate);
        Cxacaix testCxacaix = cxacaixList.get(cxacaixList.size() - 1);
        assertThat(testCxacaix.getCxacaixId()).isEqualTo(UPDATED_CXACAIX_ID);
        assertThat(testCxacaix.getXrefCardNum()).isEqualTo(UPDATED_XREF_CARD_NUM);
        assertThat(testCxacaix.getXrefCustId()).isEqualTo(UPDATED_XREF_CUST_ID);
        assertThat(testCxacaix.getXrefAcctId()).isEqualTo(UPDATED_XREF_ACCT_ID);
        assertThat(testCxacaix.getFiller()).isEqualTo(UPDATED_FILLER);
    }

    @Test
    @Transactional
    void putNonExistingCxacaix() throws Exception {
        int databaseSizeBeforeUpdate = cxacaixRepository.findAll().size();
        cxacaix.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCxacaixMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cxacaix.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cxacaix))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cxacaix in the database
        List<Cxacaix> cxacaixList = cxacaixRepository.findAll();
        assertThat(cxacaixList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCxacaix() throws Exception {
        int databaseSizeBeforeUpdate = cxacaixRepository.findAll().size();
        cxacaix.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCxacaixMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cxacaix))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cxacaix in the database
        List<Cxacaix> cxacaixList = cxacaixRepository.findAll();
        assertThat(cxacaixList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCxacaix() throws Exception {
        int databaseSizeBeforeUpdate = cxacaixRepository.findAll().size();
        cxacaix.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCxacaixMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cxacaix)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cxacaix in the database
        List<Cxacaix> cxacaixList = cxacaixRepository.findAll();
        assertThat(cxacaixList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCxacaixWithPatch() throws Exception {
        // Initialize the database
        cxacaixRepository.saveAndFlush(cxacaix);

        int databaseSizeBeforeUpdate = cxacaixRepository.findAll().size();

        // Update the cxacaix using partial update
        Cxacaix partialUpdatedCxacaix = new Cxacaix();
        partialUpdatedCxacaix.setId(cxacaix.getId());

        partialUpdatedCxacaix.cxacaixId(UPDATED_CXACAIX_ID).xrefAcctId(UPDATED_XREF_ACCT_ID);

        restCxacaixMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCxacaix.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCxacaix))
            )
            .andExpect(status().isOk());

        // Validate the Cxacaix in the database
        List<Cxacaix> cxacaixList = cxacaixRepository.findAll();
        assertThat(cxacaixList).hasSize(databaseSizeBeforeUpdate);
        Cxacaix testCxacaix = cxacaixList.get(cxacaixList.size() - 1);
        assertThat(testCxacaix.getCxacaixId()).isEqualTo(UPDATED_CXACAIX_ID);
        assertThat(testCxacaix.getXrefCardNum()).isEqualTo(DEFAULT_XREF_CARD_NUM);
        assertThat(testCxacaix.getXrefCustId()).isEqualTo(DEFAULT_XREF_CUST_ID);
        assertThat(testCxacaix.getXrefAcctId()).isEqualTo(UPDATED_XREF_ACCT_ID);
        assertThat(testCxacaix.getFiller()).isEqualTo(DEFAULT_FILLER);
    }

    @Test
    @Transactional
    void fullUpdateCxacaixWithPatch() throws Exception {
        // Initialize the database
        cxacaixRepository.saveAndFlush(cxacaix);

        int databaseSizeBeforeUpdate = cxacaixRepository.findAll().size();

        // Update the cxacaix using partial update
        Cxacaix partialUpdatedCxacaix = new Cxacaix();
        partialUpdatedCxacaix.setId(cxacaix.getId());

        partialUpdatedCxacaix
            .cxacaixId(UPDATED_CXACAIX_ID)
            .xrefCardNum(UPDATED_XREF_CARD_NUM)
            .xrefCustId(UPDATED_XREF_CUST_ID)
            .xrefAcctId(UPDATED_XREF_ACCT_ID)
            .filler(UPDATED_FILLER);

        restCxacaixMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCxacaix.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCxacaix))
            )
            .andExpect(status().isOk());

        // Validate the Cxacaix in the database
        List<Cxacaix> cxacaixList = cxacaixRepository.findAll();
        assertThat(cxacaixList).hasSize(databaseSizeBeforeUpdate);
        Cxacaix testCxacaix = cxacaixList.get(cxacaixList.size() - 1);
        assertThat(testCxacaix.getCxacaixId()).isEqualTo(UPDATED_CXACAIX_ID);
        assertThat(testCxacaix.getXrefCardNum()).isEqualTo(UPDATED_XREF_CARD_NUM);
        assertThat(testCxacaix.getXrefCustId()).isEqualTo(UPDATED_XREF_CUST_ID);
        assertThat(testCxacaix.getXrefAcctId()).isEqualTo(UPDATED_XREF_ACCT_ID);
        assertThat(testCxacaix.getFiller()).isEqualTo(UPDATED_FILLER);
    }

    @Test
    @Transactional
    void patchNonExistingCxacaix() throws Exception {
        int databaseSizeBeforeUpdate = cxacaixRepository.findAll().size();
        cxacaix.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCxacaixMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cxacaix.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cxacaix))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cxacaix in the database
        List<Cxacaix> cxacaixList = cxacaixRepository.findAll();
        assertThat(cxacaixList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCxacaix() throws Exception {
        int databaseSizeBeforeUpdate = cxacaixRepository.findAll().size();
        cxacaix.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCxacaixMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cxacaix))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cxacaix in the database
        List<Cxacaix> cxacaixList = cxacaixRepository.findAll();
        assertThat(cxacaixList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCxacaix() throws Exception {
        int databaseSizeBeforeUpdate = cxacaixRepository.findAll().size();
        cxacaix.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCxacaixMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cxacaix)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cxacaix in the database
        List<Cxacaix> cxacaixList = cxacaixRepository.findAll();
        assertThat(cxacaixList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCxacaix() throws Exception {
        // Initialize the database
        cxacaixRepository.saveAndFlush(cxacaix);

        int databaseSizeBeforeDelete = cxacaixRepository.findAll().size();

        // Delete the cxacaix
        restCxacaixMockMvc
            .perform(delete(ENTITY_API_URL_ID, cxacaix.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cxacaix> cxacaixList = cxacaixRepository.findAll();
        assertThat(cxacaixList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
