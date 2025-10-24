package com.zhumagulorken.cinema.showtime.dto;

import jakarta.validation.constraints.Min;

public class SeatDto {
    private Long id;

    @Min(value = 1, message = "Row number must be at least 1")
    private int rowNumber;

    @Min(value = 1, message = "Seat number must be at least 1")
    private int seatNumber;

    private Long hallId;

    private boolean booked;

    //Getter and Setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Long getHallId() {
        return hallId;
    }

    public void setHallId(Long hallId) {
        this.hallId = hallId;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }
}
