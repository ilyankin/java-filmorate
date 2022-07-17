package ru.yandex.practicum.filmorate.storage.like;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.storage.IntersectStorage;
import ru.yandex.practicum.filmorate.storage.rowmaper.LikeRowMapper;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class LikeDbStorage implements IntersectStorage<Like, Long> {
    private final static String FILM_LIKES = "FILM_LIKES";
    private final static String USER_ID = "user_id";
    private final static String FILM_ID = "film_id";

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Like> likeRowMapper;

    @Autowired
    public LikeDbStorage(JdbcTemplate jdbcTemplate, LikeRowMapper likeRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.likeRowMapper = likeRowMapper;
    }

    @Override
    public Optional<Like> find(Long user_id, Long film_id) {
        String selectSqlQuery = "SELECT * FROM " + FILM_LIKES
                + " WHERE (" + USER_ID + " = ? " + " AND " + FILM_ID + " = ?)";
        try (Stream<Like> stream = jdbcTemplate.queryForStream(selectSqlQuery, likeRowMapper, user_id, film_id)) {
            return stream.findAny();
        }
    }

    @Override
    public void save(Like like) {
        Objects.requireNonNull(like, "like must not be null");
        String intoSqlQuery = "MERGE INTO " + FILM_LIKES + " (" + FILM_ID
                + ", " + USER_ID + ") "
                + "KEY (" + FILM_ID + ", " + USER_ID + ") VALUES (?, ?)";
        jdbcTemplate.update(intoSqlQuery, like.getFilmId(), like.getUserId());
    }

    @Override
    public boolean delete(Like like) {
        Objects.requireNonNull(like, "like must not be null");
        String deleteSqlQuery = "DELETE FROM " + FILM_LIKES + " WHERE "
                + FILM_ID + " = ? " + " AND " + USER_ID + " = ?";
        return jdbcTemplate.update(deleteSqlQuery, like.getFilmId(), like.getUserId()) == 1;
    }
}
