package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.Storage;
import ru.yandex.practicum.filmorate.storage.rowmaper.UserRowMapper;
import ru.yandex.practicum.filmorate.util.DbUtil;

import java.util.Collection;
import java.util.Optional;

@Repository
public class UserDbStorage implements Storage<User, Long> {
    private final static String USERS = "USERS";
    private final static String USER_ID = "id";
    private final static String USER_UPDATE_SQL_QUERY = "UPDATE USERS SET email = ?, login = ?, name = ?, birthday = ? WHERE id = ?";
    private final static String USER_SELECT_FRIENDS_SQL_QUERY = "SELECT * FROM " + USERS + " WHERE id IN ("
            + "(SELECT user_id FROM FRIENDSHIPS WHERE friend_id = ? AND confirmed IS TRUE)"
            + " UNION (SELECT friend_id FROM FRIENDSHIPS WHERE user_id = ?))";
    private final static String USER_SELECT_COMMON_FRIENDS_SQL_QUERY = "SELECT * FROM " + USERS + " WHERE id IN ("
            + "(SELECT user_id FROM FRIENDSHIPS WHERE friend_id = ? AND confirmed IS TRUE) "
            + "UNION (SELECT friend_id FROM FRIENDSHIPS WHERE user_id = ?)) "
            + "AND id IN ((SELECT user_id FROM FRIENDSHIPS WHERE friend_id = ? AND confirmed IS TRUE) "
            + "UNION (SELECT friend_id FROM FRIENDSHIPS WHERE user_id = ?))";


    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final RowMapper<User> userRowMapper;

    @Autowired
    public UserDbStorage(JdbcTemplate jdbcTemplate, UserRowMapper userRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(USERS).usingGeneratedKeyColumns(USER_ID);
        this.userRowMapper = userRowMapper;
    }

    @Override
    public Optional<User> findById(Long id) {
        return findByField("id", id);
    }

    public Optional<User> findByEmail(String email) {
        return findByField("email", email);
    }

    public Optional<User> findByLogin(String login) {
        return findByField("login", login);
    }

    @Override
    public Collection<User> findAll() {
        return DbUtil.findAll(jdbcTemplate, userRowMapper, USERS);
    }

    private Optional<User> findByField(String fieldName, Object fieldValue) {
        return DbUtil.find(jdbcTemplate, userRowMapper, USERS, fieldName, fieldValue);
    }

    @Override
    public Long save(User user) {
        return (Long) DbUtil.save(simpleJdbcInsert, user);
    }

    @Override
    public Optional<User> update(User user) {
        Optional<User> oldUser = findById(user.getId());
        if (oldUser.isEmpty()) return Optional.empty();

        jdbcTemplate.update(USER_UPDATE_SQL_QUERY,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId());
        return Optional.of(user);
    }

    @Override
    public boolean delete(Long id) {
        return DbUtil.delete(jdbcTemplate, USERS, USER_ID, id);
    }

    public Collection<User> getUserFriends(Long userId) {
        return jdbcTemplate.query(USER_SELECT_FRIENDS_SQL_QUERY, userRowMapper, userId, userId);
    }

    public Collection<User> getCommonUserFriends(Long userId, Long otherUserId) {
        return jdbcTemplate.query(USER_SELECT_COMMON_FRIENDS_SQL_QUERY, userRowMapper, userId, userId, otherUserId, otherUserId);
    }
}
