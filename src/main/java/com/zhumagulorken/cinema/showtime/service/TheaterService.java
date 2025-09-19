package com.zhumagulorken.cinema.showtime.service;

import com.zhumagulorken.cinema.showtime.entity.Theater;
import com.zhumagulorken.cinema.showtime.repository.TheaterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TheaterService {
    private final TheaterRepository theaterRepository;

    public TheaterService(TheaterRepository repository) {
        this.theaterRepository = repository;
    }

    public List<Theater> getAll() {
        return theaterRepository.findAll();
    }

    public Theater create(Theater theater) {
        return theaterRepository.save(theater);
    }

    public Optional<Theater> getById(Long id) {
        return theaterRepository.findById(id);
    }

    public void delete(Long id) {
        theaterRepository.deleteById(id);
    }
}
