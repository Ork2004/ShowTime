package com.zhumagulorken.cinema.showtime.service;

import com.zhumagulorken.cinema.showtime.dto.ShowDto;
import com.zhumagulorken.cinema.showtime.entity.Hall;
import com.zhumagulorken.cinema.showtime.entity.Movie;
import com.zhumagulorken.cinema.showtime.entity.Show;
import com.zhumagulorken.cinema.showtime.exсeption.NotFoundException;
import com.zhumagulorken.cinema.showtime.repository.HallRepository;
import com.zhumagulorken.cinema.showtime.repository.MovieRepository;
import com.zhumagulorken.cinema.showtime.repository.ShowRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShowServiceTest {

    @Mock
    private ShowRepository showRepository;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private HallRepository hallRepository;

    @InjectMocks
    private ShowService showService;

    @Test
    void testGetShowsByMovie() {
        Movie movie = new Movie();
        movie.setId(1L);

        Hall hall = new Hall();
        hall.setId(2L);

        Show show = new Show();
        show.setId(10L);
        show.setShowTime(LocalDateTime.now());
        show.setPrice(BigDecimal.valueOf(1500));
        show.setMovie(movie);
        show.setHall(hall);

        when(showRepository.findByMovieId(1L)).thenReturn(List.of(show));

        List<ShowDto> result = showService.getShowsByMovie(1L);

        assertEquals(1, result.size());
        assertEquals(1500, result.get(0).getPrice().intValue());
        assertEquals(2L, result.get(0).getHallId());
    }

    @Test
    void testGetShowByIdAndMovie_found() {
        Movie movie = new Movie();
        movie.setId(1L);

        Hall hall = new Hall();
        hall.setId(2L);

        Show show = new Show();
        show.setId(20L);
        show.setShowTime(LocalDateTime.now());
        show.setPrice(BigDecimal.TEN);
        show.setMovie(movie);
        show.setHall(hall);

        when(showRepository.findByIdAndMovieId(20L, 1L)).thenReturn(Optional.of(show));

        ShowDto result = showService.getShowByIdAndMovie(1L, 20L);

        assertEquals(BigDecimal.TEN, result.getPrice());
        assertEquals(1L, result.getMovieId());
        assertEquals(2L, result.getHallId());
    }

    @Test
    void testGetShowByIdAndMovie_notFound() {
        when(showRepository.findByIdAndMovieId(99L, 1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> showService.getShowByIdAndMovie(1L, 99L));
    }

    @Test
    void testCreateShow_success() {
        Movie movie = new Movie();
        movie.setId(1L);

        Hall hall = new Hall();
        hall.setId(2L);

        ShowDto dto = new ShowDto();
        dto.setShowTime(LocalDateTime.of(2025, 1, 1, 20, 0));
        dto.setPrice(BigDecimal.valueOf(1200));
        dto.setHallId(2L);

        Show show = new Show();
        show.setId(30L);
        show.setShowTime(dto.getShowTime());
        show.setPrice(dto.getPrice());
        show.setMovie(movie);
        show.setHall(hall);

        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(hallRepository.findById(2L)).thenReturn(Optional.of(hall));
        when(showRepository.save(any(Show.class))).thenReturn(show);

        ShowDto result = showService.createShow(1L, dto);

        assertEquals(30L, result.getId());
        assertEquals(1200, result.getPrice().intValue());
        assertEquals(2L, result.getHallId());
    }

    @Test
    void testCreateShow_movieNotFound() {
        ShowDto dto = new ShowDto();
        dto.setShowTime(LocalDateTime.now());
        dto.setPrice(BigDecimal.ONE);
        dto.setHallId(1L);

        when(movieRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> showService.createShow(99L, dto));
    }

    @Test
    void testCreateShow_hallNotFound() {
        Movie movie = new Movie();
        movie.setId(1L);

        ShowDto dto = new ShowDto();
        dto.setShowTime(LocalDateTime.now());
        dto.setPrice(BigDecimal.ONE);
        dto.setHallId(100L);

        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(hallRepository.findById(100L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> showService.createShow(1L, dto));
    }

    @Test
    void testDeleteShow_success() {
        Movie movie = new Movie();
        movie.setId(1L);

        Hall hall = new Hall();
        hall.setId(2L);

        Show show = new Show();
        show.setId(40L);
        show.setMovie(movie);
        show.setHall(hall);

        when(showRepository.findByIdAndMovieId(40L, 1L)).thenReturn(Optional.of(show));
        doNothing().when(showRepository).delete(show);

        assertDoesNotThrow(() -> showService.deleteShow(1L, 40L));
        verify(showRepository, times(1)).delete(show);
    }

    @Test
    void testDeleteShow_notFound() {
        when(showRepository.findByIdAndMovieId(50L, 1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> showService.deleteShow(1L, 50L));
    }
}
