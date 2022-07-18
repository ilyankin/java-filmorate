package ru.yandex.practicum.filmorate.storage.friendship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.storage.IntersectStorage;
import ru.yandex.practicum.filmorate.storage.rowmaper.FriendshipRowMapper;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class FriendshipDbStorage implements IntersectStorage<Friendship, Long> {
    private final static String FRIENDSHIPS = "FRIENDSHIPS";
    private final static String USER_ID = "user_id";
    private final static String FRIEND_ID = "friend_id";

    private final static String FRIENDSHIP_SELECT_SQL_QUERY =
            "SELECT * FROM FRIENDSHIPS WHERE user_id = ? AND friend_id = ? OR friend_id = ? AND user_id = ?";
    private final static String FRIENDSHIP_INTO_SQL_QUERY =
            "MERGE INTO FRIENDSHIPS (user_id, friend_id, confirmed) KEY(user_id, friend_id) VALUES (?, ?, ?)";
    private final static String FRIENDSHIP_DELETE_SQL_QUERY =
            "DELETE FROM FRIENDSHIPS WHERE user_id = ? AND friend_id = ?";
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Friendship> friendshipRowMapper;

    @Autowired
    public FriendshipDbStorage(JdbcTemplate jdbcTemplate, FriendshipRowMapper friendshipRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.friendshipRowMapper = friendshipRowMapper;
    }

    @Override
    public Optional<Friendship> find(Long userId, Long friendId) {
        try (Stream<Friendship> stream = jdbcTemplate.queryForStream(FRIENDSHIP_SELECT_SQL_QUERY, friendshipRowMapper,
                userId, friendId, userId, friendId)) {
            return stream.findAny();
        }
    }

    @Override
    public void save(Friendship friendship) {
        jdbcTemplate.update(FRIENDSHIP_INTO_SQL_QUERY, friendship.getUserId(), friendship.getFriendId(),
                friendship.isConfirmed());
    }

    @Override
    public boolean delete(Friendship friendship) {
        return jdbcTemplate.update(FRIENDSHIP_DELETE_SQL_QUERY, friendship.getUserId(), friendship.getFriendId()) == 1;
    }
}
