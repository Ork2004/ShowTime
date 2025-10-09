package com.zhumagulorken.cinema.showtime.controller;

import com.zhumagulorken.cinema.showtime.dto.GenreDto;
import com.zhumagulorken.cinema.showtime.entity.Genre;
import com.zhumagulorken.cinema.showtime.service.GenreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genres")
@Tag(
        name = "Genres",
        description = "Operations related to genre management"
)
public class GenreController {
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    @Operation(
            summary = "Get all genres",
            description = "Retrieve a list of all available movie genres."
    )
    public ResponseEntity<List<GenreDto>> getGenres() {
        return ResponseEntity.ok(genreService.getGenres());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get genre by ID",
            description = "Retrieve detailed information about a specific genre by its ID."
    )
    public ResponseEntity<GenreDto> getGenreById(
            @Parameter(description = "Genre ID", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(genreService.getGenreById(id));
    }

    @PostMapping
    @Operation(
            summary = "Create a new genre",
            description = "Add a new movie genre to the database."
    )
    public ResponseEntity<GenreDto> createGenre(
            @Valid @RequestBody GenreDto genreDto) {
        return ResponseEntity.ok(genreService.createGenre(genreDto));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a genre",
            description = "Delete a genre by its ID from the database."
    )
    public ResponseEntity<Void> deleteGenre(
            @Parameter(description = "Genre ID", example = "1") @PathVariable Long id) {
        genreService.deleteGenre(id);
        return ResponseEntity.noContent().build();
    }
}
