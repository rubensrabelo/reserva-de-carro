package io.github.rubensrabelo.car.reservation.modules.car.application.services;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import io.github.rubensrabelo.car.reservation.modules.car.entity.CarEntity;
import io.github.rubensrabelo.car.reservation.modules.car.infra.repositories.CarRepository;
import jakarta.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class carServiceTest {

    @InjectMocks
    CarService service;

    @Mock
    CarRepository repository;

    @Test
    void shouldSaveACar(){
        CarEntity carToSave = new CarEntity("Sedan", 10.0, 2027);

        CarEntity carToReturn = new CarEntity("Sedan", 10.0, 2027);
        carToReturn.setId(1L);

        when( repository.save(any()) ).thenReturn(carToReturn);

        var carSave = service.save(carToSave);

        assertNotNull(carSave);
        assertEquals("Sedan", carSave.getModel());

        Mockito.verify(repository).save(any());

    }

    @Test
    void shouldGiveAnErrorWhenTryingToSaveACarWithANegativeDailyValue(){
        CarEntity car = new CarEntity("Sedan", 0, 2027);

        var err = catchThrowable( () -> service.save(car) );

        assertThat(err).isInstanceOf(IllegalArgumentException.class);

        Mockito.verify(repository, Mockito.never()).save(any());
    }

    @Test
    void shouldUpdateACar(){

        var carExist = new CarEntity("Gol", 80.0, 2026);
        when(repository.findById(1L)).thenReturn(Optional.of(carExist));

        var carUpdated = new CarEntity("Gol", 80.0, 2026);
        carUpdated.setId(1L);
        when(repository.save(any())).thenReturn(carUpdated);

        Long id = 1L;
        var car = new CarEntity("Sedan", 0, 2027);

        var result = service.update(id, car);

        assertEquals(result.getModel(), "Gol");
        Mockito.verify(repository, Mockito.times(1)).save(any());
    }

    @Test
    void shouldGiveErrorWhenTryingToUpdateNonExistentCar(){
        Long id = 1L;
        var car = new CarEntity("Sedan", 10, 2027);

        when(repository.findById(any())).thenReturn(Optional.empty());

        var err = catchThrowable(() -> service.update(id, car));

        assertThat(err).isInstanceOf(EntityNotFoundException.class);

        Mockito.verify(repository, Mockito.never()).save(any());
    }

    @Test
    void shouldGiveAnErrorWhenTryingToDeleteNonExistingCar(){
        Long id = 1L;

        when(repository.findById(any())).thenReturn(Optional.empty());

        var err = catchThrowable(() -> service.delete(id));

        assertThat(err).isInstanceOf(EntityNotFoundException.class);

        Mockito.verify(repository, Mockito.never()).delete(any());
    }

    @Test
    void shouldDeleteACar(){
        Long id = 1L;
        var car = new CarEntity("Sedan", 10, 2027);

        when(repository.findById(any())).thenReturn(Optional.of(car));

        service.delete(id);

        Mockito
                .verify(repository, Mockito.times(1))
                .delete(car);
    }

    @Test
    void shouldFindACarById(){
        Long id = 1L;
        var car = new CarEntity("Sedan", 10, 2027);
        when(repository.findById(any())).thenReturn(Optional.of(car));

        var carFound = service.findById(id);

        assertThat(carFound.getModel()).isEqualTo("Sedan");
        assertThat(carFound.getDailyValue()).isEqualTo(10);
        assertThat(carFound.getYear()).isEqualTo(2027);
    }

    @Test
    void shouldFindAll(){
        var car = new CarEntity(1L,"Sedan", 10, 2027);
        var car2 = new CarEntity(2L,"Hatch", 10, 2027);

        var list = List.of(car, car2);
        when(repository.findAll()).thenReturn(list);

        List<CarEntity> result = service.findAll();

        assertThat(result).hasSize(2);
        verify(repository, times(1)).findAll();
        verifyNoMoreInteractions(repository);
    }

}