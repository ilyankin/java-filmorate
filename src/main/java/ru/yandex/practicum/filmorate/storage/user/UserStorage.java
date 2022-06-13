package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.Storage;

public interface UserStorage<T extends User> extends Storage<T>, Friendable<Long> {

}
