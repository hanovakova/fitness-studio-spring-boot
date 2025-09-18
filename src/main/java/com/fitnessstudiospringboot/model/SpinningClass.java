package com.fitnessstudiospringboot.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("spinning")
public class SpinningClass extends FitnessClass {
    private String bikeType;

    public SpinningClass() {}

    public SpinningClass(String bikeType) {
        this.bikeType = bikeType;
    }

    public String getBikeType() {
        return bikeType;
    }

    public void setBikeType(String bikeType) {
        this.bikeType = bikeType;
    }
}
