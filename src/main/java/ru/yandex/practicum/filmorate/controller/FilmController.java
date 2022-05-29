package ru.yandex.practicum.filmorate.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundByIdException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private long id;
    private final Map<Long, Film> filmMap = new HashMap<>();

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        long id = generateId();
        film.setId(id);
        filmMap.put(id, film);
        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        long id = film.getId();

        if (filmMap.get(id) == null) throw new NotFoundByIdException("film", id);

        filmMap.put(id, film);
        return film;
    }

    @GetMapping
    public Collection<Film> getAll() {
        return filmMap.values();
    }

    private long generateId() {
        return ++id;
    }
}
