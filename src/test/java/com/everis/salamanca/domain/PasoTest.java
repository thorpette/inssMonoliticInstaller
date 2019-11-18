package com.everis.salamanca.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.everis.salamanca.web.rest.TestUtil;

public class PasoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Paso.class);
        Paso paso1 = new Paso();
        paso1.setId(1L);
        Paso paso2 = new Paso();
        paso2.setId(paso1.getId());
        assertThat(paso1).isEqualTo(paso2);
        paso2.setId(2L);
        assertThat(paso1).isNotEqualTo(paso2);
        paso1.setId(null);
        assertThat(paso1).isNotEqualTo(paso2);
    }
}
