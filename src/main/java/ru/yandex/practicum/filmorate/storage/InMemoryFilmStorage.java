package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import ru.yandex.practicum.filmorate.exception.NotFoundByIdException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage<Film> {

    private long id;

    private final Map<Long, Film> filmMap = new HashMap<>();

    @Override
    public Film find(long id) {
        return filmMap.get(id);
    }

    @Override
    public Collection<Film> findAll() {
        return filmMap.values();
    }

    @Override
    public Film create(Film film) {
        long id = generateId();
        film.setId(id);
        filmMap.put(id, film);
        return film;
    }

    @Override
    public Film update(Film film) {
        long id = film.getId();

        if (filmMap.get(id) == null) throw new NotFoundByIdException("film", id);

        filmMap.put(id, film);
        return film;
    }

    @Override
    public Film delete(long id) {
        return filmMap.remove(id);
    }

    @Override
    public void deleteAll() {
        filmMap.clear();
    }

    @GetMapping
    public Collection<Film> getAll() {
        return filmMap.values();
    }

    private long generateId() {
        return ++id;
    }
}
