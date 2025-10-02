package com.fitnessstudiospringboot.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("yoga")
public class YogaClass extends FitnessClass {
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
