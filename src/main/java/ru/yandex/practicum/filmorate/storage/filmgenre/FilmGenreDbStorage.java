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

    private final static String FILM_GENRES_SELECT_SQL_QUERY =
            "SELECT * FROM FILM_GENRES WHERE (film_id = ? AND genre_id = ?)";
    private final static String FILM_GENRES_INTO_SQL_QUERY =
            "MERGE INTO FILM_GENRES (film_id, genre_id) KEY (film_id, genre_id) VALUES (?, ?)";
    private final static String FILM_GENRES_DELETE_SQL_QUERY =
            "DELETE FROM FILM_GENRES WHERE (film_id = ? AND genre_id = ?)";
    private final static String FILM_GENRES_DELETE_BY_FILM_ID_SQL_QUERY = "DELETE FROM FILM_GENRES WHERE film_id = ?";
    private final static String FILM_GENRES_SELECT_GENRES_BY_FILM_SQL_QUERY =
            "SELECT fg.genre_id AS id, name FROM FILM_GENRES AS fg"
                    + " JOIN GENRES AS g ON fg.genre_id = g.id AND fg.film_id = ? ORDER BY genre_id";

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<FilmGenre> filmGenreRowMapper;
    private final RowMapper<Genre> genreRowMapper;

    @Autowired
    public FilmGenreDbStorage(JdbcTemplate jdbcTemplate, FilmGenreRowMapper filmGenreRowMapper,
                              GenreRowMapper genreRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.filmGenreRowMapper = filmGenreRowMapper;
        this.genreRowMapper = genreRowMapper;
    }

    @Override
    public Optional<FilmGenre> find(Long film_id, Long genre_id) {
        try (Stream<FilmGenre> stream = jdbcTemplate.queryForStream(FILM_GENRES_SELECT_SQL_QUERY, filmGenreRowMapper,
                film_id, genre_id)) {
            return stream.findAny();
        }
    }

    @Override
    public void save(FilmGenre fg) {
        Objects.requireNonNull(fg, "fg must not be null");
        jdbcTemplate.update(FILM_GENRES_INTO_SQL_QUERY, fg.getFilmId(), fg.getGenreId());
    }

    @Override
    public boolean delete(FilmGenre fg) {
        Objects.requireNonNull(fg, "fg must not be null");
        return jdbcTemplate.update(FILM_GENRES_DELETE_SQL_QUERY, fg.getFilmId(), fg.getGenreId()) == 1;
    }

    public boolean deleteByFilmId(Long filmId) {
        return jdbcTemplate.update(FILM_GENRES_DELETE_BY_FILM_ID_SQL_QUERY, filmId) > 0;
    }

    public Set<Genre> findFilmGenres(Long filmId) {
        return new LinkedHashSet<>(
                jdbcTemplate.query(FILM_GENRES_SELECT_GENRES_BY_FILM_SQL_QUERY, genreRowMapper, filmId)
        );
    }
}
