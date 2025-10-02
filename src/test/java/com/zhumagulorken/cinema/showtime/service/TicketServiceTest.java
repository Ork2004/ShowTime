package com.zhumagulorken.cinema.showtime.service;

import com.zhumagulorken.cinema.showtime.dto.TicketDto;
import com.zhumagulorken.cinema.showtime.entity.*;
import com.zhumagulorken.cinema.showtime.exсeption.NotFoundException;
import com.zhumagulorken.cinema.showtime.exсeption.SeatAlreadyBookedException;
import com.zhumagulorken.cinema.showtime.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ShowRepository showRepository;

    @Mock
    private SeatRepository seatRepository;

    @InjectMocks
    private TicketService ticketService;

    @Test
    void testGetTicketsByUser() {
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setBookedAt(LocalDateTime.now());

        User user = new User();
        user.setId(10L);
        ticket.setUser(user);

        Show show = new Show();
        show.setId(20L);
        ticket.setShow(show);

        Seat seat = new Seat();
        seat.setId(30L);
        ticket.setSeat(seat);

        when(ticketRepository.findByUserId(10L)).thenReturn(List.of(ticket));

        List<TicketDto> result = ticketService.getTicketsByUser(10L);

        assertEquals(1, result.size());
        assertEquals(20L, result.get(0).getShowId());
        assertEquals(30L, result.get(0).getSeatId());
    }

    @Test
    void testGetTicketByIdAndUser_found() {
        Ticket ticket = new Ticket();
        ticket.setId(2L);

        Show show = new Show();
        show.setId(5L);
        ticket.setShow(show);

        Seat seat = new Seat();
        seat.setId(6L);
        ticket.setSeat(seat);

        ticket.setUser(new User());

        when(ticketRepository.findByIdAndUserId(2L, 1L)).thenReturn(Optional.of(ticket));

        TicketDto result = ticketService.getTicketByIdAndUser(1L, 2L);

        assertEquals(2L, result.getId());
        assertEquals(5L, result.getShowId());
    }

    @Test
    void testGetTicketByIdAndUser_notFound() {
        when(ticketRepository.findByIdAndUserId(99L, 1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> ticketService.getTicketByIdAndUser(1L, 99L));
    }

    @Test
    void testBookTicket_success() {
        User user = new User();
        user.setId(1L);

        Show show = new Show();
        show.setId(2L);

        Seat seat = new Seat();
        seat.setId(3L);

        TicketDto dto = new TicketDto();
        dto.setShowId(2L);
        dto.setSeatId(3L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(showRepository.findById(2L)).thenReturn(Optional.of(show));
        when(seatRepository.findById(3L)).thenReturn(Optional.of(seat));
        when(ticketRepository.findByShowIdAndSeatId(2L, 3L)).thenReturn(Optional.empty());

        Ticket savedTicket = new Ticket();
        savedTicket.setId(10L);
        savedTicket.setUser(user);
        savedTicket.setShow(show);
        savedTicket.setSeat(seat);
        savedTicket.setBookedAt(LocalDateTime.now());

        when(ticketRepository.save(any(Ticket.class))).thenReturn(savedTicket);

        TicketDto result = ticketService.bookTicket(1L, dto);

        assertEquals(10L, result.getId());
        assertEquals(2L, result.getShowId());
        assertEquals(3L, result.getSeatId());
    }

    @Test
    void testBookTicket_seatAlreadyBooked() {
        TicketDto dto = new TicketDto();
        dto.setShowId(2L);
        dto.setSeatId(3L);

        User user = new User();
        user.setId(1L);

        Show show = new Show();
        show.setId(2L);

        Seat seat = new Seat();
        seat.setId(3L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(showRepository.findById(2L)).thenReturn(Optional.of(show));
        when(seatRepository.findById(3L)).thenReturn(Optional.of(seat));
        when(ticketRepository.findByShowIdAndSeatId(2L, 3L)).thenReturn(Optional.of(new Ticket()));

        assertThrows(SeatAlreadyBookedException.class, () -> ticketService.bookTicket(1L, dto));
    }

    @Test
    void testCancelTicket_success() {
        Ticket ticket = new Ticket();
        ticket.setId(5L);

        when(ticketRepository.findByIdAndUserId(5L, 1L)).thenReturn(Optional.of(ticket));

        assertDoesNotThrow(() -> ticketService.cancelTicket(1L, 5L));
        verify(ticketRepository, times(1)).delete(ticket);
    }

    @Test
    void testCancelTicket_notFound() {
        when(ticketRepository.findByIdAndUserId(99L, 1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> ticketService.cancelTicket(1L, 99L));
    }
}
