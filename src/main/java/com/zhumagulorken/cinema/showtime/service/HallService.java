package com.zhumagulorken.cinema.showtime.service;

import com.zhumagulorken.cinema.showtime.entity.Hall;
import com.zhumagulorken.cinema.showtime.repository.HallRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HallService {
    private final HallRepository repository;

    public HallService(HallRepository repository) {
        this.repository = repository;
    }

    public List<Hall> getAll() {
        return repository.findAll();
    }

    public Hall create(Hall hall) {
        return repository.save(hall);
    }

    public Optional<Hall> getById(Long id) {
        return repository.findById(id);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
