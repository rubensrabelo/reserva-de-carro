package io.github.rubensrabelo.car.reservation.shared.exceptions.domain;

public class InvalidReservationException extends RuntimeException {
    
    public InvalidReservationException(String message) {
        super(message);
    }
}
