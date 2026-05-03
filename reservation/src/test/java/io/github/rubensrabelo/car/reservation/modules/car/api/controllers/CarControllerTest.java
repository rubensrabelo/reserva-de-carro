package io.github.rubensrabelo.car.reservation.modules.car.api.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import io.github.rubensrabelo.car.reservation.modules.car.application.services.CarService;
import io.github.rubensrabelo.car.reservation.modules.car.entity.CarEntity;
import jakarta.persistence.EntityNotFoundException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

@WebMvcTest(CarController.class)
public class CarControllerTest {
    
    @Autowired
    MockMvc mvc;

    @MockitoBean
    CarService carService;

    @Test
    void shouldSaveACar() throws Exception {
        CarEntity car = new CarEntity(
                1L, "Honda Civic", 150, 2027);

        when(carService.save(any())).thenReturn(car);

        String json = """
                {
                    "modelo": "Honda Civic",
                    "valorDiaria": 150,
                    "ano": 2027
                }
                """;

        ResultActions result = mvc.perform(
                post("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)

        );

        result
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.model").value("Honda Civic"));

    }

    @Test
    void shouldGetDetailsCar() throws Exception {
        when(carService.findById(any())).thenReturn(new CarEntity(
                1L, "Civic", 250, 2028
        ));


        mvc.perform(
            MockMvcRequestBuilders.get("/cars/1")
        ).andExpect(status().isOk())
         .andExpect(jsonPath("$.id").value(1))
         .andExpect(jsonPath("$.model").value("Civic"))
         .andExpect(jsonPath("$.dailyValue").value(250))
         .andExpect(jsonPath("$.year").value(2028));
    }

    @Test
    void shouldNotFoundDetailsCarWhenCarNotFound() throws Exception {
        when(carService.findById(any())).thenThrow(EntityNotFoundException.class);

        mvc.perform(
                MockMvcRequestBuilders.get("/cars/1")
        ).andExpect(status().isNotFound());

    }

    @Test
    void shouldFindAllCars() throws Exception {
        var listagem = List.of(
                new CarEntity(1L, "Argo", 150, 2025),
                new CarEntity(2L, "Celta", 80, 2015)
        );

        when(carService.findAll()).thenReturn(listagem);

        mvc.perform(
                MockMvcRequestBuilders.get("/cars")
        ).andExpect(status().isOk())
         .andExpect(jsonPath("$[0].model").value("Argo"))
         .andExpect(jsonPath("$[1].model").value("Celta"));
    }

    @Test
    void shouldUpdateACar() throws Exception {
        when(carService.update(any(), any()))
                .thenReturn(new CarEntity(1L, "Celta", 100, 2025));

        String json = """
                {
                    "modelo": "Celta",
                    "valorDiaria": 100,
                    "ano": 2025
                }
        """;

        mvc.perform(
                MockMvcRequestBuilders.put("/cars/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isNoContent());
    }

    @Test
    void shouldNotUpdateACarWhenNotFound() throws Exception {
        when(carService.update(any(), any()))
                .thenThrow(EntityNotFoundException.class);

        String json = """
                {
                    "modelo": "Celta",
                    "valorDiaria": 100,
                    "ano": 2025
                }
        """;

        mvc.perform(
                MockMvcRequestBuilders.put("/cars/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isNotFound());

    }

    @Test
    void shouldDeleteACar() throws Exception {
        doNothing().when(carService).delete(any());

        mvc.perform(MockMvcRequestBuilders.delete("/cars/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldNotDeleteACarWhenNotFound() throws Exception {
        doThrow(EntityNotFoundException.class).when(carService).delete(any());

        mvc.perform(MockMvcRequestBuilders.delete("/cars/1"))
                .andExpect(status().isNotFound());
    }

}
