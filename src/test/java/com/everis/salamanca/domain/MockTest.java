package com.everis.salamanca.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.everis.salamanca.web.rest.TestUtil;

public class MockTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mock.class);
        Mock mock1 = new Mock();
        mock1.setId(1L);
        Mock mock2 = new Mock();
        mock2.setId(mock1.getId());
        assertThat(mock1).isEqualTo(mock2);
        mock2.setId(2L);
        assertThat(mock1).isNotEqualTo(mock2);
        mock1.setId(null);
        assertThat(mock1).isNotEqualTo(mock2);
    }
}
