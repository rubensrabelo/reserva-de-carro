package io.github.rubensrabelo.car.reservation.modules.reservation.models;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.rubensrabelo.car.reservation.modules.car.models.Car;
import io.github.rubensrabelo.car.reservation.modules.client.models.Client;
import io.github.rubensrabelo.car.reservation.shared.exceptions.domain.InvalidReservationException;

public class ReservationTest {

    Client client;
    Car car;

    @BeforeEach
    void setUp() {
        client = new Client("José");
        car = new Car("Hatch", 50.0);
    }

    @Test
    void shouldCreateAReservation() {
        int days = 5;

        var reservation = new Reservation(days, client, car);

        Assertions.assertThat(reservation).isNotNull();
    }

    @Test
    void shouldGiveAnErrorWhenCreatingAReservationWithNegativeDays() {

        assertThrows(InvalidReservationException.class, () -> new Reservation(0, client, car));
        assertDoesNotThrow(() -> new Reservation(1, client, car));

        var erro = Assertions.catchThrowable(() -> new Reservation(0, client, car));

        Assertions.assertThat(erro)
                .isInstanceOf(InvalidReservationException.class)
                .hasMessage("The reservation cannot have fewer than 1 day.");
    }

    @Test
    void mustCalculateTheTotalRent() {
        var reservation = new Reservation(3, client, car);

        var total = reservation.calculateTotal();

        Assertions.assertThat(total).isEqualTo(150.0);
    }
}
