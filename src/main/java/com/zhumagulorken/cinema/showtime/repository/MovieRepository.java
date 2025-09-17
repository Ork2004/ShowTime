package com.zhumagulorken.cinema.showtime.repository;

import com.zhumagulorken.cinema.showtime.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {
}
