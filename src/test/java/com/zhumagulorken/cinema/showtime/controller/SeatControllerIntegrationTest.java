package com.zhumagulorken.cinema.showtime.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhumagulorken.cinema.showtime.dto.HallDto;
import com.zhumagulorken.cinema.showtime.dto.SeatDto;
import com.zhumagulorken.cinema.showtime.dto.TheaterDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class SeatControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateAndGetSeat() throws Exception {
        TheaterDto theaterDto = new TheaterDto();
        theaterDto.setName("Cinema World");
        theaterDto.setLocation("Main Street");

        String theaterResponse = mockMvc.perform(post("/theaters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(theaterDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        TheaterDto savedTheater = objectMapper.readValue(theaterResponse, TheaterDto.class);

        HallDto hallDto = new HallDto();
        hallDto.setName("Blue Hall");

        String hallResponse = mockMvc.perform(post("/theaters/{theaterId}/halls", savedTheater.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(hallDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        HallDto savedHall = objectMapper.readValue(hallResponse, HallDto.class);

        SeatDto seatDto = new SeatDto();
        seatDto.setRowNumber(1);
        seatDto.setSeatNumber(10);

        mockMvc.perform(post("/theaters/{theaterId}/halls/{hallId}/seats", savedTheater.getId(), savedHall.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(seatDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rowNumber").value(1))
                .andExpect(jsonPath("$.seatNumber").value(10))
                .andExpect(jsonPath("$.hallId").value(savedHall.getId()));

        mockMvc.perform(get("/theaters/{theaterId}/halls/{hallId}/seats", savedTheater.getId(), savedHall.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].rowNumber").value(1))
                .andExpect(jsonPath("$[0].seatNumber").value(10));
    }

    @Test
    void testDeleteSeat() throws Exception {
        TheaterDto theaterDto = new TheaterDto();
        theaterDto.setName("Delete Cinema");
        theaterDto.setLocation("North Side");

        String theaterResponse = mockMvc.perform(post("/theaters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(theaterDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        TheaterDto savedTheater = objectMapper.readValue(theaterResponse, TheaterDto.class);

        HallDto hallDto = new HallDto();
        hallDto.setName("Hall Delete");

        String hallResponse = mockMvc.perform(post("/theaters/{theaterId}/halls", savedTheater.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(hallDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        HallDto savedHall = objectMapper.readValue(hallResponse, HallDto.class);

        SeatDto seatDto = new SeatDto();
        seatDto.setRowNumber(2);
        seatDto.setSeatNumber(5);

        String seatResponse = mockMvc.perform(post("/theaters/{theaterId}/halls/{hallId}/seats", savedTheater.getId(), savedHall.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(seatDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        SeatDto savedSeat = objectMapper.readValue(seatResponse, SeatDto.class);

        mockMvc.perform(delete("/theaters/{theaterId}/halls/{hallId}/seats/{id}", savedTheater.getId(), savedHall.getId(), savedSeat.getId()))
                .andExpect(status().isOk());
    }
}
