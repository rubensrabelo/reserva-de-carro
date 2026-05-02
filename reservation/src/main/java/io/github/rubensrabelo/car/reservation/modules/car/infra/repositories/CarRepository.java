package io.github.rubensrabelo.car.reservation.modules.car.infra.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.rubensrabelo.car.reservation.modules.car.entity.CarEntity;


public interface CarRepository extends JpaRepository<CarEntity, Long> {
    List<CarEntity> findByModel(String model);
}