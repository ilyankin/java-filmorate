package ru.yandex.practicum.filmorate.model;

import lombok.Getter;

import java.util.Map;

@Getter
public class Like implements Mapable {
    private final Long userId;
    private final Long filmId;

    public Like(Long userId, Long filmId) {
        this.userId = userId;
        this.filmId = filmId;
    }

    @Override
    public Map<String, Object> toMap() {
        return Map.of("user_id", userId, "film_id", filmId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Like)) return false;

        Like like = (Like) o;

        if (!userId.equals(like.userId)) return false;
        return filmId.equals(like.filmId);
    }

    @Override
    public int hashCode() {
        int result = userId.hashCode();
        result = 31 * result + filmId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Like{" +
                "userId=" + userId +
                ", filmId=" + filmId +
                '}';
    }
}
