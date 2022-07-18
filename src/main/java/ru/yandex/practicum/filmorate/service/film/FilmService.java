package ru.yandex.practicum.filmorate.service.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ResourceNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.filmgenre.FilmGenreDbStorage;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Service
public class FilmService {
    private final FilmDbStorage filmStorage;
    private final FilmGenreDbStorage filmGenreStorage;

    @Autowired
    public FilmService(FilmDbStorage filmStorage, FilmGenreDbStorage filmGenreStorage) {
        this.filmStorage = filmStorage;
        this.filmGenreStorage = filmGenreStorage;
    }

    public Film find(Long filmId) {
        Film film = filmStorage.findById(filmId).orElseThrow(() -> new ResourceNotFoundException("film", "id", filmId));
        film.setGenres(filmGenreStorage.findFilmGenres(filmId));
        return film;
    }

    public Collection<Film> findAll() {
        Collection<Film> films = filmStorage.findAll();
        films.forEach(film -> film.setGenres(filmGenreStorage.findFilmGenres(film.getId())));
        return films;
    }

    public Collection<Film> findTopFilmsByLikes(int count) {
        Collection<Film> topFilms = filmStorage.findTopFilmsByLikes(count);
        topFilms.forEach(film -> film.setGenres(filmGenreStorage.findFilmGenres(film.getId())));
        return topFilms;
    }

    public Film create(Film film) {
        Objects.requireNonNull(film, "film must not be null");
        film.setId(filmStorage.save(film));
        saveFilmGenres(film);
        film.setGenres(filmGenreStorage.findFilmGenres(film.getId()));
        return film;
    }

    public Film update(Film film) {
        Objects.requireNonNull(film, "film must not be null");
        Film updatedFilm = filmStorage.update(film).orElseThrow(() ->
                new ResourceNotFoundException("film", "id", film.getId()));
        if (film.getGenres() != null) {
            filmGenreStorage.deleteByFilmId(film.getId());
            saveFilmGenres(film);
        }
        film.setGenres(filmGenreStorage.findFilmGenres(film.getId()));
        return updatedFilm;
    }

    public boolean delete(Long filmId) {
        return filmStorage.delete(filmId);
    }


    private void saveFilmGenres(Film film) {
        Set<Genre> genres = film.getGenres();
        if (genres == null) return;
        for (Genre genre : genres) {
            filmGenreStorage.save(new FilmGenre(film.getId(), genre.getId()));
        }
    }
}
