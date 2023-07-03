package ru.practicum.ewm.stats.error.exception;

public class EndBeforeStartTimeException extends RuntimeException {
    public EndBeforeStartTimeException(String message) {
        super(message);
    }
}
