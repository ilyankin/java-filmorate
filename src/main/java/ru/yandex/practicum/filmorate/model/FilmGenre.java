package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Map;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class FilmGenre implements Mapable {
    private final Long filmId;
    private final Integer genreId;

    @Override
    public Map<String, Object> toMap() {
        return Map.of("film_id", filmId, "genre_id", genreId);
    }

    @Override
    public String toString() {
        return "FilmGenre{" +
                "filmId=" + filmId +
                ", genreId=" + genreId +
                '}';
    }
}
