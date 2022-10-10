package ru.yandex.practicum.filmorate.model;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Map;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Friendship implements Mapable {
    private final Long userId;
    private final Long friendId;
    private final boolean confirmed;

    @Override
    public Map<String, Object> toMap() {
        return Map.of("user_id", userId, "friend_id", friendId, "confirmed", confirmed);
    }

    @Override
    public String toString() {
        return "Friendship{" +
                "userId=" + userId +
                ", friendId=" + friendId +
                ", confirmed=" + confirmed +
                '}';
    }
}
