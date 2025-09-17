package com.zhumagulorken.cinema.showtime.service;

import com.zhumagulorken.cinema.showtime.entity.Theater;
import com.zhumagulorken.cinema.showtime.repository.TheaterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TheaterService {
    private final TheaterRepository repository;

    public TheaterService(TheaterRepository repository) {
        this.repository = repository;
    }

    public List<Theater> getAll() {
        return repository.findAll();
    }

    public Theater create(Theater theater) {
        return repository.save(theater);
    }

    public Optional<Theater> getById(Long id) {
        return repository.findById(id);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
