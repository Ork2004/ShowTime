package com.zhumagulorken.cinema.showtime.service;

import com.zhumagulorken.cinema.showtime.entity.Hall;
import com.zhumagulorken.cinema.showtime.entity.Theater;
import com.zhumagulorken.cinema.showtime.repository.HallRepository;
import com.zhumagulorken.cinema.showtime.repository.TheaterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HallService {
    private final HallRepository hallRepository;
    private final TheaterRepository theaterRepository;

    public HallService(HallRepository hallRepository,  TheaterRepository theaterRepository) {
        this.hallRepository = hallRepository;
        this.theaterRepository = theaterRepository;
    }

    public List<Hall> getHallsByTheater(Long theaterId) {
        return hallRepository.findByTheaterId(theaterId);
    }

    public Hall getHallByIdAndTheater(Long theaterId, Long hallId) {
        return hallRepository.findByIdAndTheaterId(hallId, theaterId)
                .orElseThrow(() -> new RuntimeException("Hall not found"));
    }

    public Hall createHall(Long theaterId, Hall hall) {
        Theater theater = theaterRepository.findById(theaterId)
                .orElseThrow(() -> new RuntimeException("Theater not found"));
        hall.setTheater(theater);
        return hallRepository.save(hall);
    }

    public void deleteHall(Long theaterId, Long hallId) {
        Hall hall = getHallByIdAndTheater(theaterId, hallId);
        hallRepository.delete(hall);
    }
}
