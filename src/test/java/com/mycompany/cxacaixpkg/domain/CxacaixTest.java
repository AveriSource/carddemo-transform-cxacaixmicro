package com.mycompany.cxacaixpkg.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.cxacaixpkg.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CxacaixTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cxacaix.class);
        Cxacaix cxacaix1 = new Cxacaix();
        cxacaix1.setId(1L);
        Cxacaix cxacaix2 = new Cxacaix();
        cxacaix2.setId(cxacaix1.getId());
        assertThat(cxacaix1).isEqualTo(cxacaix2);
        cxacaix2.setId(2L);
        assertThat(cxacaix1).isNotEqualTo(cxacaix2);
        cxacaix1.setId(null);
        assertThat(cxacaix1).isNotEqualTo(cxacaix2);
    }
}
