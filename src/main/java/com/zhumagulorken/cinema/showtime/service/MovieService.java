package com.zhumagulorken.cinema.showtime.service;

import com.zhumagulorken.cinema.showtime.entity.Movie;
import com.zhumagulorken.cinema.showtime.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService (MovieRepository repository) {
        this.movieRepository = repository;
    }

    public List<Movie> getAll() {
        return movieRepository.findAll();
    }

    public Movie create(Movie movie) {
        return movieRepository.save(movie);
    }

    public Optional<Movie> getById(Long id) {
        return movieRepository.findById(id);
    }

    public void delete(Long id) {
        movieRepository.deleteById(id);
    }
}
