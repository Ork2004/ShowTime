package com.zhumagulorken.cinema.showtime.service;

import com.zhumagulorken.cinema.showtime.dto.SeatDto;
import com.zhumagulorken.cinema.showtime.entity.Hall;
import com.zhumagulorken.cinema.showtime.entity.Seat;
import com.zhumagulorken.cinema.showtime.exсeption.NotFoundException;
import com.zhumagulorken.cinema.showtime.repository.HallRepository;
import com.zhumagulorken.cinema.showtime.repository.SeatRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SeatServiceTest {

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private HallRepository hallRepository;

    @InjectMocks
    private SeatService seatService;

    @Test
    void testGetSeatsByHall() {
        Hall hall = new Hall();
        hall.setId(1L);

        Seat seat = new Seat();
        seat.setId(10L);
        seat.setRowNumber(1);
        seat.setSeatNumber(5);
        seat.setHall(hall);

        when(seatRepository.findByHallId(1L)).thenReturn(List.of(seat));

        List<SeatDto> result = seatService.getSeatsByHall(1L);

        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getRowNumber());
        assertEquals(5, result.get(0).getSeatNumber());
    }

    @Test
    void testGetSeatByIdAndHall_found() {
        Hall hall = new Hall();
        hall.setId(2L);

        Seat seat = new Seat();
        seat.setId(20L);
        seat.setRowNumber(2);
        seat.setSeatNumber(10);
        seat.setHall(hall);

        when(seatRepository.findByIdAndHallId(20L, 2L)).thenReturn(Optional.of(seat));

        SeatDto result = seatService.getSeatByIdAndHall(2L, 20L);

        assertEquals(2, result.getRowNumber());
        assertEquals(10, result.getSeatNumber());
    }

    @Test
    void testGetSeatByIdAndHall_notFound() {
        when(seatRepository.findByIdAndHallId(99L, 1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> seatService.getSeatByIdAndHall(1L, 99L));
    }

    @Test
    void testCreateSeat_success() {
        Hall hall = new Hall();
        hall.setId(3L);

        SeatDto dto = new SeatDto();
        dto.setRowNumber(3);
        dto.setSeatNumber(15);

        Seat seat = new Seat();
        seat.setId(30L);
        seat.setRowNumber(3);
        seat.setSeatNumber(15);
        seat.setHall(hall);

        when(hallRepository.findById(3L)).thenReturn(Optional.of(hall));
        when(seatRepository.save(any(Seat.class))).thenReturn(seat);

        SeatDto result = seatService.createSeat(3L, dto);

        assertEquals(30L, result.getId());
        assertEquals(3, result.getRowNumber());
        assertEquals(15, result.getSeatNumber());
        assertEquals(3L, result.getHallId());
    }

    @Test
    void testCreateSeat_hallNotFound() {
        SeatDto dto = new SeatDto();
        dto.setRowNumber(1);
        dto.setSeatNumber(1);

        when(hallRepository.findById(100L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> seatService.createSeat(100L, dto));
    }

    @Test
    void testDeleteSeat_success() {
        Hall hall = new Hall();
        hall.setId(4L);

        Seat seat = new Seat();
        seat.setId(40L);
        seat.setRowNumber(5);
        seat.setSeatNumber(7);
        seat.setHall(hall);

        when(seatRepository.findByIdAndHallId(40L, 4L)).thenReturn(Optional.of(seat));
        doNothing().when(seatRepository).delete(seat);

        assertDoesNotThrow(() -> seatService.deleteSeat(4L, 40L));
        verify(seatRepository, times(1)).delete(seat);
    }

    @Test
    void testDeleteSeat_notFound() {
        when(seatRepository.findByIdAndHallId(50L, 5L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> seatService.deleteSeat(5L, 50L));
    }
}
