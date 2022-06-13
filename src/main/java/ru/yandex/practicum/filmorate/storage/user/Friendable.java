package ru.yandex.practicum.filmorate.storage.user;

import java.util.Collection;

public interface Friendable<IdType> {
    Collection<IdType> getIdsFriends(IdType id);

    void addFriend(IdType id, IdType friendId);

    void addFriends(IdType id, IdType... friendId);

    void deleteFriend(IdType id, IdType friendId);

    void deleteFriends(IdType id, IdType... friendId);

    void deleteAllFriends(IdType id);
}
