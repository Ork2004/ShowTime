package com.zhumagulorken.cinema.showtime.controller;

import com.zhumagulorken.cinema.showtime.dto.HallDto;
import com.zhumagulorken.cinema.showtime.entity.Hall;
import com.zhumagulorken.cinema.showtime.service.HallService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/theaters/{theaterId}/halls")
@Tag(
        name = "Halls",
        description = "Operation related to halls within theaters"
)
public class HallController {
    private final HallService hallService;

    public HallController(HallService hallService) {
        this.hallService = hallService;
    }

    @GetMapping
    @Operation(
            summary = "Get all halls in a theater",
            description = "Retrieve a list of all halls belonging to a specific theater."
    )
    public ResponseEntity<List<HallDto>> getHallsByTheater(
            @Parameter(description = "Theater ID", example = "1") @PathVariable Long theaterId) {
        return ResponseEntity.ok(hallService.getHallsByTheater(theaterId));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get hall by ID",
            description = "Retrieve details of a specific hall within a theater by its ID."
    )
    public ResponseEntity<HallDto> getHallByIdAndTheater(
            @Parameter(description = "Theater ID", example = "1") @PathVariable Long theaterId,
            @Parameter(description = "Hall ID", example = "10") @PathVariable Long id) {
        return ResponseEntity.ok(hallService.getHallByIdAndTheater(theaterId, id));
    }

    @PostMapping
    @Operation(
            summary = "Create a new hall",
            description = "Add a new hall to the specified theater."
    )
    public ResponseEntity<HallDto> createHall(
            @Parameter(description = "Theater ID", example = "1") @PathVariable Long theaterId,
            @Valid @RequestBody HallDto hallDto) {
        return ResponseEntity.ok(hallService.createHall(theaterId, hallDto));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update an existing hall",
            description = "Update hall details (name) by ID within a theater."
    )
    public ResponseEntity<HallDto> updateHall(
            @Parameter(description = "Theater ID", example = "1") @PathVariable Long theaterId,
            @Parameter(description = "Hall ID", example = "10") @PathVariable Long id,
            @Valid @RequestBody HallDto hallDto) {
        return ResponseEntity.ok(hallService.updateHall(theaterId, id, hallDto));
    }


    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a hall",
            description = "Remove a specific hall from a theater by its ID."
    )
    public ResponseEntity<Void> deleteHall(
            @Parameter(description = "Theater ID", example = "1") @PathVariable Long theaterId,
            @Parameter(description = "Hall ID", example = "10") @PathVariable Long id) {
        hallService.deleteHall(theaterId, id);
        return ResponseEntity.noContent().build();
    }
}
