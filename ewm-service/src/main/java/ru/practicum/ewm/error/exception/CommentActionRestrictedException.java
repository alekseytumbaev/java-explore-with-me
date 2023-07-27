package ru.practicum.ewm.error.exception;

public class CommentActionRestrictedException extends RuntimeException {
    public CommentActionRestrictedException(String message) {
        super(message);
    }
}
