package com.fitnessstudiospringboot.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name="fitness_classes")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "class_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("fitness")
@Schema(
        name = "FitnessClass",
        description = "Represents a generic fitness class",
        discriminatorProperty = "classType",
        subTypes = {YogaClass.class, SpinningClass.class}
)
public class FitnessClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "The auto-generated unique identifier for the class.", accessMode = Schema.AccessMode.READ_ONLY, example = "1")
    private Integer id;

    @Schema(description = "The name of the fitness class.", example = "Morning HIIT")
    private String name;

    @Schema(description = "A detailed description of the class.", example = "A high-intensity interval training session to start your day.")
    private String description;

    @Schema(description = "The date and time the class starts.", example = "2025-10-28T09:00:00.000+00:00")
    private Timestamp startTime;

    @Schema(description = "The date and time the class ends.", example = "2025-10-28T10:00:00.000+00:00")
    private Timestamp endTime;

    @Schema(description = "The name of the instructor teaching the class.", example = "Alex Johnson")
    private String instructorName;

    @Schema(description = "The price to attend the class.", example = "25.50")
    private Float price;

    @Schema(description = "The maximum number of participants for the class.", example = "20")
    private Integer capacity;

    @Schema(description = "The URL path to an image for the class.", example = "/images/hiit.jpg")
    private String imagePath;

    public FitnessClass() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Schema(description = "The type of class. This property is used by OpenAPI to distinguish between class types.",
            example = "FitnessClass", accessMode = Schema.AccessMode.READ_ONLY)
    public String getClassType() {
        return this.getClass().getSimpleName();
    }
}