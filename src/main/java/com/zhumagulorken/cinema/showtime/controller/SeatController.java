package com.zhumagulorken.cinema.showtime.controller;

import com.zhumagulorken.cinema.showtime.dto.SeatDto;
import com.zhumagulorken.cinema.showtime.entity.Seat;
import com.zhumagulorken.cinema.showtime.service.SeatService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/theaters/{theaterId}/halls/{hallId}/seats")
public class SeatController {
    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @GetMapping
    public ResponseEntity<List<SeatDto>> getSeatsByHall(@PathVariable Long hallId) {
        return ResponseEntity.ok(seatService.getSeatsByHall(hallId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeatDto> getSeatByIdAndHall(@PathVariable Long hallId, @PathVariable Long id) {
        return ResponseEntity.ok(seatService.getSeatByIdAndHall(hallId, id));
    }

    @PostMapping
    public ResponseEntity<SeatDto> createSeat(@PathVariable Long hallId, @Valid @RequestBody SeatDto seatDto) {
        return ResponseEntity.ok(seatService.createSeat(hallId, seatDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeat(@PathVariable Long hallId, @PathVariable Long id) {
        seatService.deleteSeat(hallId, id);
        return ResponseEntity.ok().build();
    }
}
