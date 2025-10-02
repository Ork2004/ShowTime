package com.zhumagulorken.cinema.showtime.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhumagulorken.cinema.showtime.dto.HallDto;
import com.zhumagulorken.cinema.showtime.dto.TheaterDto;
import com.zhumagulorken.cinema.showtime.repository.HallRepository;
import com.zhumagulorken.cinema.showtime.repository.TheaterRepository;
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
class HallControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TheaterRepository theaterRepository;

    @Autowired
    private HallRepository hallRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateAndGetHall() throws Exception {
        TheaterDto theaterDto = new TheaterDto();
        theaterDto.setName("Grand Cinema");
        theaterDto.setLocation("Center");

        String theaterResponse = mockMvc.perform(post("/theaters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(theaterDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        TheaterDto savedTheater = objectMapper.readValue(theaterResponse, TheaterDto.class);

        HallDto hallDto = new HallDto();
        hallDto.setName("Hall 1");

        mockMvc.perform(post("/theaters/{theaterId}/halls", savedTheater.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(hallDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Hall 1"))
                .andExpect(jsonPath("$.theaterId").value(savedTheater.getId()));

        mockMvc.perform(get("/theaters/{theaterId}/halls", savedTheater.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Hall 1"));
    }

    @Test
    void testDeleteHall() throws Exception {
        TheaterDto theaterDto = new TheaterDto();
        theaterDto.setName("IMAX Cinema");
        theaterDto.setLocation("North");

        String theaterResponse = mockMvc.perform(post("/theaters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(theaterDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        TheaterDto savedTheater = objectMapper.readValue(theaterResponse, TheaterDto.class);

        HallDto hallDto = new HallDto();
        hallDto.setName("Hall 2");

        String hallResponse = mockMvc.perform(post("/theaters/{theaterId}/halls", savedTheater.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(hallDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        HallDto savedHall = objectMapper.readValue(hallResponse, HallDto.class);

        mockMvc.perform(delete("/theaters/{theaterId}/halls/{id}", savedTheater.getId(), savedHall.getId()))
                .andExpect(status().isNoContent());
    }
}
