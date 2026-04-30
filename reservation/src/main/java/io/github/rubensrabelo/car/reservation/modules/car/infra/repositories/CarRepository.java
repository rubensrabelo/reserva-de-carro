package io.github.rubensrabelo.car.reservation.modules.car.infra.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.rubensrabelo.car.reservation.modules.car.models.Car;
import java.util.List;


public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByModel(String model);
}