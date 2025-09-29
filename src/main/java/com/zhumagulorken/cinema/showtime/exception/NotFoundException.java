package com.zhumagulorken.cinema.showtime.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(Class<?> entityClass, Long id) {
        super(entityClass.getSimpleName() + " not found with id " + id);
    }

    public NotFoundException(Class<?> entityClass, String value) {
        super(entityClass.getSimpleName() + " not found with value " + value);
    }
}
