package io.github.rubensrabelo.car.reservation.modules.car.infra.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import io.github.rubensrabelo.car.reservation.modules.car.entity.CarEntity;

@DataJpaTest
@ActiveProfiles("test")
public class CarRepositorySQLTest {

    @Autowired
    CarRepository repository;

    @Test
    @Sql(scripts = "/sql/popular-cars.sql")
    void shouldSearchCarByModel() {
        List<CarEntity> cars = repository.findByModel("SUV");

        var car = cars.stream().findFirst().get();

        assertEquals(1, cars.size());

        assertThat(car.getDailyValue()).isEqualTo(150.0);
        assertThat(car.getModel()).isEqualTo("SUV");
    }
}