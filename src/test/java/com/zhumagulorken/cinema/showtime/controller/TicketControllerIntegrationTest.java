package com.zhumagulorken.cinema.showtime.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhumagulorken.cinema.showtime.dto.*;
import com.zhumagulorken.cinema.showtime.enums.Role;
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
class TicketControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testBookAndGetTicket() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setName("TicketUser");
        userDto.setEmail("ticketuser@example.com");
        userDto.setPassword("pass123");
        userDto.setRole(Role.USER);

        String userResponse = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        UserDto savedUser = objectMapper.readValue(userResponse, UserDto.class);

        GenreDto genreDto = new GenreDto();
        genreDto.setName("Thriller");

        String genreResponse = mockMvc.perform(post("/genres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(genreDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        GenreDto savedGenre = objectMapper.readValue(genreResponse, GenreDto.class);

        MovieDto movieDto = new MovieDto();
        movieDto.setTitle("Scary Movie");
        movieDto.setDuration("01:45:00");
        movieDto.setReleaseDate(LocalDate.parse("2025-05-01"));
        movieDto.setRating(BigDecimal.valueOf(7.2));
        movieDto.setGenreId(savedGenre.getId());

        String movieResponse = mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        MovieDto savedMovie = objectMapper.readValue(movieResponse, MovieDto.class);

        TheaterDto theaterDto = new TheaterDto();
        theaterDto.setName("Ticket Theater");
        theaterDto.setLocation("City Center");

        String theaterResponse = mockMvc.perform(post("/theaters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(theaterDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        TheaterDto savedTheater = objectMapper.readValue(theaterResponse, TheaterDto.class);

        HallDto hallDto = new HallDto();
        hallDto.setName("Ticket Hall");

        String hallResponse = mockMvc.perform(post("/theaters/{theaterId}/halls", savedTheater.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(hallDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        HallDto savedHall = objectMapper.readValue(hallResponse, HallDto.class);

        SeatDto seatDto = new SeatDto();
        seatDto.setRowNumber(1);
        seatDto.setSeatNumber(1);

        String seatResponse = mockMvc.perform(post("/theaters/{theaterId}/halls/{hallId}/seats", savedTheater.getId(), savedHall.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(seatDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        SeatDto savedSeat = objectMapper.readValue(seatResponse, SeatDto.class);

        ShowDto showDto = new ShowDto();
        showDto.setShowTime(LocalDateTime.now().plusDays(1));
        showDto.setPrice(BigDecimal.valueOf(1800));
        showDto.setHallId(savedHall.getId());

        String showResponse = mockMvc.perform(post("/movies/{movieId}/shows", savedMovie.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(showDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        ShowDto savedShow = objectMapper.readValue(showResponse, ShowDto.class);

        TicketDto ticketDto = new TicketDto();
        ticketDto.setShowId(savedShow.getId());
        ticketDto.setSeatId(savedSeat.getId());

        String ticketResponse = mockMvc.perform(post("/users/{userId}/tickets", savedUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ticketDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.showId").value(savedShow.getId()))
                .andExpect(jsonPath("$.seatId").value(savedSeat.getId()))
                .andReturn().getResponse().getContentAsString();

        TicketDto savedTicket = objectMapper.readValue(ticketResponse, TicketDto.class);

        mockMvc.perform(get("/users/{userId}/tickets/{id}", savedUser.getId(), savedTicket.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedTicket.getId()));
    }

    @Test
    void testCancelTicket() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setName("CancelUser");
        userDto.setEmail("canceluser@example.com");
        userDto.setPassword("cancel123");
        userDto.setRole(Role.USER);

        String userResponse = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        UserDto savedUser = objectMapper.readValue(userResponse, UserDto.class);

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
        movieDto.setDuration("02:00:00");
        movieDto.setReleaseDate(LocalDate.parse("2025-06-01"));
        movieDto.setRating(BigDecimal.valueOf(8.5));
        movieDto.setGenreId(savedGenre.getId());

        String movieResponse = mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        MovieDto savedMovie = objectMapper.readValue(movieResponse, MovieDto.class);

        TheaterDto theaterDto = new TheaterDto();
        theaterDto.setName("Cancel Theater");
        theaterDto.setLocation("Downtown");

        String theaterResponse = mockMvc.perform(post("/theaters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(theaterDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        TheaterDto savedTheater = objectMapper.readValue(theaterResponse, TheaterDto.class);

        HallDto hallDto = new HallDto();
        hallDto.setName("Cancel Hall");

        String hallResponse = mockMvc.perform(post("/theaters/{theaterId}/halls", savedTheater.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(hallDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        HallDto savedHall = objectMapper.readValue(hallResponse, HallDto.class);

        SeatDto seatDto = new SeatDto();
        seatDto.setRowNumber(1);
        seatDto.setSeatNumber(5);

        String seatResponse = mockMvc.perform(post("/theaters/{theaterId}/halls/{hallId}/seats", savedTheater.getId(), savedHall.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(seatDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        SeatDto savedSeat = objectMapper.readValue(seatResponse, SeatDto.class);

        ShowDto showDto = new ShowDto();
        showDto.setShowTime(LocalDateTime.now().plusDays(2));
        showDto.setPrice(BigDecimal.valueOf(2000));
        showDto.setHallId(savedHall.getId());

        String showResponse = mockMvc.perform(post("/movies/{movieId}/shows", savedMovie.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(showDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        ShowDto savedShow = objectMapper.readValue(showResponse, ShowDto.class);

        TicketDto ticketDto = new TicketDto();
        ticketDto.setShowId(savedShow.getId());
        ticketDto.setSeatId(savedSeat.getId());

        String ticketResponse = mockMvc.perform(post("/users/{userId}/tickets", savedUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ticketDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        TicketDto savedTicket = objectMapper.readValue(ticketResponse, TicketDto.class);

        mockMvc.perform(delete("/users/{userId}/tickets/{id}", savedUser.getId(), savedTicket.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/users/{userId}/tickets/{id}", savedUser.getId(), savedTicket.getId()))
                .andExpect(status().isNotFound());
    }

}
