package ru.yandex.practicum.filmorate.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.Storage;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final Storage<Film> filmStorage;

    @Autowired
    public FilmController(InMemoryFilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        return filmStorage.create(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        return filmStorage.update(film);
    }

    @GetMapping
    public Collection<Film> getAll() {
        return filmStorage.findAll();
    }
}
