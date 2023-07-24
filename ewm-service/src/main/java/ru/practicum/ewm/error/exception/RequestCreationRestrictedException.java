package ru.practicum.ewm.error.exception;

public class RequestCreationRestrictedException extends RuntimeException {
    public RequestCreationRestrictedException(String message) {
        super(message);
    }
}
