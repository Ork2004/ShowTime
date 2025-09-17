package com.zhumagulorken.cinema.showtime.controller;

import com.zhumagulorken.cinema.showtime.entity.Hall;
import com.zhumagulorken.cinema.showtime.entity.Movie;
import com.zhumagulorken.cinema.showtime.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }

    @GetMapping
    public List<Movie> getAll() {
        return service.getAll();
    }

    @PostMapping
    public Movie create(@RequestBody Movie movie) {
        return service.create(movie);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
