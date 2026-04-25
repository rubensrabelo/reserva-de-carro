package io.github.rubensrabelo.car.reservation.modules.car.models;

public class Car {
    private String model;
    private double dailyValue;
    
    public Car() {
    }

    public Car(String model, double dailyValue) {
        this.model = model;
        this.dailyValue = dailyValue;
    }

    public double calculateRentValue(int days) {
        double total = days * this.dailyValue;
        if(days >= 5){
            total -= 50.0;
        }
        return total;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getDailyValue() {
        return dailyValue;
    }

    public void setDailyValue(double dailyValue) {
        this.dailyValue = dailyValue;
    }
}
