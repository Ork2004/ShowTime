package com.zhumagulorken.cinema.showtime.repository;

import com.zhumagulorken.cinema.showtime.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre,Long> {
}
