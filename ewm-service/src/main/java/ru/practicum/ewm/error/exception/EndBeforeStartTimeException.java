package ru.practicum.ewm.error.exception;

public class EndBeforeStartTimeException extends RuntimeException {
    public EndBeforeStartTimeException(String message) {
        super(message);
    }
}
