package ru.practicum.ewm.error.exception;

public class RequestUpdateRestrictedException extends RuntimeException {
    public RequestUpdateRestrictedException(String message) {
        super(message);
    }
}
