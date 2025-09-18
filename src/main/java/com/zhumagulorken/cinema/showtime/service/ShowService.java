package com.zhumagulorken.cinema.showtime.service;

import com.zhumagulorken.cinema.showtime.entity.Hall;
import com.zhumagulorken.cinema.showtime.entity.Movie;
import com.zhumagulorken.cinema.showtime.entity.Show;
import com.zhumagulorken.cinema.showtime.repository.HallRepository;
import com.zhumagulorken.cinema.showtime.repository.MovieRepository;
import com.zhumagulorken.cinema.showtime.repository.ShowRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShowService {
    private final ShowRepository showRepository;
    private final MovieRepository movieRepository;
    private final HallRepository hallRepository;

    public ShowService(ShowRepository showRepository, MovieRepository movieRepository, HallRepository hallRepository) {
        this.showRepository = showRepository;
        this.movieRepository = movieRepository;
        this.hallRepository = hallRepository;
    }

    public List<Show> getShowsByMovie(Long movieId) {
        return showRepository.findByMovieId(movieId);
    }

    public Show getShowByIdAndMovie(Long movieId, Long showId) {
        return showRepository.findByIdAndMovieId(showId, movieId)
                .orElseThrow(() -> new RuntimeException("Show not found"));
    }

    public Show createShow(Long movieId, Show show) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        Hall hall = hallRepository.findById(show.getHall().getId())
                .orElseThrow(() -> new RuntimeException("Hall not found"));
        show.setMovie(movie);
        show.setHall(hall);
        return showRepository.save(show);
    }

    public void deleteShow(Long movieId,  Long showId) {
        Show show = getShowByIdAndMovie(movieId, showId);
        showRepository.delete(show);
    }
}
