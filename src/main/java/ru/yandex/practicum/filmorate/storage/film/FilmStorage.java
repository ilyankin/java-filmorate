package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.Storage;

public interface FilmStorage<T extends Film> extends Storage<T>, Likable<Long, Long> {
}
