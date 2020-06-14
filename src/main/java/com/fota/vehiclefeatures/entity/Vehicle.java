package com.fota.vehiclefeatures.entity;

public class Vehicle {
    private String vin;

    public Vehicle(String vin) {
        this.vin = vin;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }
}
