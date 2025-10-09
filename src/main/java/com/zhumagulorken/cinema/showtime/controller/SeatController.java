package com.zhumagulorken.cinema.showtime.controller;

import com.zhumagulorken.cinema.showtime.dto.SeatDto;
import com.zhumagulorken.cinema.showtime.entity.Seat;
import com.zhumagulorken.cinema.showtime.service.SeatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/theaters/{theaterId}/halls/{hallId}/seats")
@Tag(
        name = "Seats",
        description = "Operations related to seat management inside halls"
)
public class SeatController {
    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @GetMapping
    @Operation(
            summary = "Get all seats in a hall",
            description = "Retrieve a list of all seats available in a specific hall."
    )
    public ResponseEntity<List<SeatDto>> getSeatsByHall(
            @Parameter(description = "Hall ID", example = "1") @PathVariable Long hallId) {
        return ResponseEntity.ok(seatService.getSeatsByHall(hallId));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get seat by ID",
            description = "Retrieve detailed information about a specific seat in a hall."
    )
    public ResponseEntity<SeatDto> getSeatByIdAndHall(
            @Parameter(description = "Hall ID", example = "1") @PathVariable Long hallId,
            @Parameter(description = "Seat ID", example = "10") @PathVariable Long id) {
        return ResponseEntity.ok(seatService.getSeatByIdAndHall(hallId, id));
    }

    @PostMapping
    @Operation(
            summary = "Create a new seat",
            description = "Add a new seat to the specified hall."
    )
    public ResponseEntity<SeatDto> createSeat(
            @Parameter(description = "Hall ID", example = "1") @PathVariable Long hallId,
            @Valid @RequestBody SeatDto seatDto) {
        return ResponseEntity.ok(seatService.createSeat(hallId, seatDto));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a seat",
            description = "Delete a specific seat from the hall by its ID."
    )
    public ResponseEntity<Void> deleteSeat(
            @Parameter(description = "Hall ID", example = "1") @PathVariable Long hallId,
            @Parameter(description = "Seat ID", example = "10") @PathVariable Long id) {
        seatService.deleteSeat(hallId, id);
        return ResponseEntity.ok().build();
    }
}
