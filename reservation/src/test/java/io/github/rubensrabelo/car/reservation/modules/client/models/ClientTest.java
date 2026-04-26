package io.github.rubensrabelo.car.reservation.modules.client.models;


import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ClientTest {
    
    @Test
    void shouldCreateClientWithName() {
        Client client = new Client("Rubens");

        String name = client.getName();

        assertNotNull(name);

        assertThat(name).isEqualTo("Rubens");
        assertThat(name).isLessThan("Rubens5");

        assertTrue(name.startsWith("R"));
        assertFalse(name.length() == 100);

        assertThat(name.length()).isLessThan(100);

        assertThat(name).contains("Ru");
    }

    @Test
    void shouldCreateClientWithoutName() {
        
        Client client = new Client(null);

        String name = client.getName();

        assertNull(name);
    }
}
