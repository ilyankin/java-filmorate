package ru.yandex.practicum.filmorate.exception;

public class UserLoginAlreadyTakenException extends EntityAlreadyTakenException {
    public <T> UserLoginAlreadyTakenException(T valueName) {
        super("User", "login", valueName);
    }
}
