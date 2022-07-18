package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Map;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Like implements Mapable {
    private final Long userId;
    private final Long filmId;

    @Override
    public Map<String, Object> toMap() {
        return Map.of("user_id", userId, "film_id", filmId);
    }

    @Override
    public String toString() {
        return "Like{" +
                "userId=" + userId +
                ", filmId=" + filmId +
                '}';
    }
}
