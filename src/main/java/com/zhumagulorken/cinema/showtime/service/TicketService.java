package com.zhumagulorken.cinema.showtime.service;

import com.zhumagulorken.cinema.showtime.dto.TicketDto;
import com.zhumagulorken.cinema.showtime.entity.Seat;
import com.zhumagulorken.cinema.showtime.entity.Show;
import com.zhumagulorken.cinema.showtime.entity.Ticket;
import com.zhumagulorken.cinema.showtime.entity.User;
import com.zhumagulorken.cinema.showtime.exсeption.NotFoundException;
import com.zhumagulorken.cinema.showtime.exсeption.SeatAlreadyBookedException;
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
                .orElseThrow(() -> new NotFoundException(Ticket.class, ticketId));
        return mapToDto(ticket);
    }

    public TicketDto bookTicket(Long userId, TicketDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(User.class, userId));

        Show show = showRepository.findById(dto.getShowId())
                .orElseThrow(() -> new NotFoundException(Show.class, dto.getShowId()));

        Seat seat = seatRepository.findById(dto.getSeatId())
                .orElseThrow(() -> new NotFoundException(Seat.class, dto.getSeatId()));

        ticketRepository.findByShowIdAndSeatId(show.getId(), seat.getId())
                .ifPresent(t -> { throw new SeatAlreadyBookedException(show.getId(), seat.getId()); });

        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setShow(show);
        ticket.setSeat(seat);
        ticket.setBookedAt(LocalDateTime.now());

        return mapToDto(ticketRepository.save(ticket));
    }

    public void cancelTicket(Long userId, Long ticketId) {
        Ticket ticket = ticketRepository.findByIdAndUserId(ticketId, userId)
                .orElseThrow(() -> new NotFoundException(Ticket.class, ticketId));
        ticketRepository.delete(ticket);
    }

    private TicketDto mapToDto(Ticket ticket) {
        TicketDto dto = new TicketDto();
        dto.setId(ticket.getId());
        dto.setShowId(ticket.getShow().getId());
        dto.setSeatId(ticket.getSeat().getId());
        dto.setBookedAt(ticket.getBookedAt());

        dto.setMovieTitle(ticket.getShow().getMovie().getTitle());
        dto.setTheaterName(ticket.getShow().getHall().getTheater().getName());
        dto.setHallName(ticket.getShow().getHall().getName());
        dto.setSeatNumber(ticket.getSeat().getSeatNumber());
        dto.setShowTime(ticket.getShow().getShowTime());
        dto.setPrice(ticket.getShow().getPrice());
        return dto;
    }
}
