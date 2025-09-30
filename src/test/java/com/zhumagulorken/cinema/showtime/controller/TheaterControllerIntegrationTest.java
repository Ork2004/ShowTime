package com.zhumagulorken.cinema.showtime.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhumagulorken.cinema.showtime.dto.TheaterDto;
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
class TheaterControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TheaterRepository theaterRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateAndGetTheater() throws Exception {
        TheaterDto dto = new TheaterDto();
        dto.setName("Cinemax");
        dto.setLocation("Downtown");

        mockMvc.perform(post("/theaters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Cinemax"))
                .andExpect(jsonPath("$.location").value("Downtown"));

        mockMvc.perform(get("/theaters"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Cinemax"));
    }

    @Test
    void testDeleteTheater() throws Exception {
        TheaterDto dto = new TheaterDto();
        dto.setName("IMAX");
        dto.setLocation("Central Park");

        String response = mockMvc.perform(post("/theaters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        TheaterDto saved = objectMapper.readValue(response, TheaterDto.class);

        mockMvc.perform(delete("/theaters/{id}", saved.getId()))
                .andExpect(status().isNoContent());
    }
}
