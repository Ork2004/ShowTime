package com.zhumagulorken.cinema.showtime.repository;

import com.zhumagulorken.cinema.showtime.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long> {
    List<Ticket> findByUserId(Long userId);
    Optional<Ticket> findByIdAndUserId(Long id, Long userId);
    Optional<Ticket> findByShowIdAndSeatId(Long showId, Long seatId);
}
