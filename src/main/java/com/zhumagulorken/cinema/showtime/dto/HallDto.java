package com.zhumagulorken.cinema.showtime.dto;

import jakarta.validation.constraints.NotBlank;

public class HallDto {
    private Long id;

    @NotBlank(message = "Hall name is required")
    private String name;

    private Long theaterId;

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

    public Long getTheaterId() {
        return theaterId;
    }

    public void setTheaterId(Long theaterId) {
        this.theaterId = theaterId;
    }
}
