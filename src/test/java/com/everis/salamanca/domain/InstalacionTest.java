package com.everis.salamanca.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.everis.salamanca.web.rest.TestUtil;

public class InstalacionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Instalacion.class);
        Instalacion instalacion1 = new Instalacion();
        instalacion1.setId(1L);
        Instalacion instalacion2 = new Instalacion();
        instalacion2.setId(instalacion1.getId());
        assertThat(instalacion1).isEqualTo(instalacion2);
        instalacion2.setId(2L);
        assertThat(instalacion1).isNotEqualTo(instalacion2);
        instalacion1.setId(null);
        assertThat(instalacion1).isNotEqualTo(instalacion2);
    }
}
