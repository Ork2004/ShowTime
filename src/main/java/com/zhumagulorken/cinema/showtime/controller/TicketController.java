package com.zhumagulorken.cinema.showtime.controller;

import com.zhumagulorken.cinema.showtime.dto.TicketDto;
import com.zhumagulorken.cinema.showtime.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/tickets")
@Tag(
        name = "Tickets",
        description = "Operations related to booking, viewing, and canceling tickets"
)
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    @Operation(
            summary = "Get all tickets for a user",
            description = "Retrieve a list of all tickets booked by a specific user."
    )
    public ResponseEntity<List<TicketDto>> getTicketsByUser(
            @Parameter(description = "User ID", example = "1") @PathVariable Long userId) {
        return ResponseEntity.ok(ticketService.getTicketsByUser(userId));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get a ticket by ID",
            description = "Retrieve a specific ticket for a given user."
    )
    public ResponseEntity<TicketDto> getTicketByIdAndUser(
            @Parameter(description = "User ID", example = "1") @PathVariable Long userId,
            @Parameter(description = "Ticket ID", example = "10") @PathVariable Long id) {
        return ResponseEntity.ok(ticketService.getTicketByIdAndUser(userId, id));
    }

    @PostMapping
    @Operation(
            summary = "Book a new ticket",
            description = "Create a new ticket for a user to a specific show."
    )
    public ResponseEntity<TicketDto> bookTicket(
            @Parameter(description = "User ID", example = "1") @PathVariable Long userId,
            @Valid @RequestBody TicketDto ticketDto) {
        return ResponseEntity.ok(ticketService.bookTicket(userId, ticketDto));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Cancel a ticket",
            description = "Cancel an existing ticket for a given user."
    )
    public ResponseEntity<Void> cancelTicket(
            @Parameter(description = "User ID", example = "1") @PathVariable Long userId,
            @Parameter(description = "Ticket ID", example = "10") @PathVariable Long id) {
        ticketService.cancelTicket(userId, id);
        return ResponseEntity.noContent().build();
    }
}
