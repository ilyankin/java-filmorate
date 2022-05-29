package ru.yandex.practicum.filmorate.exception.validation;

abstract class ValidationException extends RuntimeException {
    protected ValidationException(String message) {
        super(message);
    }
}
