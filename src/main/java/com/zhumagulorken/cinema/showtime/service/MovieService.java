package com.zhumagulorken.cinema.showtime.service;

import com.zhumagulorken.cinema.showtime.entity.Movie;
import com.zhumagulorken.cinema.showtime.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    private final MovieRepository repository;

    public MovieService (MovieRepository repository) {
        this.repository = repository;
    }

    public List<Movie> getAll() {
        return repository.findAll();
    }

    public Movie create(Movie movie) {
        return repository.save(movie);
    }

    public Optional<Movie> getById(Long id) {
        return repository.findById(id);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
