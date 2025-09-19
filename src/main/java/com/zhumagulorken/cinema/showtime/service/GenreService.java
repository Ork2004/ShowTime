package com.zhumagulorken.cinema.showtime.service;

import com.zhumagulorken.cinema.showtime.entity.Genre;
import com.zhumagulorken.cinema.showtime.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {
    private final GenreRepository genreRepository;

    public GenreService(GenreRepository repository) {
        this.genreRepository = repository;
    }

    public List<Genre> getGenres() {
        return genreRepository.findAll();
    }

    public Optional<Genre> getGenreById(Long Id) {
        return genreRepository.findById(Id);
    }

    public Genre createGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    public void deleteGenre(Long Id) {
        genreRepository.deleteById(Id);
    }
}
