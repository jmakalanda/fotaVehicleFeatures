package com.fota.vehiclefeatures.entity;

public class VinFeature {
    String vin;
    String feature;
    String isCompatible;

    public VinFeature(String vin, String feature, String isCompatible) {
        this.vin = vin;
        this.feature = feature;
        this.isCompatible = isCompatible;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getIsCompatible() {
        return isCompatible;
    }

    public void setIsCompatible(String isCompatible) {
        this.isCompatible = isCompatible;
    }
}
