package io.github.rubensrabelo.car.reservation.modules.reservation.models;

import io.github.rubensrabelo.car.reservation.modules.car.models.Car;
import io.github.rubensrabelo.car.reservation.modules.client.models.Client;
import io.github.rubensrabelo.car.reservation.shared.exceptions.domain.InvalidReservationException;

public class Reservation {
  
    private int days;

    private Client client;
    private Car car;
    
    public Reservation() {
    }

    public Reservation(int days, Client client, Car car) {
        if(days < 1)
            throw new InvalidReservationException("The reservation cannot have fewer than 1 day.");

        this.days = days;
        this.client = client;
        this.car = car;
    }

    public double calculateTotal() {
        return this.car.calculateRentValue(this.days);
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    
}
