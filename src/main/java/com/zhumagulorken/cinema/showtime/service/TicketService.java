package com.zhumagulorken.cinema.showtime.service;

import com.zhumagulorken.cinema.showtime.dto.TicketDto;
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

    public List<TicketDto> getTicketsByUser(Long userId) {
        return ticketRepository.findByUserId(userId)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public TicketDto getTicketByIdAndUser(Long userId, Long ticketId) {
        Ticket ticket = ticketRepository.findByIdAndUserId(ticketId, userId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        return mapToDto(ticket);
    }

    public TicketDto bookTicket(Long userId, TicketDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Show show = showRepository.findById(dto.getShowId())
                .orElseThrow(() -> new RuntimeException("Show not found"));

        Seat seat = seatRepository.findById(dto.getSeatId())
                .orElseThrow(() -> new RuntimeException("Seat not found"));

        ticketRepository.findByShowIdAndSeatId(show.getId(), seat.getId())
                .ifPresent(t -> { throw new RuntimeException("Seat already booked"); });

        ticketRepository.findByShowIdAndSeatId(show.getId(), seat.getId())
                .ifPresent(t -> { throw new RuntimeException("Seat already booked"); });

        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setShow(show);
        ticket.setSeat(seat);
        ticket.setBookedAt(LocalDateTime.now());

        return mapToDto(ticketRepository.save(ticket));
    }

    public void cancelTicket(Long userId, Long ticketId) {
        Ticket ticket = ticketRepository.findByIdAndUserId(ticketId, userId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        ticketRepository.delete(ticket);
    }

    private TicketDto mapToDto(Ticket ticket) {
        TicketDto dto = new TicketDto();
        dto.setId(ticket.getId());
        dto.setShowId(ticket.getShow().getId());
        dto.setSeatId(ticket.getSeat().getId());
        dto.setBookedAt(ticket.getBookedAt());
        return dto;
    }
}
