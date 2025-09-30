package com.zhumagulorken.cinema.showtime.service;

import com.zhumagulorken.cinema.showtime.dto.MovieDto;
import com.zhumagulorken.cinema.showtime.entity.Genre;
import com.zhumagulorken.cinema.showtime.entity.Movie;
import com.zhumagulorken.cinema.showtime.exсeption.NotFoundException;
import com.zhumagulorken.cinema.showtime.repository.GenreRepository;
import com.zhumagulorken.cinema.showtime.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private MovieService movieService;

    @Test
    void testGetMovies() {
        Genre genre = new Genre();
        genre.setId(1L);
        genre.setName("Action");

        Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Inception");
        movie.setDuration("PT148M");
        movie.setReleaseDate(LocalDate.of(2010, 7, 16));
        movie.setRating(BigDecimal.valueOf(8.8));
        movie.setGenre(genre);

        when(movieRepository.findAll()).thenReturn(List.of(movie));

        List<MovieDto> movies = movieService.getMovies();

        assertEquals(1, movies.size());
        assertEquals("Inception", movies.get(0).getTitle());
        assertEquals(1L, movies.get(0).getGenreId());
    }

    @Test
    void testGetMovieById_found() {
        Genre genre = new Genre();
        genre.setId(1L);

        Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Interstellar");
        movie.setGenre(genre);

        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

        MovieDto result = movieService.getMovieById(1L);

        assertEquals("Interstellar", result.getTitle());
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetMovieById_notFound() {
        when(movieRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> movieService.getMovieById(1L));
    }

    @Test
    void testCreateMovie_success() {
        Genre genre = new Genre();
        genre.setId(1L);
        genre.setName("Drama");

        MovieDto dto = new MovieDto();
        dto.setTitle("The Godfather");
        dto.setDuration("PT175M");
        dto.setReleaseDate(LocalDate.of(1972, 3, 24));
        dto.setRating(BigDecimal.valueOf(9.2));
        dto.setGenreId(1L);

        when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));

        Movie savedMovie = new Movie();
        savedMovie.setId(1L);
        savedMovie.setTitle("The Godfather");
        savedMovie.setDuration("PT175M");
        savedMovie.setReleaseDate(LocalDate.of(1972, 3, 24));
        savedMovie.setRating(BigDecimal.valueOf(9.2));
        savedMovie.setGenre(genre);

        when(movieRepository.save(any(Movie.class))).thenReturn(savedMovie);

        MovieDto result = movieService.createMovie(dto);

        assertEquals(1L, result.getId());
        assertEquals("The Godfather", result.getTitle());
        assertEquals(1L, result.getGenreId());
    }

    @Test
    void testCreateMovie_genreNotFound() {
        MovieDto dto = new MovieDto();
        dto.setGenreId(99L);

        when(genreRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> movieService.createMovie(dto));
    }

    @Test
    void testDeleteMovie_found() {
        when(movieRepository.existsById(1L)).thenReturn(true);
        doNothing().when(movieRepository).deleteById(1L);

        assertDoesNotThrow(() -> movieService.deleteMovie(1L));
        verify(movieRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteMovie_notFound() {
        when(movieRepository.existsById(1L)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> movieService.deleteMovie(1L));
    }
}
