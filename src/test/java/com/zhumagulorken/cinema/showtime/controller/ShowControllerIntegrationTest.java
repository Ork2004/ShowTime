package com.zhumagulorken.cinema.showtime.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhumagulorken.cinema.showtime.dto.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class ShowControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateAndGetShow() throws Exception {
        GenreDto genreDto = new GenreDto();
        genreDto.setName("Action");

        String genreResponse = mockMvc.perform(post("/genres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(genreDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        GenreDto savedGenre = objectMapper.readValue(genreResponse, GenreDto.class);

        MovieDto movieDto = new MovieDto();
        movieDto.setTitle("Fast & Furious");
        movieDto.setDuration("02:00:00");
        movieDto.setReleaseDate(LocalDate.parse("2025-01-01"));
        movieDto.setRating(BigDecimal.valueOf(8.5));
        movieDto.setGenreId(savedGenre.getId());

        String movieResponse = mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        MovieDto savedMovie = objectMapper.readValue(movieResponse, MovieDto.class);

        TheaterDto theaterDto = new TheaterDto();
        theaterDto.setName("Grand Cinema");
        theaterDto.setLocation("Downtown");

        String theaterResponse = mockMvc.perform(post("/theaters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(theaterDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        TheaterDto savedTheater = objectMapper.readValue(theaterResponse, TheaterDto.class);

        HallDto hallDto = new HallDto();
        hallDto.setName("Main Hall");

        String hallResponse = mockMvc.perform(post("/theaters/{theaterId}/halls", savedTheater.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(hallDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        HallDto savedHall = objectMapper.readValue(hallResponse, HallDto.class);

        ShowDto showDto = new ShowDto();
        showDto.setShowTime(LocalDateTime.now().plusDays(1));
        showDto.setPrice(BigDecimal.valueOf(2000));
        showDto.setHallId(savedHall.getId());

        mockMvc.perform(post("/movies/{movieId}/shows", savedMovie.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(showDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(2000))
                .andExpect(jsonPath("$.hallId").value(savedHall.getId()))
                .andExpect(jsonPath("$.movieId").value(savedMovie.getId()));

        mockMvc.perform(get("/movies/{movieId}/shows", savedMovie.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].movieId").value(savedMovie.getId()));
    }

    @Test
    void testDeleteShow() throws Exception {
        GenreDto genreDto = new GenreDto();
        genreDto.setName("Comedy");

        String genreResponse = mockMvc.perform(post("/genres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(genreDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        GenreDto savedGenre = objectMapper.readValue(genreResponse, GenreDto.class);

        MovieDto movieDto = new MovieDto();
        movieDto.setTitle("Funny Movie");
        movieDto.setDuration("01:30:00");
        movieDto.setReleaseDate(LocalDate.parse("2025-02-01"));
        movieDto.setRating(BigDecimal.valueOf(7.0));
        movieDto.setGenreId(savedGenre.getId());

        String movieResponse = mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        MovieDto savedMovie = objectMapper.readValue(movieResponse, MovieDto.class);

        TheaterDto theaterDto = new TheaterDto();
        theaterDto.setName("Comedy Theater");
        theaterDto.setLocation("Central");

        String theaterResponse = mockMvc.perform(post("/theaters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(theaterDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        TheaterDto savedTheater = objectMapper.readValue(theaterResponse, TheaterDto.class);

        HallDto hallDto = new HallDto();
        hallDto.setName("Comedy Hall");

        String hallResponse = mockMvc.perform(post("/theaters/{theaterId}/halls", savedTheater.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(hallDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        HallDto savedHall = objectMapper.readValue(hallResponse, HallDto.class);

        ShowDto showDto = new ShowDto();
        showDto.setShowTime(LocalDateTime.now().plusDays(2));
        showDto.setPrice(BigDecimal.valueOf(1500));
        showDto.setHallId(savedHall.getId());

        String showResponse = mockMvc.perform(post("/movies/{movieId}/shows", savedMovie.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(showDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        ShowDto savedShow = objectMapper.readValue(showResponse, ShowDto.class);

        mockMvc.perform(delete("/movies/{movieId}/shows/{id}", savedMovie.getId(), savedShow.getId()))
                .andExpect(status().isNoContent());
    }
}
