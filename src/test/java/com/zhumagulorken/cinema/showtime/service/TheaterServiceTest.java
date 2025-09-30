package com.zhumagulorken.cinema.showtime.service;

import com.zhumagulorken.cinema.showtime.dto.TheaterDto;
import com.zhumagulorken.cinema.showtime.entity.Theater;
import com.zhumagulorken.cinema.showtime.exсeption.NotFoundException;
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
class TheaterServiceTest {

    @Mock
    private TheaterRepository theaterRepository;

    @InjectMocks
    private TheaterService theaterService;

    @Test
    void testGetTheaters() {
        Theater theater = new Theater();
        theater.setId(1L);
        theater.setName("Cinemax");
        theater.setLocation("Downtown");

        when(theaterRepository.findAll()).thenReturn(List.of(theater));

        List<TheaterDto> result = theaterService.getTheaters();

        assertEquals(1, result.size());
        assertEquals("Cinemax", result.get(0).getName());
        assertEquals("Downtown", result.get(0).getLocation());
    }

    @Test
    void testGetTheaterById_found() {
        Theater theater = new Theater();
        theater.setId(1L);
        theater.setName("IMAX");

        when(theaterRepository.findById(1L)).thenReturn(Optional.of(theater));

        TheaterDto result = theaterService.getTheaterById(1L);

        assertEquals("IMAX", result.getName());
    }

    @Test
    void testGetTheaterById_notFound() {
        when(theaterRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> theaterService.getTheaterById(1L));
    }

    @Test
    void testCreateTheater() {
        TheaterDto dto = new TheaterDto();
        dto.setName("Mega Cinema");
        dto.setLocation("Central Park");

        Theater saved = new Theater();
        saved.setId(1L);
        saved.setName("Mega Cinema");
        saved.setLocation("Central Park");

        when(theaterRepository.save(any(Theater.class))).thenReturn(saved);

        TheaterDto result = theaterService.createTheater(dto);

        assertEquals(1L, result.getId());
        assertEquals("Mega Cinema", result.getName());
        assertEquals("Central Park", result.getLocation());
    }

    @Test
    void testDeleteTheater_found() {
        when(theaterRepository.existsById(1L)).thenReturn(true);
        doNothing().when(theaterRepository).deleteById(1L);

        assertDoesNotThrow(() -> theaterService.deleteTheater(1L));
        verify(theaterRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteTheater_notFound() {
        when(theaterRepository.existsById(1L)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> theaterService.deleteTheater(1L));
    }
}
