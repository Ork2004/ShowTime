package com.zhumagulorken.cinema.showtime.service;

import com.zhumagulorken.cinema.showtime.dto.SeatDto;
import com.zhumagulorken.cinema.showtime.entity.Hall;
import com.zhumagulorken.cinema.showtime.entity.Seat;
import com.zhumagulorken.cinema.showtime.repository.HallRepository;
import com.zhumagulorken.cinema.showtime.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeatService {
    private final SeatRepository seatRepository;
    private final HallRepository hallRepository;

    public SeatService(SeatRepository seatRepository, HallRepository hallRepository) {
        this.seatRepository = seatRepository;
        this.hallRepository = hallRepository;
    }

    public List<SeatDto> getSeatsByHall(Long hallId) {
        return seatRepository.findByHallId(hallId)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public SeatDto getSeatByIdAndHall(Long hallId, Long seatId) {
        Seat seat = seatRepository.findByIdAndHallId(seatId, hallId)
                .orElseThrow(() -> new RuntimeException("Seat not found"));
        return mapToDto(seat);
    }

    public SeatDto createSeat(Long hallId, SeatDto dto) {
        Hall hall = hallRepository.findById(hallId)
                .orElseThrow(() -> new RuntimeException("Hall not found"));

        Seat seat = new Seat();
        seat.setRowNumber(dto.getRowNumber());
        seat.setSeatNumber(dto.getSeatNumber());
        seat.setHall(hall);

        return mapToDto(seatRepository.save(seat));
    }

    public void deleteSeat(Long hallId, Long seatId) {
        Seat seat = seatRepository.findByIdAndHallId(seatId, hallId)
                .orElseThrow(() -> new RuntimeException("Seat not found"));
        seatRepository.delete(seat);
    }

    private SeatDto mapToDto(Seat seat) {
        SeatDto dto = new SeatDto();
        dto.setId(seat.getId());
        dto.setRowNumber(seat.getRowNumber());
        dto.setSeatNumber(seat.getSeatNumber());
        dto.setHallId(seat.getHall().getId());
        return dto;
    }
}
