package com.zhumagulorken.cinema.showtime.service;

import com.zhumagulorken.cinema.showtime.dto.TheaterDto;
import com.zhumagulorken.cinema.showtime.entity.Theater;
import com.zhumagulorken.cinema.showtime.repository.TheaterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TheaterService {
    private final TheaterRepository theaterRepository;

    public TheaterService(TheaterRepository theaterRepository) {
        this.theaterRepository = theaterRepository;
    }

    public List<TheaterDto> getTheaters() {
        return theaterRepository.findAll().stream()
                .map(this::mapToDto)
                .toList();
    }

    public TheaterDto getTheaterById(Long id) {
        Theater theater = theaterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Theater not found"));
        return mapToDto(theater);
    }


    public TheaterDto createTheater(TheaterDto dto) {
        Theater theater = new Theater();
        theater.setName(dto.getName());
        theater.setLocation(dto.getLocation());

        Theater saved = theaterRepository.save(theater);
        return mapToDto(saved);
    }

    public void deleteTheater(Long id) {
        theaterRepository.deleteById(id);
    }

    private TheaterDto mapToDto(Theater theater) {
        TheaterDto dto = new TheaterDto();
        dto.setId(theater.getId());
        dto.setName(theater.getName());
        dto.setLocation(theater.getLocation());
        return dto;
    }
}
