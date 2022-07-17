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

import java.sql.Date;
import java.util.Collection;
import java.util.Optional;

@Repository
public class UserDbStorage implements Storage<User, Long> {
    private final static String USERS = "USERS";
    private final static String USER_ID = "id";

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

        String updateSqlQuery = "UPDATE " + USERS + " SET " +
                "email = ?, login = ?, name = ?, birthday = ?" +
                " WHERE " + USER_ID + " = ?";
        jdbcTemplate.update(updateSqlQuery,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                Date.valueOf(user.getBirthday()),
                user.getId());
        return Optional.of(user);
    }

    @Override
    public boolean delete(Long id) {
        String deleteSqlQuery = "DELETE FROM " + USERS + " WHERE " + USER_ID + " = ?";
        return jdbcTemplate.update(deleteSqlQuery, id) == 1;
    }

    public Collection<User> getUserFriends(Long userId) {
        String selectSqlQuery = "SELECT * FROM " + USERS + " WHERE id IN ("
                + "(SELECT user_id FROM FRIENDSHIPS WHERE friend_id = ? AND confirmed IS TRUE)"
                + " UNION "
                + "(SELECT friend_id FROM FRIENDSHIPS WHERE user_id = ?))";
        return jdbcTemplate.query(selectSqlQuery, userRowMapper, userId, userId);
    }

    public Collection<User> getCommonUserFriends(Long userId, Long otherUserId) {
        String selectSqlQuery = "SELECT * FROM " + USERS + " WHERE id IN ("
                + "(SELECT user_id FROM FRIENDSHIPS WHERE friend_id = ? AND confirmed IS TRUE)"
                + " UNION "
                + "(SELECT friend_id FROM FRIENDSHIPS WHERE user_id = ?))"
                + " AND id IN ("
                + "(SELECT user_id FROM FRIENDSHIPS WHERE friend_id = ? AND confirmed IS TRUE)"
                + " UNION "
                + "(SELECT friend_id FROM FRIENDSHIPS WHERE user_id = ?))";
        return jdbcTemplate.query(selectSqlQuery, userRowMapper, userId, userId, otherUserId, otherUserId);
    }
}
