package ru.practicum.later.expception.exp;

import lombok.Getter;

@Getter
public class NotReturnItemException extends RuntimeException {
    private final String message;

    public NotReturnItemException(String message) {
        super(message);
        this.message = message;
    }
}
