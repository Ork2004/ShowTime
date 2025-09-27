package com.zhumagulorken.cinema.showtime.exсeption;

public class NotFoundException extends RuntimeException {
    public NotFoundException(Class<?> entityClass, Long id) {
        super(entityClass.getSimpleName() + " not found with id " + id);
    }
}
