package com.zhumagulorken.cinema.showtime.controller;

import com.zhumagulorken.cinema.showtime.dto.ShowDto;
import com.zhumagulorken.cinema.showtime.entity.Show;
import com.zhumagulorken.cinema.showtime.service.ShowService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies/{movieId}/shows")
public class ShowController {
    private final ShowService showService;

    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @GetMapping
    public ResponseEntity<List<ShowDto>> getShowsByMovie(@PathVariable Long movieId) {
        return ResponseEntity.ok(showService.getShowsByMovie(movieId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShowDto> getShowByIdAndMovie(@PathVariable Long movieId, @PathVariable Long id) {
        return ResponseEntity.ok(showService.getShowByIdAndMovie(movieId, id));
    }

    @PostMapping
    public ResponseEntity<ShowDto> createShow(@PathVariable Long movieId, @Valid @RequestBody ShowDto showDto) {
        return ResponseEntity.ok(showService.createShow(movieId, showDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShow(@PathVariable Long movieId, @PathVariable Long id) {
        showService.deleteShow(movieId, id);
        return ResponseEntity.noContent().build();
    }
}
