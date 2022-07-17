package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.Storage;
import ru.yandex.practicum.filmorate.storage.rowmaper.FilmRowMapper;
import ru.yandex.practicum.filmorate.util.DbUtil;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@Repository
public class FilmDbStorage implements Storage<Film, Long> {
    private final static String FILMS = "FILMS";
    private final static String FILM_ID = "id";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final RowMapper<Film> filmRowMapper;

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate, FilmRowMapper filmRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(FILMS).usingGeneratedKeyColumns(FILM_ID);
        this.filmRowMapper = filmRowMapper;
    }

    @Override
    public Optional<Film> findById(Long id) {
        return DbUtil.find(jdbcTemplate, filmRowMapper, FILMS, FILM_ID, id);
    }

    @Override
    public Collection<Film> findAll() {
        return DbUtil.findAll(jdbcTemplate, filmRowMapper, FILMS);
    }

    public Collection<Film> findTopFilmsByLikes(int count) {
        String sql = "SELECT * FROM " + FILMS + " AS f"
                + " LEFT JOIN FILM_LIKES AS fl on f.id = fl.film_id"
                + " GROUP BY f.id"
                + " ORDER BY COUNT(DISTINCT fl.user_id) DESC"
                + " LIMIT ?";
        return jdbcTemplate.query(sql, filmRowMapper, count);
    }

    @Override
    public Long save(Film film) {
        Objects.requireNonNull(film, "film must not be null");
        return (Long) DbUtil.save(simpleJdbcInsert, film);
    }

    @Override
    public Optional<Film> update(Film film) {
        Objects.requireNonNull(film, "user must not be null");
        Optional<Film> oldFilm = findById(film.getId());
        if (oldFilm.isEmpty()) return Optional.empty();

        String updateSqlQuery = "UPDATE " + FILMS + " SET" +
                " name = ?, description = ?, release_date = ?, duration = ?, rate = ?, mpa = ?" +
                " WHERE " + FILM_ID + " = ?";
        jdbcTemplate.update(updateSqlQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getRate(),
                film.getMpa().getName(),
                film.getId());
        return Optional.of(film);
    }

    @Override
    public boolean delete(Long id) {
        return DbUtil.delete(jdbcTemplate, FILMS, FILM_ID, id);
    }

}
