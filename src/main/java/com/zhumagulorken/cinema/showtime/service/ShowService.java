package com.zhumagulorken.cinema.showtime.service;

import com.zhumagulorken.cinema.showtime.dto.ShowDto;
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

    public List<ShowDto> getShowsByMovie(Long movieId) {
        return showRepository.findByMovieId(movieId)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public ShowDto getShowByIdAndMovie(Long movieId, Long showId) {
        Show show = showRepository.findByIdAndMovieId(showId, movieId)
                .orElseThrow(() -> new RuntimeException("Show not found"));
        return mapToDto(show);
    }

    public ShowDto createShow(Long movieId, ShowDto dto) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        Hall hall = hallRepository.findById(dto.getHallId())
                .orElseThrow(() -> new RuntimeException("Hall not found"));

        Show show = new Show();
        show.setShowTime(dto.getShowTime());
        show.setPrice(dto.getPrice());
        show.setMovie(movie);
        show.setHall(hall);

        return mapToDto(showRepository.save(show));
    }

    public void deleteShow(Long movieId, Long showId) {
        Show show = showRepository.findByIdAndMovieId(showId, movieId)
                .orElseThrow(() -> new RuntimeException("Show not found"));
        showRepository.delete(show);
    }

    private ShowDto mapToDto(Show show) {
        ShowDto dto = new ShowDto();
        dto.setId(show.getId());
        dto.setShowTime(show.getShowTime());
        dto.setPrice(show.getPrice());
        dto.setMovieId(show.getMovie().getId());
        dto.setHallId(show.getHall().getId());
        return dto;
    }
}
