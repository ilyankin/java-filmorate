package ru.yandex.practicum.filmorate.storage;

import java.util.Collection;

public interface Storage<T> {
    T find(long id);

    Collection<T> findAll();

    T create(T value);

    T update(T value);

    T delete(long id);

    void deleteAll();
}
