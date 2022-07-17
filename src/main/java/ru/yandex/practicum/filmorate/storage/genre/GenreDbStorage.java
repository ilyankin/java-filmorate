package ru.yandex.practicum.filmorate.storage.genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.Storage;
import ru.yandex.practicum.filmorate.storage.rowmaper.GenreRowMapper;
import ru.yandex.practicum.filmorate.util.DbUtil;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@Repository
public class GenreDbStorage implements Storage<Genre, Integer> {
    private final static String GENRES = "GENRES";
    private final static String GENRE_ID = "id";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final RowMapper<Genre> genreRowMapper;

    @Autowired
    public GenreDbStorage(JdbcTemplate jdbcTemplate, GenreRowMapper genreRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(GENRES).usingGeneratedKeyColumns(GENRE_ID);
        this.genreRowMapper = genreRowMapper;
    }

    @Override
    public Optional<Genre> findById(Integer id) {
        return DbUtil.find(jdbcTemplate, genreRowMapper, GENRES, GENRE_ID, id);
    }

    @Override
    public Collection<Genre> findAll() {
        return DbUtil.findAll(jdbcTemplate, genreRowMapper, GENRES);
    }

    @Override
    public Integer save(Genre genre) {

        return (Integer) DbUtil.save(simpleJdbcInsert, genre);
    }

    @Override
    public Optional<Genre> update(Genre genre) {
        Objects.requireNonNull(genre, "genre must not be null");
        Optional<Genre> oldGenre = findById(genre.getId());
        if (oldGenre.isEmpty()) return Optional.empty();

        String updateSqlQuery = "UPDATE + " + GENRES + " SET " +
                " id = ?, name = ?" + " WHERE " + GENRE_ID + " = ?";
        jdbcTemplate.update(updateSqlQuery, genre.toMap());
        return Optional.of(genre);
    }

    @Override
    public boolean delete(Integer id) {
        return DbUtil.delete(jdbcTemplate, GENRES, GENRE_ID, id);
    }
}
