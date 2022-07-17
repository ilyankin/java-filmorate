package ru.yandex.practicum.filmorate.model;

import lombok.Getter;

import java.util.Map;

@Getter
public class FilmGenre implements Mapable {
    private final Long filmId;
    private final Integer genreId;

    public FilmGenre(Long filmId, Integer genreId) {
        this.filmId = filmId;
        this.genreId = genreId;
    }

    @Override
    public Map<String, Object> toMap() {
        return Map.of("film_id", filmId, "genre_id", genreId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FilmGenre)) return false;

        FilmGenre filmGenre = (FilmGenre) o;

        if (!filmId.equals(filmGenre.filmId)) return false;
        return genreId.equals(filmGenre.genreId);
    }

    @Override
    public int hashCode() {
        int result = filmId.hashCode();
        result = 31 * result + genreId.hashCode();
        return result;
    }
}
