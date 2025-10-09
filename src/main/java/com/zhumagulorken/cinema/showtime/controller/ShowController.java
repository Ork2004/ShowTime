package com.zhumagulorken.cinema.showtime.controller;

import com.zhumagulorken.cinema.showtime.dto.ShowDto;
import com.zhumagulorken.cinema.showtime.entity.Show;
import com.zhumagulorken.cinema.showtime.service.ShowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies/{movieId}/shows")
@Tag(
        name = "Shows",
        description = "Operations related to movie showtimes"
)
public class ShowController {
    private final ShowService showService;

    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @GetMapping
    @Operation(
            summary = "Get all shows for a movie",
            description = "Retrieve a list of all showtimes associated with a specific movie."
    )
    public ResponseEntity<List<ShowDto>> getShowsByMovie(
            @Parameter(description = "Movie ID", example = "1") @PathVariable Long movieId) {
        return ResponseEntity.ok(showService.getShowsByMovie(movieId));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get show by ID",
            description = "Retrieve details of a specific showtime for a given movie."
    )
    public ResponseEntity<ShowDto> getShowByIdAndMovie(
            @Parameter(description = "Movie ID", example = "1") @PathVariable Long movieId,
            @Parameter(description = "Show ID", example = "10") @PathVariable Long id) {
        return ResponseEntity.ok(showService.getShowByIdAndMovie(movieId, id));
    }

    @PostMapping
    @Operation(
            summary = "Create a new show",
            description = "Add a new showtime entry for a specific movie."
    )
    public ResponseEntity<ShowDto> createShow(
            @Parameter(description = "Movie ID", example = "1") @PathVariable Long movieId,
            @Valid @RequestBody ShowDto showDto) {
        return ResponseEntity.ok(showService.createShow(movieId, showDto));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a show",
            description = "Delete a specific showtime for a given movie."
    )
    public ResponseEntity<Void> deleteShow(
            @Parameter(description = "Movie ID", example = "1") @PathVariable Long movieId,
            @Parameter(description = "Show ID", example = "10") @PathVariable Long id) {
        showService.deleteShow(movieId, id);
        return ResponseEntity.noContent().build();
    }
}
