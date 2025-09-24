package com.zhumagulorken.cinema.showtime.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class TicketDto {
    private Long id;

    @NotNull(message = "Show ID is required")
    private Long showId;

    @NotNull(message = "Seat ID is required")
    private Long seatId;

    private LocalDateTime bookedAt;

    //Getter and Setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShowId() {
        return showId;
    }

    public void setShowId(Long showId) {
        this.showId = showId;
    }

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    public LocalDateTime getBookedAt() {
        return bookedAt;
    }

    public void setBookedAt(LocalDateTime bookedAt) {
        this.bookedAt = bookedAt;
    }
}
