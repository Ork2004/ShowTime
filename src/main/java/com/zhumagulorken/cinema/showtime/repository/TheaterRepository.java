package com.zhumagulorken.cinema.showtime.repository;

import com.zhumagulorken.cinema.showtime.entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Long> {
}
