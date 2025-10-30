package com.fitnessstudiospringboot.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("yoga")
@Schema(name = "YogaClass", description = "A fitness class specializing in yoga.")
public class YogaClass extends FitnessClass {

    @Schema(description = "The difficulty level of the yoga class.", example = "Intermediate")
    private String yogaLevel;

    public YogaClass() {
    }

    public YogaClass(String yogaLevel) {
        this.yogaLevel = yogaLevel;
    }

    public String getYogaLevel() {
        return yogaLevel;
    }

    public void setYogaLevel(String yogaLevel) {
        this.yogaLevel = yogaLevel;
    }
}