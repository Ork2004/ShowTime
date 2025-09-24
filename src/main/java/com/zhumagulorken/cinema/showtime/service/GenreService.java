package com.zhumagulorken.cinema.showtime.service;

import com.zhumagulorken.cinema.showtime.dto.GenreDto;
import com.zhumagulorken.cinema.showtime.entity.Genre;
import com.zhumagulorken.cinema.showtime.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {
    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<GenreDto> getGenres() {
        return genreRepository.findAll().stream()
                .map(this::mapToDto)
                .toList();
    }

    public GenreDto getGenreById(Long id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Genre not found"));
        return mapToDto(genre);
    }

    public GenreDto createGenre(GenreDto dto) {
        Genre genre = new Genre();
        genre.setName(dto.getName());

        Genre saved = genreRepository.save(genre);
        return mapToDto(saved);
    }

    public void deleteGenre(Long id) {
        if (!genreRepository.existsById(id)) {
            throw new RuntimeException("Genre not found");
        }
        genreRepository.deleteById(id);
    }

    private GenreDto mapToDto(Genre genre) {
        GenreDto dto = new GenreDto();
        dto.setId(genre.getId());
        dto.setName(genre.getName());
        return dto;
    }
}
