package ru.yandex.practicum.filmorate.model;


import lombok.Getter;

import java.util.Map;

@Getter
public class Friendship implements Mapable {
    private final Long userId;
    private final Long friendId;
    private final boolean confirmed;

    public Friendship(Long userId, Long friendId, boolean confirmed) {
        this.userId = userId;
        this.friendId = friendId;
        this.confirmed = confirmed;
    }

    @Override
    public Map<String, Object> toMap() {
        return Map.of("user_id", userId, "friend_id", friendId, "confirmed", confirmed);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Friendship)) return false;

        Friendship that = (Friendship) o;

        if (confirmed != that.confirmed) return false;
        if (!userId.equals(that.userId)) return false;
        return friendId.equals(that.friendId);
    }

    @Override
    public int hashCode() {
        int result = userId.hashCode();
        result = 31 * result + friendId.hashCode();
        result = 31 * result + (confirmed ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Friendship{" +
                "userId=" + userId +
                ", friendId=" + friendId +
                ", approved=" + confirmed +
                '}';
    }
}
