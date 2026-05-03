package io.github.rubensrabelo.car.reservation.modules.car.api.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.rubensrabelo.car.reservation.modules.car.application.services.CarService;
import io.github.rubensrabelo.car.reservation.modules.car.entity.CarEntity;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("cars")
public class CarController {
    private final CarService service;

    public CarController(CarService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody CarEntity carro){
        try {
            var carroSalvo = service.save(carro);
            return ResponseEntity.status(HttpStatus.CREATED).body(carroSalvo);
        } catch (IllegalArgumentException e){
            return ResponseEntity
                    .status(HttpStatus.UNPROCESSABLE_CONTENT)
                    .body(e.getMessage());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<CarEntity> detalhesCarro(@PathVariable("id") Long id){
        try {
            var carFound = service.findById(id);
            return ResponseEntity.ok(carFound);
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<CarEntity>> listar(){
        return ResponseEntity.ok(service.findAll());
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> atualizar(
            @PathVariable Long id, @RequestBody CarEntity updateData){
        try {
            service.update(id, updateData);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}
