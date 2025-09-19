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

    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    public Genre create(Genre genre) {
        return genreRepository.save(genre);
    }

    public Optional<Genre> getById(Long Id) {
        return genreRepository.findById(Id);
    }

    public void delete(Long Id) {
        genreRepository.deleteById(Id);
    }
}
