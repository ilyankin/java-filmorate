package ru.yandex.practicum.filmorate.util;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ru.yandex.practicum.filmorate.exception.ResourceNotFoundException;
import ru.yandex.practicum.filmorate.model.BaseEntity;
import ru.yandex.practicum.filmorate.model.Mapable;
import ru.yandex.practicum.filmorate.storage.Storage;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class DbUtil {

    private DbUtil() {
    }

    public static <T, K> Optional<T> find(JdbcTemplate jdbcTemplate,
                                          RowMapper<T> rowMapper,
                                          String tableName,
                                          String fieldName,
                                          K value) {
        String selectSqlQuery = "SELECT * FROM " + tableName + " WHERE " + fieldName + " = ?";
        try (Stream<T> stream = jdbcTemplate.queryForStream(selectSqlQuery, rowMapper, value)) {
            return stream.findAny();
        }
    }

    public static <T> Collection<T> findAll(JdbcTemplate jdbcTemplate,
                                            RowMapper<T> rowMapper,
                                            String tableName) {
        String selectSqlQuery = "SELECT * FROM " + tableName;
        try (Stream<T> stream = jdbcTemplate.queryForStream(selectSqlQuery, rowMapper)) {
            return stream.collect(Collectors.toList());
        }
    }

    public static <T extends Mapable> Number save(SimpleJdbcInsert simpleJdbcInsert, T value) {
        return simpleJdbcInsert.executeAndReturnKey(value.toMap());
    }

    public static <K> boolean delete(JdbcTemplate jdbcTemplate,
                                     String tableName,
                                     String fieldName,
                                     K value) {
        return jdbcTemplate.update("DELETE FROM " + tableName + " WHERE " + fieldName + " = ?", value) == 1;
    }

    public static void checkUsersExist(UserDbStorage storage, Long... userIds) {
        checkEntityExists(storage, "user", "id", userIds);
    }

    public static void checkFilmsExist(FilmDbStorage storage, Long... filmIds) {
        checkEntityExists(storage, "film", "id", filmIds);
    }

    @SafeVarargs
    public static <EntityType extends BaseEntity<idType>, idType> void checkEntityExists(
            Storage<EntityType, idType> storage, String entityName, String fieldName, idType... userIds) {
        for (idType entityId : userIds) {
            storage.findById(entityId).orElseThrow(() -> new ResourceNotFoundException(entityName, fieldName, entityId));
        }
    }
}
