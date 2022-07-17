package ru.yandex.practicum.filmorate.storage.filmgenre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.IntersectStorage;
import ru.yandex.practicum.filmorate.storage.rowmaper.FilmGenreRowMapper;
import ru.yandex.practicum.filmorate.storage.rowmaper.GenreRowMapper;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Repository
public class FilmGenreDbStorage implements IntersectStorage<FilmGenre, Long> {
    private final static String FILM_GENRES = "FILM_GENRES";
    private final static String FILM_ID = "film_id";
    private final static String GENRE_ID = "genre_id";

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<FilmGenre> filmGenreRowMapper;
    private final RowMapper<Genre> genreRowMapper;

    @Autowired
    public FilmGenreDbStorage(JdbcTemplate jdbcTemplate, FilmGenreRowMapper filmGenreRowMapper, GenreRowMapper genreRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.filmGenreRowMapper = filmGenreRowMapper;
        this.genreRowMapper = genreRowMapper;
    }

    @Override
    public Optional<FilmGenre> find(Long film_id, Long genre_id) {
        String selectSqlQuery = "SELECT * FROM " + FILM_GENRES
                + " WHERE (" + FILM_ID + " = ? " + " AND " + GENRE_ID + " = ?)";
        try (Stream<FilmGenre> stream = jdbcTemplate.queryForStream(selectSqlQuery, filmGenreRowMapper, film_id, genre_id)) {
            return stream.findAny();
        }
    }

    @Override
    public void save(FilmGenre fg) {
        Objects.requireNonNull(fg, "fg must not be null");
        String intoSqlQuery = "MERGE INTO " + FILM_GENRES + " (" + FILM_ID
                + ", " + GENRE_ID + ") "
                + "KEY (" + FILM_ID + ", " + GENRE_ID + ") VALUES (?, ?)";
        jdbcTemplate.update(intoSqlQuery, fg.getFilmId(), fg.getGenreId());
    }

    @Override
    public boolean delete(FilmGenre fg) {
        Objects.requireNonNull(fg, "fg must not be null");
        String deleteSqlQuery = "DELETE FROM " + FILM_GENRES + " WHERE "
                + FILM_ID + " = ? " + " AND " + GENRE_ID + " = ?";
        return jdbcTemplate.update(deleteSqlQuery, fg.getFilmId(), fg.getGenreId()) == 1;
    }

    public boolean deleteByFilmId(Long filmId) {
        String deleteSqlQuery = "DELETE FROM " + FILM_GENRES + " WHERE " + FILM_ID + " = ?";
        return jdbcTemplate.update(deleteSqlQuery, filmId) > 0;
    }

    public Set<Genre> findFilmGenres(Long filmId) {
        String selectSqlQuery = "SELECT fg." + GENRE_ID + " AS id, name FROM " + FILM_GENRES + " AS fg" +
                " JOIN GENRES AS g ON fg." + GENRE_ID + " = g.id AND fg." + FILM_ID + " = ?" +
                " ORDER BY " + GENRE_ID;
        return new LinkedHashSet<>(jdbcTemplate.query(selectSqlQuery, genreRowMapper, filmId));
    }
}
