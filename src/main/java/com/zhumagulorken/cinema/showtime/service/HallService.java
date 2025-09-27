package com.zhumagulorken.cinema.showtime.service;

import com.zhumagulorken.cinema.showtime.dto.HallDto;
import com.zhumagulorken.cinema.showtime.entity.Hall;
import com.zhumagulorken.cinema.showtime.entity.Theater;
import com.zhumagulorken.cinema.showtime.exсeption.NotFoundException;
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

    public List<HallDto> getHallsByTheater(Long theaterId) {
        return hallRepository.findByTheaterId(theaterId)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public HallDto getHallByIdAndTheater(Long theaterId, Long hallId) {
        Hall hall = hallRepository.findByIdAndTheaterId(hallId, theaterId)
                .orElseThrow(() -> new NotFoundException(Hall.class, hallId));
        return mapToDto(hall);
    }

    public HallDto createHall(Long theaterId, HallDto dto) {
        Theater theater = theaterRepository.findById(theaterId)
                .orElseThrow(() -> new NotFoundException(Theater.class,  theaterId));

        Hall hall = new Hall();
        hall.setName(dto.getName());
        hall.setTheater(theater);

        return mapToDto(hallRepository.save(hall));
    }

    public void deleteHall(Long theaterId, Long hallId) {
        Hall hall = hallRepository.findByIdAndTheaterId(hallId, theaterId)
                .orElseThrow(() -> new NotFoundException(Hall.class, hallId));
        hallRepository.delete(hall);
    }

    private HallDto mapToDto(Hall hall) {
        HallDto dto = new HallDto();
        dto.setId(hall.getId());
        dto.setName(hall.getName());
        dto.setTheaterId(hall.getTheater().getId());
        return dto;
    }
}
