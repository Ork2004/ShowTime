package com.zhumagulorken.cinema.showtime.repository;

import com.zhumagulorken.cinema.showtime.entity.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HallRepository extends JpaRepository<Hall,Long> {
    List<Hall> findByTheaterId(Long theaterId);
    Optional<Hall> findByIdAndTheaterId(Long id, Long theaterId);
}
