package ru.yandex.practicum.filmorate.exception;

public class NotFoundByIdException extends RuntimeException {

    public NotFoundByIdException(String entity, long id) {
        this("Couldn't find " + entity + " by id = " + id);
    }

    private NotFoundByIdException(String message) {
        super(message);
    }


}
