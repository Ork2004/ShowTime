package com.zhumagulorken.cinema.showtime.controller;

import com.zhumagulorken.cinema.showtime.entity.Seat;
import com.zhumagulorken.cinema.showtime.service.SeatService;
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
    public List<Seat> getSeatsByHall(@PathVariable Long hallId) {
        return seatService.getSeatsByHall(hallId);
    }

    @GetMapping("/{id}")
    public Seat getSeatByIdAndHall(@PathVariable Long hallId, @PathVariable Long id) {
        return seatService.getSeatByIdAndHall(hallId, id);
    }

    @PostMapping
    public Seat createSeat(@PathVariable Long hallId, @RequestBody Seat seat) {
        return seatService.createSeat(hallId, seat);
    }

    @DeleteMapping("/{id}")
    public void deleteSeat(@PathVariable Long hallId, @PathVariable Long id) {
        seatService.deleteSeat(hallId, id);
    }
}
