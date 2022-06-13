package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

public class FilmService {
    private final FilmStorage<Film> filmStorage;

    @Autowired
    public FilmService(FilmStorage<Film> filmStorage) {
        this.filmStorage = filmStorage;
    }



}
