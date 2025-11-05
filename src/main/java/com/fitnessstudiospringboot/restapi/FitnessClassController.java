package com.fitnessstudiospringboot.restapi;

import com.fitnessstudiospringboot.model.FitnessClass;
import com.fitnessstudiospringboot.service.FitnessClassService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fitnessClasses")
@Tag(name = "Fitness Class Management", description = "Endpoints for creating, retrieving, updating, and deleting fitness classes.")
public class FitnessClassController {

    private final FitnessClassService fitnessClassService;

    public FitnessClassController(FitnessClassService fitnessClassService) {
        this.fitnessClassService = fitnessClassService;
    }

    @Operation(summary = "Get all fitness classes", description = "Retrieves a list of all fitness classes, with optional filtering by class time and type.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of classes",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FitnessClass.class)))
    })
    @GetMapping
    public List<FitnessClass> showClasses(
            @Parameter(description = "Filter by class time (e.g., 'morning', 'afternoon')", example = "morning")
            @RequestParam(required = false) String classTime,
            @Parameter(description = "Filter by class type (e.g., 'yoga', 'spin')", example = "yoga")
            @RequestParam(required = false) String classType) {

        List<FitnessClass> classes = fitnessClassService.getClasses();
        if (classTime != null && !classTime.isBlank()) {
            classes = fitnessClassService.getClassesByTimeFrame(classTime.toLowerCase(), classes);
        }

        if (classType != null && !classType.isBlank()) {
            classes = fitnessClassService.getClassesByClassType(classType.toLowerCase(), classes);
        }
        return classes;
    }

    @Operation(summary = "Get a fitness class by ID", description = "Retrieves a single fitness class using its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the class",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FitnessClass.class))),
            @ApiResponse(responseCode = "404", description = "Class not found with the given ID",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<FitnessClass> getClassById(
            @Parameter(description = "Identifier of the class to be retrieved", required = true, example = "1")
            @PathVariable int id) {
        FitnessClass fitnessClass = fitnessClassService.getClassById(id);
        return ResponseEntity.ok(fitnessClass);
    }

    @Operation(summary = "Add a new fitness class", description = "Creates a new fitness class and returns its generated ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Class successfully created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Integer.class, example = "10"))),
            @ApiResponse(responseCode = "400", description = "Invalid class data provided",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<Integer> addClass(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The fitness class object to be created. ID will be auto-generated.", required = true,
                    content = @Content(schema = @Schema(implementation = FitnessClass.class)))
            @RequestBody FitnessClass fitnessClass) {
        Integer id = fitnessClassService.add(fitnessClass);
        return ResponseEntity.ok(id);
    }

    @Operation(summary = "Update a fitness class", description = "Updates all fields of an existing fitness class by its ID. This is a full replacement (PUT).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Class successfully updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FitnessClass.class))),
            @ApiResponse(responseCode = "404", description = "Class not found with the given ID",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid class data provided",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<FitnessClass> updateClass(
            @Parameter(description = "Identifier of the class to be updated", required = true, example = "1")
            @PathVariable int id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The updated fitness class object. All fields will be replaced.", required = true,
                    content = @Content(schema = @Schema(implementation = FitnessClass.class)))
            @RequestBody FitnessClass fitnessClassDetails) {
        FitnessClass updatedClass = fitnessClassService.updateClass(id, fitnessClassDetails);
        return ResponseEntity.ok(updatedClass);
    }

    @Operation(summary = "Partially update a fitness class", description = "Partially updates an existing fitness class by its ID. Only provided (non-null) fields in the request will be updated.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Class successfully patched",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FitnessClass.class))),
            @ApiResponse(responseCode = "404", description = "Class not found with the given ID",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid class data provided",
                    content = @Content)
    })
    @PatchMapping("/{id}")
    public ResponseEntity<FitnessClass> partialUpdateClass(
            @Parameter(description = "Identifier of the class to be partially updated", required = true, example = "1")
            @PathVariable int id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "A fitness class object containing only the fields to be updated.", required = true,
                    content = @Content(schema = @Schema(implementation = FitnessClass.class)))
            @RequestBody FitnessClass fitnessClassDetails) {
        FitnessClass updatedClass = fitnessClassService.partialUpdateClass(id, fitnessClassDetails);
        return ResponseEntity.ok(updatedClass);
    }


    @Operation(summary = "Delete a fitness class by ID", description = "Deletes a fitness class using its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Class successfully deleted",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Class not found with the given ID",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClass(
            @Parameter(description = "Identifier of the class to be deleted", required = true, example = "1")
            @PathVariable int id) {
        fitnessClassService.deleteClass(id);
        return ResponseEntity.noContent().build();
    }
}