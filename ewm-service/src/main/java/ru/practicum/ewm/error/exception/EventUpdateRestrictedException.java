package ru.practicum.ewm.error.exception;

public class EventUpdateRestrictedException extends RuntimeException {
    public EventUpdateRestrictedException(String message) {
        super(message);
    }
}
