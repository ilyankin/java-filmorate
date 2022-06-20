package ru.yandex.practicum.filmorate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EntityAlreadyTakenException extends RuntimeException {

    public <T> EntityAlreadyTakenException(String sourceName, String fieldName, T valueName) {
        super(String.format("%s with {%s=%s} already taken", sourceName, fieldName, valueName));
    }
}
