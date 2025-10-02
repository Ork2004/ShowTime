package com.zhumagulorken.cinema.showtime.service;

import com.zhumagulorken.cinema.showtime.dto.HallDto;
import com.zhumagulorken.cinema.showtime.entity.Hall;
import com.zhumagulorken.cinema.showtime.entity.Theater;
import com.zhumagulorken.cinema.showtime.exсeption.NotFoundException;
import com.zhumagulorken.cinema.showtime.repository.HallRepository;
import com.zhumagulorken.cinema.showtime.repository.TheaterRepository;
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
public class HallServiceTest {

    @Mock
    private HallRepository hallRepository;

    @Mock
    private TheaterRepository theaterRepository;

    @InjectMocks
    private HallService hallService;

    @Test
    void testGetHallsByTheater(){
        Theater theater = new Theater();
        theater.setId(1L);

        Hall hall = new Hall();
        hall.setId(1L);
        hall.setName("Hall A");
        hall.setTheater(theater);

        when(hallRepository.findByTheaterId(1L)).thenReturn(List.of(hall));

        List<HallDto> result = hallService.getHallsByTheater(1L);

        assertEquals(1, result.size());
        assertEquals("Hall A", result.get(0).getName());
        assertEquals(1L, result.get(0).getTheaterId());
    }

    @Test
    void testGetHallByIdandTheater_found(){
        Theater theater = new Theater();
        theater.setId(1L);

        Hall hall = new Hall();
        hall.setId(5L);
        hall.setName("Hall B");
        hall.setTheater(theater);

        when(hallRepository.findByIdAndTheaterId(5L,1L)).thenReturn(Optional.of(hall));

        HallDto result = hallService.getHallByIdAndTheater(1L,5L);

        assertEquals("Hall B", result.getName());
    }

    @Test
    void testGetHallByIdandTheater_notfound(){
        when(hallRepository.findByIdAndTheaterId(5L,1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> hallService.getHallByIdAndTheater(1L,5L));
    }

    @Test
    void testCreateHall(){
        Theater theater = new Theater();
        theater.setId(2L);

        HallDto dto = new HallDto();
        dto.setName("Hall C");

        Hall hall = new Hall();
        hall.setId(20L);
        hall.setName("Hall C");
        hall.setTheater(theater);

        when(theaterRepository.findById(2L)).thenReturn(Optional.of(theater));
        when(hallRepository.save(any(Hall.class))).thenReturn(hall);

        HallDto result = hallService.createHall(2L, dto);

        assertEquals(20L, result.getId());
        assertEquals("Hall C", result.getName());
        assertEquals(2L, result.getTheaterId());
    }

    @Test
    void testCreateHall_theaterNotFound(){
        HallDto dto = new HallDto();
        dto.setName("Hall D");

        when(theaterRepository.findById(100L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> hallService.createHall(100L, dto));
    }

    @Test
    void testDeleteHall_found() {
        Theater theater = new Theater();
        theater.setId(1L);

        Hall hall = new Hall();
        hall.setId(30L);
        hall.setName("Hall E");
        hall.setTheater(theater);

        when(hallRepository.findByIdAndTheaterId(30L, 1L)).thenReturn(Optional.of(hall));
        doNothing().when(hallRepository).delete(hall);

        assertDoesNotThrow(() -> hallService.deleteHall(1L, 30L));
        verify(hallRepository, times(1)).delete(hall);
    }

    @Test
    void testDeleteHall_notFound() {
        when(hallRepository.findByIdAndTheaterId(30L, 1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> hallService.deleteHall(1L, 30L));
    }
}
