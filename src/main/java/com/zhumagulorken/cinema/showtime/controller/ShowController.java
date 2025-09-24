package com.zhumagulorken.cinema.showtime.controller;

import com.zhumagulorken.cinema.showtime.entity.Show;
import com.zhumagulorken.cinema.showtime.service.ShowService;
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
    public List<Show> getShowsByMovie(@PathVariable Long movieId) {
        return showService.getShowsByMovie(movieId);
    }

    @GetMapping("/{id}")
    public Show getShowByIdAndMovie(@PathVariable Long movieId, @PathVariable Long id) {
        return showService.getShowByIdAndMovie(movieId, id);
    }

    @PostMapping
    public Show createShow(@PathVariable Long movieId, @RequestBody Show show) {
        return showService.createShow(movieId, show);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long movieId, @PathVariable Long id) {
        showService.deleteShow(movieId, id);
    }
}
