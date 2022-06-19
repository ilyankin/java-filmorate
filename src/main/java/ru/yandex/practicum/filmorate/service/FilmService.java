package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import java.util.Collection;

@Service
public class FilmService extends AbstractService<Film, InMemoryFilmStorage> {

    @Autowired
    public FilmService(InMemoryFilmStorage filmStorage) {
        super(filmStorage);
    }

    public Collection<Film> getPopular(int count) {
        return storage.getTopFilmsByLikes(count);
    }

    public void addLike(Long userId, Long filmId) {
        storage.addLike(userId, filmId);
    }

    public void removeLike(Long userId, Long filmId) {
        storage.removeLike(userId, filmId);
    }

}
