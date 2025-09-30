package com.zhumagulorken.cinema.showtime.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhumagulorken.cinema.showtime.dto.GenreDto;
import com.zhumagulorken.cinema.showtime.dto.MovieDto;
import com.zhumagulorken.cinema.showtime.repository.GenreRepository;
import com.zhumagulorken.cinema.showtime.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class MovieControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateAndGetMovie() throws Exception {
        GenreDto genreDto = new GenreDto();
        genreDto.setName("Sci-Fi");

        String genreResponse = mockMvc.perform(post("/genres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(genreDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        GenreDto savedGenre = objectMapper.readValue(genreResponse, GenreDto.class);

        MovieDto movieDto = new MovieDto();
        movieDto.setTitle("Dune");
        movieDto.setDuration("PT155M");
        movieDto.setReleaseDate(LocalDate.of(2021, 10, 22));
        movieDto.setRating(BigDecimal.valueOf(8.0));
        movieDto.setGenreId(savedGenre.getId());

        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Dune"))
                .andExpect(jsonPath("$.genreId").value(savedGenre.getId()));

        mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Dune"));
    }

    @Test
    void testDeleteMovie() throws Exception {
        GenreDto genreDto = new GenreDto();
        genreDto.setName("Horror");

        String genreResponse = mockMvc.perform(post("/genres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(genreDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        GenreDto savedGenre = objectMapper.readValue(genreResponse, GenreDto.class);

        MovieDto movieDto = new MovieDto();
        movieDto.setTitle("It");
        movieDto.setDuration("PT135M");
        movieDto.setReleaseDate(LocalDate.of(2017, 9, 8));
        movieDto.setRating(BigDecimal.valueOf(7.4));
        movieDto.setGenreId(savedGenre.getId());

        String movieResponse = mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        MovieDto savedMovie = objectMapper.readValue(movieResponse, MovieDto.class);

        mockMvc.perform(delete("/movies/{id}", savedMovie.getId()))
                .andExpect(status().isNoContent());
    }
}
