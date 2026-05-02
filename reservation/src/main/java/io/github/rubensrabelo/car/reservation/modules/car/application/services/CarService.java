package io.github.rubensrabelo.car.reservation.modules.car.application.services;

import java.util.List;

import org.springframework.stereotype.Service;

import io.github.rubensrabelo.car.reservation.modules.car.entity.CarEntity;
import io.github.rubensrabelo.car.reservation.modules.car.infra.repositories.CarRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class CarService {

    private final CarRepository repository;

    public CarService(CarRepository repository) {
        this.repository = repository;
    }

    public CarEntity save(CarEntity car){
        if(car.getDailyValue() <= 0){
            throw new IllegalArgumentException("The daily rate cannot be negative");
        }
        return repository.save(car);
    }

    public CarEntity update(Long id, CarEntity udpateCar){
        CarEntity existedCar = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Car not found"));

        existedCar.setYear(udpateCar.getYear());
        existedCar.setModel(udpateCar.getModel());
        existedCar.setDailyValue(udpateCar.getDailyValue());

        return repository.save(existedCar);
    }

    public void delete(Long id){
        var existedCar = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Car not found"));
        repository.delete(existedCar);
    }

    public CarEntity findById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Car not found"));
    }

    public List<CarEntity> findAll(){
        return repository.findAll();
    }
}
