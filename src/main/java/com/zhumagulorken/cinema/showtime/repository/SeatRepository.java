package com.zhumagulorken.cinema.showtime.repository;

import com.zhumagulorken.cinema.showtime.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat,Long> {
    List<Seat> findByHallId(Long hallId);
    Optional<Seat> findByIdAndHallId(Long id, Long hallId);
}
