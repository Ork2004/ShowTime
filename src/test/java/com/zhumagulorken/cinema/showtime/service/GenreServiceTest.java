package com.zhumagulorken.cinema.showtime.service;

import com.zhumagulorken.cinema.showtime.dto.GenreDto;
import com.zhumagulorken.cinema.showtime.entity.Genre;
import com.zhumagulorken.cinema.showtime.exсeption.NotFoundException;
import com.zhumagulorken.cinema.showtime.repository.GenreRepository;
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
class GenreServiceTest {

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private GenreService genreService;

    @Test
    void testGetGenres() {
        Genre genre1 = new Genre();
        genre1.setId(1L);
        genre1.setName("Action");

        Genre genre2 = new Genre();
        genre2.setId(2L);
        genre2.setName("Comedy");

        when(genreRepository.findAll()).thenReturn(List.of(genre1, genre2));

        List<GenreDto> genres = genreService.getGenres();

        assertEquals(2, genres.size());
        assertEquals("Action", genres.get(0).getName());
        assertEquals("Comedy", genres.get(1).getName());
    }

    @Test
    void testGetGenreById_found() {
        Genre genre = new Genre();
        genre.setId(1L);
        genre.setName("Action");

        when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));

        GenreDto result = genreService.getGenreById(1L);

        assertEquals("Action", result.getName());
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetGenreById_not_found() {
        when(genreRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> genreService.getGenreById(1L));
    }

    @Test
    void TestCreateGenre() {
        GenreDto dto = new GenreDto();
        dto.setName("Horror");

        Genre savedGenre = new Genre();
        savedGenre.setId(1L);
        savedGenre.setName("Horror");

        when(genreRepository.save(any(Genre.class))).thenReturn(savedGenre);

        GenreDto result = genreService.createGenre(dto);

        assertEquals(1L, result.getId());
        assertEquals("Horror", result.getName());
    }

    @Test
    void testDeleteGenre_found() {
        when(genreRepository.existsById(1L)).thenReturn(true);
        doNothing().when(genreRepository).deleteById(1L);

        assertDoesNotThrow(() -> genreService.deleteGenre(1L));
        verify(genreRepository, times(1)).existsById(1L);
    }

    @Test
    void testDeleteGenre_not_found() {
        when(genreRepository.existsById(1L)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> genreService.deleteGenre(1L));
    }
}
