package com.zhumagulorken.cinema.showtime.exсeption;

public class SeatAlreadyBookedException extends RuntimeException {
    public SeatAlreadyBookedException(Long showId, Long seatId) {
        super("Seat with id" + seatId + " is already booked for show with id" + showId);
    }
}
