package com.zhumagulorken.cinema.showtime.service;

import com.zhumagulorken.cinema.showtime.entity.Genre;
import com.zhumagulorken.cinema.showtime.repository.GenreRepository;
import com.zhumagulorken.cinema.showtime.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {
    private final GenreRepository repository;

    public GenreService(GenreRepository repository) {
        this.repository = repository;
    }

    public List<Genre> getAll() {
        return repository.findAll();
    }

    public Genre create(Genre genre) {
        return repository.save(genre);
    }

    public Optional<Genre> getById(Long Id) {
        return repository.findById(Id);
    }

    public void delete(Long Id) {
        repository.deleteById(Id);
    }
}
