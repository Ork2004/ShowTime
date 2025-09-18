package com.zhumagulorken.cinema.showtime.service;

import com.zhumagulorken.cinema.showtime.entity.Seat;
import com.zhumagulorken.cinema.showtime.entity.Show;
import com.zhumagulorken.cinema.showtime.entity.Ticket;
import com.zhumagulorken.cinema.showtime.entity.User;
import com.zhumagulorken.cinema.showtime.repository.SeatRepository;
import com.zhumagulorken.cinema.showtime.repository.ShowRepository;
import com.zhumagulorken.cinema.showtime.repository.TicketRepository;
import com.zhumagulorken.cinema.showtime.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final ShowRepository showRepository;
    private final SeatRepository seatRepository;

    public TicketService(TicketRepository ticketRepository, UserRepository userRepository,
                         ShowRepository showRepository, SeatRepository seatRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.showRepository = showRepository;
        this.seatRepository = seatRepository;
    }

    public List<Ticket> getTicketsByUser(Long userId) {
        return ticketRepository.findByUserId(userId);
    }

    public Ticket getTicketByIdAndUser(Long userId, Long ticketId) {
        return ticketRepository.findByIdAndUserId(ticketId, userId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
    }

    public Ticket bookTicket(Long userId, Ticket ticket) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Show show = showRepository.findById(ticket.getShow().getId())
                .orElseThrow(() -> new RuntimeException("Show not found"));

        Seat seat = seatRepository.findById(ticket.getSeat().getId())
                .orElseThrow(() -> new RuntimeException("Seat not found"));

        ticketRepository.findByShowIdAndSeatId(show.getId(), seat.getId())
                .ifPresent(t -> { throw new RuntimeException("Seat already booked"); });

        ticket.setUser(user);
        ticket.setShow(show);
        ticket.setSeat(seat);
        ticket.setBookedAt(LocalDateTime.now());

        return ticketRepository.save(ticket);
    }

    public void cancelTicket(Long userId, Long ticketId) {
        Ticket ticket = getTicketByIdAndUser(userId, ticketId);
        ticketRepository.delete(ticket);
    }
}
