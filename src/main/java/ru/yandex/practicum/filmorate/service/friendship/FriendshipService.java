package ru.yandex.practicum.filmorate.service.friendship;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ResourceNotFoundException;
import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.storage.friendship.FriendshipDbStorage;

import java.util.Map;
import java.util.Objects;

@Service
public class FriendshipService {
    private final FriendshipDbStorage friendshipStorage;

    @Autowired
    public FriendshipService(FriendshipDbStorage friendshipStorage) {
        this.friendshipStorage = friendshipStorage;
    }

    public Friendship find(Long userId, Long friendId) {
        return friendshipStorage.find(userId, friendId).orElseThrow(() ->
                new ResourceNotFoundException("friendship", Map.of("userId", userId, "friendId", friendId)));
    }

    public void save(Friendship friendship) {
        Objects.requireNonNull(friendship, "friendship must not be null");
        friendshipStorage.save(friendship);
    }

    public boolean delete(Friendship friendship) {
        Objects.requireNonNull(friendship, "friendship must not be null");
        return friendshipStorage.delete(friendship);
    }

}
