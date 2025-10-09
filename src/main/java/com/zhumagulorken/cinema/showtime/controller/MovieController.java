package com.zhumagulorken.cinema.showtime.controller;

import com.zhumagulorken.cinema.showtime.dto.MovieDto;
import com.zhumagulorken.cinema.showtime.entity.Movie;
import com.zhumagulorken.cinema.showtime.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
@Tag(
        name = "Movies",
        description = "Operations related to movie management"
)
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    @Operation(
            summary = "Get all movies",
            description = "Retrieve a list of all movies available in the cinema system."
    )
    public ResponseEntity<List<MovieDto>> getMovies() {
        return ResponseEntity.ok(movieService.getMovies());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get movie by ID",
            description = "Retrieve detailed information about a specific movie by its ID."
    )
    public ResponseEntity<MovieDto> getMovieById(
            @Parameter(description = "Movie ID", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(movieService.getMovieById(id));
    }

    @PostMapping
    @Operation(
            summary = "Create a new movie",
            description = "Add a new movie to the database."
    )
    public ResponseEntity<MovieDto> createMovie(
            @Valid @RequestBody MovieDto movieDto) {
        return ResponseEntity.ok(movieService.createMovie(movieDto));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a movie",
            description = "Delete a movie by its ID from the database."
    )
    public ResponseEntity<Void> deleteMovie(
            @Parameter(description = "Movie ID", example = "1") @PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }
}
