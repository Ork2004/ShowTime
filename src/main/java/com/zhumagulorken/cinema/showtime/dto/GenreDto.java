package com.zhumagulorken.cinema.showtime.dto;

import jakarta.validation.constraints.NotBlank;

public class GenreDto {
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    //Getter and Setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
