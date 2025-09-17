package com.zhumagulorken.cinema.showtime.service;

import com.zhumagulorken.cinema.showtime.entity.Show;
import com.zhumagulorken.cinema.showtime.repository.ShowRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShowService {
    private final ShowRepository repository;

    public ShowService(ShowRepository repository) {
        this.repository = repository;
    }

    public List<Show> getAll() {
        return repository.findAll();
    }

    public Show create(Show show) {
        return repository.save(show);
    }

    public Optional<Show> getById(Long id) {
        return repository.findById(id);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
