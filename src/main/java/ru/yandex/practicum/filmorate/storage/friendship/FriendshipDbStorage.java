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

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Friendship> friendshipRowMapper;

    @Autowired
    public FriendshipDbStorage(JdbcTemplate jdbcTemplate, FriendshipRowMapper friendshipRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.friendshipRowMapper = friendshipRowMapper;
    }

    @Override
    public Optional<Friendship> find(Long userId, Long friendId) {
        String selectSqlQuery = "SELECT * FROM " + FRIENDSHIPS
                + " WHERE (" + USER_ID + " = ?" + " AND " + FRIEND_ID + " = ?)"
                + " OR (" + FRIEND_ID + " = ?" + " AND " + USER_ID + " = ?)";
        try (Stream<Friendship> stream = jdbcTemplate.queryForStream(selectSqlQuery, friendshipRowMapper,
                userId, friendId, userId, friendId)) {
            return stream.findAny();
        }
    }

    @Override
    public void save(Friendship friendship) {
        String intoSqlQuery = "MERGE INTO " + FRIENDSHIPS + " (" + USER_ID
                + ", " + FRIEND_ID + ", confirmed) "
                + "KEY (" + USER_ID + ", " + FRIEND_ID + ") VALUES (?, ?, ?)";
        jdbcTemplate.update(intoSqlQuery, friendship.getUserId(), friendship.getFriendId(), friendship.isConfirmed());
    }

    @Override
    public boolean delete(Friendship friendship) {
        String deleteSqlQuery = "DELETE FROM " + FRIENDSHIPS + " WHERE "
                + USER_ID + " = ? " + " AND " + FRIEND_ID + " = ?";
        return jdbcTemplate.update(deleteSqlQuery, friendship.getUserId(), friendship.getFriendId()) == 1;
    }
}
