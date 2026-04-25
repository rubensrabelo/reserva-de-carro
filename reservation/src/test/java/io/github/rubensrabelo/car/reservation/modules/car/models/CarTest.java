package io.github.rubensrabelo.car.reservation.modules.car.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CarTest {

    @Test
    @DisplayName("Deve calcular o valor correto do aluguel")
    void calculateRentalValueTest(){
        Car car = new Car("Sedan", 100.0);

        double total = car.calculateRentValue(3);

        Assertions.assertEquals(300.0, total);
    }

    @Test
    @DisplayName("Deve calcular o valor do aluguel com desconto")
    void mustCalculateRentalValueWithDiscountTest(){
        Car car = new Car("Sedan", 100.0);
        int daysQuantity = 6;

        double total = car.calculateRentValue(daysQuantity);

        Assertions.assertEquals(550.0, total);
    }
}
