package ru.practicum.later.exception.exp;

import lombok.Getter;

@Getter
public class NotReceivedItemException extends RuntimeException {
    private final String message;

    public NotReceivedItemException(String message) {
        super(message);
        this.message = message;
    }
}
