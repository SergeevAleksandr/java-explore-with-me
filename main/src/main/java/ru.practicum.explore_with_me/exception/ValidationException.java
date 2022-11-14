package ru.practicum.explore_with_me.exception;

public class ValidationException extends IllegalStateException {
    public ValidationException(final String message) {
        super(message);
    }
}
