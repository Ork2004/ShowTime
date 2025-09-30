package com.zhumagulorken.cinema.showtime.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhumagulorken.cinema.showtime.dto.GenreDto;
import com.zhumagulorken.cinema.showtime.repository.GenreRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest()
@AutoConfigureMockMvc(addFilters = false)
class GenreControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateAndGetGenres() throws Exception {
        GenreDto genreDto = new GenreDto();
        genreDto.setName("Thriller");

        mockMvc.perform(post("/genres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(genreDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Thriller"))
                .andExpect(jsonPath("$.id").isNumber());

        mockMvc.perform(get("/genres"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Thriller"));
    }

    @Test
    void testDeleteGenre() throws Exception {
        GenreDto genreDto = new GenreDto();
        genreDto.setName("Mystery");

        String response = mockMvc.perform(post("/genres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(genreDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        GenreDto created = objectMapper.readValue(response, GenreDto.class);

        mockMvc.perform(delete("/genres/{id}", created.getId()))
                .andExpect(status().isNoContent());
    }
}
