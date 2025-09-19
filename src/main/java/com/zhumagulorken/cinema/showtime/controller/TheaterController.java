package com.zhumagulorken.cinema.showtime.controller;

import com.zhumagulorken.cinema.showtime.entity.Theater;
import com.zhumagulorken.cinema.showtime.service.TheaterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/theaters")
public class TheaterController {
    private final TheaterService theaterService;

    public TheaterController(TheaterService service) {
        this.theaterService = service;
    }

    @GetMapping
    public List<Theater> getAll() {
        return theaterService.getAll();
    }

    @PostMapping
    public Theater create(@RequestBody Theater theater) {
        return theaterService.create(theater);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Theater> getById(@PathVariable Long id) {
        return theaterService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        theaterService.delete(id);
    }
}
