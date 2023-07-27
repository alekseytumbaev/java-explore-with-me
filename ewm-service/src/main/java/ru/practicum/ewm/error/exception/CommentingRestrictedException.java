package ru.practicum.ewm.error.exception;

public class CommentingRestrictedException extends RuntimeException {
    public CommentingRestrictedException(String message) {
        super(message);
    }
}
