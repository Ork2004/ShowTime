package com.zhumagulorken.cinema.showtime.service;

import com.zhumagulorken.cinema.showtime.dto.TheaterDto;
import com.zhumagulorken.cinema.showtime.entity.Theater;
import com.zhumagulorken.cinema.showtime.exсeption.NotFoundException;
import com.zhumagulorken.cinema.showtime.repository.TheaterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
                .orElseThrow(() -> new NotFoundException(Theater.class, id));
        return mapToDto(theater);
    }


    public TheaterDto createTheater(TheaterDto dto) {
        Theater theater = new Theater();
        theater.setName(dto.getName());
        theater.setLocation(dto.getLocation());

        Theater saved = theaterRepository.save(theater);
        return mapToDto(saved);
    }

    public TheaterDto updateTheater(Long id, TheaterDto dto) {
        Theater theater = theaterRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Theater.class, id));
        theater.setName(dto.getName());
        theater.setLocation(dto.getLocation());
        return mapToDto(theaterRepository.save(theater));
    }

    public void deleteTheater(Long id) {
        if (!theaterRepository.existsById(id)) {
            throw new NotFoundException(Theater.class, id);
        }
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
