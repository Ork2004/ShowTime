package com.zhumagulorken.cinema.showtime.controller;

import com.zhumagulorken.cinema.showtime.dto.HallDto;
import com.zhumagulorken.cinema.showtime.entity.Hall;
import com.zhumagulorken.cinema.showtime.service.HallService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/theaters/{theaterId}/halls")
public class HallController {
    private final HallService hallService;

    public HallController(HallService hallService) {
        this.hallService = hallService;
    }

    @GetMapping
    public ResponseEntity<List<HallDto>> getHallsByTheater(@PathVariable Long theaterId) {
        return ResponseEntity.ok(hallService.getHallsByTheater(theaterId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HallDto> getHallByIdAndTheater(@PathVariable Long theaterId, @PathVariable Long id) {
        return ResponseEntity.ok(hallService.getHallByIdAndTheater(theaterId, id));
    }

    @PostMapping
    public ResponseEntity<HallDto> createHall(@PathVariable Long theaterId, @Valid @RequestBody HallDto hallDto) {
        return ResponseEntity.ok(hallService.createHall(theaterId, hallDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHall(@PathVariable Long theaterId, @PathVariable Long id) {
        hallService.deleteHall(theaterId, id);
        return ResponseEntity.noContent().build();
    }
}
