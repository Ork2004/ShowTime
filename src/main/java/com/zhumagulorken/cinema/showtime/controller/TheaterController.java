package com.zhumagulorken.cinema.showtime.controller;

import com.zhumagulorken.cinema.showtime.dto.TheaterDto;
import com.zhumagulorken.cinema.showtime.entity.Theater;
import com.zhumagulorken.cinema.showtime.service.TheaterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/theaters")
@Tag(
        name = "Theaters",
        description = "Operations related to theater management"
)
public class TheaterController {
    private final TheaterService theaterService;

    public TheaterController(TheaterService theaterService) {
        this.theaterService = theaterService;
    }

    @GetMapping
    @Operation(
            summary = "Get all theaters",
            description = "Retrieve a list of all theaters."
    )
    public ResponseEntity<List<TheaterDto>> getTheaters() {
        return ResponseEntity.ok(theaterService.getTheaters());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get theater by ID",
            description = "Retrieve details of a specific theater by its ID."
    )
    public ResponseEntity<TheaterDto> getTheaterById(
            @Parameter(description = "Theater ID", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(theaterService.getTheaterById(id));
    }

    @PostMapping
    @Operation(
            summary = "Create a new theater",
            description = "Add a new theater to the system."
    )
    public ResponseEntity<TheaterDto> createTheater(@Valid @RequestBody TheaterDto theaterDto) {
        return ResponseEntity.ok(theaterService.createTheater(theaterDto));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update an existing theater",
            description = "Update theater details (name, location) by ID."
    )
    public ResponseEntity<TheaterDto> updateTheater(
            @Parameter(description = "Theater ID", example = "1") @PathVariable Long id,
            @Valid @RequestBody TheaterDto theaterDto) {
        return ResponseEntity.ok(theaterService.updateTheater(id, theaterDto));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a theater",
            description = "Remove a theater from the system by its ID."
    )
    public ResponseEntity<Void> deleteTheater(
            @Parameter(description = "Theater ID", example = "1") @PathVariable Long id) {
        theaterService.deleteTheater(id);
        return ResponseEntity.noContent().build();
    }
}
