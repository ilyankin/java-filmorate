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
    private static final String SELECT_ALL_FROM = "SELECT * FROM ";
    private static final String DELETE_FROM = "DELETE FROM ";
    private static final String WHERE = " WHERE ";
    private static final String UNDEFINED_PARAMETER = " = ?";

    private DbUtil() {
    }

    private final static String ID = "id";

    public static <T, K> Optional<T> find(JdbcTemplate jdbcTemplate,
                                          RowMapper<T> rowMapper,
                                          String tableName,
                                          String fieldName,
                                          K value) {
        String selectSqlQuery = SELECT_ALL_FROM + tableName + WHERE + fieldName + UNDEFINED_PARAMETER;
        try (Stream<T> stream = jdbcTemplate.queryForStream(selectSqlQuery, rowMapper, value)) {
            return stream.findAny();
        }
    }

    public static <T extends Mapable> Number save(SimpleJdbcInsert simpleJdbcInsert, T value) {
        return simpleJdbcInsert.executeAndReturnKey(value.toMap());
    }

    public static <T> Collection<T> findAll(JdbcTemplate jdbcTemplate,
                                            RowMapper<T> rowMapper,
                                            String tableName) {
        String selectSqlQuery = SELECT_ALL_FROM + tableName;
        try (Stream<T> stream = jdbcTemplate.queryForStream(selectSqlQuery, rowMapper)) {
            return stream.collect(Collectors.toList());
        }
    }

    public static <K> boolean delete(JdbcTemplate jdbcTemplate,
                                     String tableName,
                                     String fieldName,
                                     K value) {
        return jdbcTemplate.update(DELETE_FROM + tableName + WHERE + fieldName + UNDEFINED_PARAMETER, value) == 1;
    }

    public static void checkUsersExist(UserDbStorage storage, Long... userIds) {
        checkEntityExists(storage, "user", ID, userIds);
    }

    public static void checkFilmsExist(FilmDbStorage storage, Long... filmIds) {
        checkEntityExists(storage, "film", ID, filmIds);
    }

    @SafeVarargs
    public static <EntityType extends BaseEntity<idType>, idType> void checkEntityExists(
            Storage<EntityType, idType> storage, String entityName, String fieldName, idType... userIds) {
        for (idType entityId : userIds) {
            storage.findById(entityId).orElseThrow(() -> new ResourceNotFoundException(entityName, fieldName, entityId));
        }
    }
}
