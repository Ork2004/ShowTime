package com.zhumagulorken.cinema.showtime.controller;

import com.zhumagulorken.cinema.showtime.entity.Ticket;
import com.zhumagulorken.cinema.showtime.service.TicketService;
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
    public List<Ticket> getTicketsByUser(@PathVariable Long userId) {
        return ticketService.getTicketsByUser(userId);
    }

    @GetMapping("/{id}")
    public Ticket getTicket(@PathVariable Long userId, @PathVariable Long id) {
        return ticketService.getTicketByIdAndUser(userId, id);
    }

    @PostMapping
    public Ticket bookTicket(@PathVariable Long userId, @RequestBody Ticket ticket) {
        return ticketService.bookTicket(userId, ticket);
    }

    @DeleteMapping("/{id}")
    public void cancelTicket(@PathVariable Long userId, @PathVariable Long id) {
        ticketService.cancelTicket(userId, id);
    }
}
