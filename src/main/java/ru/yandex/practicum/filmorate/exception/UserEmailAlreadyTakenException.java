package ru.yandex.practicum.filmorate.exception;

public class UserEmailAlreadyTakenException extends EntityAlreadyTakenException {
    public <T> UserEmailAlreadyTakenException(T valueName) {
        super("User", "email", valueName);
    }
}
