package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundByIdException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage<User> {

    private long id;

    private final Map<Long, User> userMap = new HashMap<>();

    @Override
    public User find(long id) {
        return userMap.get(id);
    }

    @Override
    public Collection<User> findAll() {
        return userMap.values();
    }

    @Override
    public User create(User user) {
        long userId = generateId();
        user.setId(userId);
        userMap.put(userId, user);
        log.debug("The {} created", user);
        return user;
    }

    @Override
    public User update(User user) {
        long userId = user.getId();

        User pastUser = userMap.get(userId);
        if (pastUser == null) throw new NotFoundByIdException("user", userId);

        userMap.put(userId, user);
        log.debug("The {} updated to {}", pastUser, user);
        return user;
    }

    @Override
    public User delete(long id) {
        log.debug("The user (id={}) removed", id);
        return userMap.remove(id);
    }

    @Override
    public void deleteAll() {
        userMap.clear();
        log.debug("All users removed");
    }

    @Override
    public Collection<Long> getIdsFriends(Long id) {
        return find(id).getFriendsId();
    }

    @Override
    public void addFriend(Long id, Long friendId) {
        User user = find(id);
        user.getFriendsId().add(id);
        log.debug("The friend (id={}) added to user (id={}) friends", friendId, id);
    }

    @Override
    public void addFriends(Long id, Long... friendIds) {
        User user = find(id);
        user.getFriendsId().addAll(Set.of(friendIds));
        log.debug("The friends (id={}) added to user (id={}) friends", friendIds, id);
    }

    @Override
    public void deleteFriend(Long id, Long friendId) {
        User user = find(id);
        user.getFriendsId().remove(friendId);
        log.debug("The friend (id={}) removed from user (id={}) friends", friendId, id);
    }

    @Override
    public void deleteFriends(Long id, Long... friendIds) {
        User user = find(id);
        user.getFriendsId().removeAll(Set.of(friendIds));
        log.debug("The friends (id={}) removed from user (id={}) friends", friendIds, id);
    }

    @Override
    public void deleteAllFriends(Long id) {
        User user = find(id);
        user.getFriendsId().clear();
        log.debug("All friends removed from user (id={}) friends", id);
    }

    private long generateId() {
        return ++id;
    }
}
