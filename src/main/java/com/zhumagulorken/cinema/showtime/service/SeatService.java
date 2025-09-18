package com.zhumagulorken.cinema.showtime.service;

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

    public List<Seat> getSeatsByHall(Long hallId) {
        return seatRepository.findByHallId(hallId);
    }

    public Seat getSeatByIdAndHall(Long hallId, Long seatId) {
        return seatRepository.findByIdAndHallId(seatId, hallId)
                .orElseThrow(() -> new RuntimeException("Seat not found"));
    }

    public Seat createSeat(Long hallId, Seat seat) {
        Hall hall = hallRepository.findById(hallId)
                .orElseThrow(() -> new RuntimeException("Hall not found"));
        seat.setHall(hall);
        return seatRepository.save(seat);
    }

    public void deleteSeat(Long hallId, Long seatId) {
        Seat seat = getSeatByIdAndHall(hallId, seatId);
        seatRepository.delete(seat);
    }
}
