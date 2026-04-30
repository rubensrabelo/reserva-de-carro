package io.github.rubensrabelo.car.reservation.modules.car.infra.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import io.github.rubensrabelo.car.reservation.modules.car.entity.CarEntity;

@DataJpaTest
@ActiveProfiles("test")
public class CarRepositoryTest {

    @Autowired
    CarRepository repository;

    CarEntity car;

    @BeforeEach
    void setUp(){
        car = new CarEntity("Honda Civic", 200.0, 2027);
    }

    @Test
    void shouldSaveACar(){
        var entity = new CarEntity("Sedan", 100.0, 2027);

        repository.save(entity);

        assertNotNull(entity.getId());
    }

    @Test
    void shouldGetACarById(){
        var carroSalvo = repository.save(car);

        Optional<CarEntity> carroEncontrado = repository.findById(carroSalvo.getId());

        assertThat(carroEncontrado).isPresent();
        assertThat(carroEncontrado.get().getModel()).isEqualTo("Honda Civic");
    }

    @Test
    void shouldUpdateACarAtualizarCarro(){
        var carroSalvo = repository.save(car);

        carroSalvo.setYear(2028);

        var carroAtualizado = repository.save(carroSalvo);

        assertThat(carroAtualizado.getYear()).isEqualTo(2028);
    }

    @Test
    void shouldDeleteACar(){
        var carroSalvo = repository.save(car);

        repository.deleteById(carroSalvo.getId());

        Optional<CarEntity> carroEncontrado = repository.findById(carroSalvo.getId());

        assertThat(carroEncontrado).isEmpty();

    }
}
