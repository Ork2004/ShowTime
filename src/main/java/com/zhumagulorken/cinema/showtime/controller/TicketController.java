package com.zhumagulorken.cinema.showtime.controller;

import com.zhumagulorken.cinema.showtime.dto.TicketDto;
import com.zhumagulorken.cinema.showtime.entity.Ticket;
import com.zhumagulorken.cinema.showtime.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/tickets")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public ResponseEntity<List<TicketDto>> getTicketsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(ticketService.getTicketsByUser(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketDto> getTicket(@PathVariable Long userId, @PathVariable Long id) {
        return ResponseEntity.ok(ticketService.getTicketByIdAndUser(userId, id));
    }

    @PostMapping
    public ResponseEntity<TicketDto> bookTicket(@PathVariable Long userId, @Valid @RequestBody TicketDto ticketDto) {
        return ResponseEntity.ok(ticketService.bookTicket(userId, ticketDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelTicket(@PathVariable Long userId, @PathVariable Long id) {
        ticketService.cancelTicket(userId, id);
        return ResponseEntity.noContent().build();
    }
}
