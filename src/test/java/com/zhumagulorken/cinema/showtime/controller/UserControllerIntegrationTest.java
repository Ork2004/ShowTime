package com.zhumagulorken.cinema.showtime.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhumagulorken.cinema.showtime.dto.UserDto;
import com.zhumagulorken.cinema.showtime.enums.Role;
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
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateAndGetUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setName("David");
        userDto.setEmail("david@example.com");
        userDto.setPassword("secure123");
        userDto.setRole(Role.USER);

        String userResponse = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("David"))
                .andReturn().getResponse().getContentAsString();

        UserDto savedUser = objectMapper.readValue(userResponse, UserDto.class);

        mockMvc.perform(get("/users/{id}", savedUser.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("david@example.com"));
    }

    @Test
    void testDeleteUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setName("Eve");
        userDto.setEmail("eve@example.com");
        userDto.setPassword("123456");
        userDto.setRole(Role.ADMIN);

        String userResponse = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        UserDto savedUser = objectMapper.readValue(userResponse, UserDto.class);

        mockMvc.perform(delete("/users/{id}", savedUser.getId()))
                .andExpect(status().isNoContent());
    }
}
