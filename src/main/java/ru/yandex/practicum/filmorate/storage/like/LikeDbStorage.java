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
    private final static String LIKE_SELECT_SQL_QUERY =
            "SELECT * FROM FILM_LIKES WHERE user_id = ? AND film_id = ?";
    private final static String LIKE_INSERT_SQL_QUERY =
            "MERGE INTO FILM_LIKES (film_id, user_id) KEY (film_id, user_id) VALUES (?, ?)";
    private final static String LIKE_DELETE_SQL_QUERY =
            "DELETE FROM FILM_LIKES WHERE film_id = ? AND user_id = ?";
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Like> likeRowMapper;

    @Autowired
    public LikeDbStorage(JdbcTemplate jdbcTemplate, LikeRowMapper likeRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.likeRowMapper = likeRowMapper;
    }

    @Override
    public Optional<Like> find(Long user_id, Long film_id) {
        try (Stream<Like> stream =
                     jdbcTemplate.queryForStream(LIKE_SELECT_SQL_QUERY, likeRowMapper, user_id, film_id)) {
            return stream.findAny();
        }
    }

    @Override
    public void save(Like like) {
        Objects.requireNonNull(like, "like must not be null");
        jdbcTemplate.update(LIKE_INSERT_SQL_QUERY, like.getFilmId(), like.getUserId());
    }

    @Override
    public boolean delete(Like like) {
        return jdbcTemplate.update(LIKE_DELETE_SQL_QUERY, like.getFilmId(), like.getUserId()) == 1;
    }
}
