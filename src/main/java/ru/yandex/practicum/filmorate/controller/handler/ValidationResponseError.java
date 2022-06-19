package ru.yandex.practicum.filmorate.controller.handler;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
class ValidationResponseError extends SubResponseError {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    ValidationResponseError(String object, String message) {
        this.object = object;
        this.message = message;
    }
}
