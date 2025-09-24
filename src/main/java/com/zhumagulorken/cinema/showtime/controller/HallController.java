package com.zhumagulorken.cinema.showtime.controller;

import com.zhumagulorken.cinema.showtime.entity.Hall;
import com.zhumagulorken.cinema.showtime.service.HallService;
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
    public List<Hall> getHallsByTheater(@PathVariable Long theaterId) {
        return hallService.getHallsByTheater(theaterId);
    }

    @GetMapping("/{id}")
    public Hall getHallByIdAndTheater(@PathVariable Long theaterId, @PathVariable Long id) {
        return hallService.getHallByIdAndTheater(theaterId, id);
    }

    @PostMapping
    public Hall createHall(@PathVariable Long theaterId, @RequestBody Hall hall) {
        return hallService.createHall(theaterId, hall);
    }

    @DeleteMapping("/{id}")
    public void deleteHall(@PathVariable Long theaterId, @PathVariable Long id) {
        hallService.deleteHall(theaterId, id);
    }
}
