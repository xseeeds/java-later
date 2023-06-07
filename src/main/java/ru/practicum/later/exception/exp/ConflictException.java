package ru.practicum.later.exception.exp;

import lombok.Getter;

@Getter
public class ConflictException extends RuntimeException {
    private final String message;

    public ConflictException(String message) {
        super(message);
        this.message = message;
    }
}