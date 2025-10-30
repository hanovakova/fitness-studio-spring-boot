package com.fitnessstudiospringboot.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("spinning")
@Schema(name = "SpinningClass", description = "A fitness class specializing in indoor cycling (spinning).")
public class SpinningClass extends FitnessClass {

    @Schema(description = "The type of stationary bike used in the class.", example = "Keiser M3i")
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