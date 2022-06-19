package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.AbstractStorage;

import java.util.Collection;

@Slf4j
@Component
public class InMemoryUserStorage extends AbstractStorage<User, Long> implements Friendable<Long> {
    private Long id = 0L;

    @Override
    public User create(User user) {
        user.setId(generateId());
        return super.create(user);
    }

    @Override
    public Collection<Long> getIdsFriends(Long id) {
        return find(id).getFriendIds();
    }

    @Override
    public void addFriend(Long id, Long friendId) {
        User user = find(id);
        User friend = find(friendId);
        user.getFriendIds().add(friendId);
        friend.getFriendIds().add(id);
        log.info("The friend (id={}) added to user (id={}) friends", friendId, id);
    }

    @Override
    public void deleteFriend(Long id, Long friendId) {
        User user = find(id);
        User friend = find(friendId);
        user.getFriendIds().remove(friendId);
        friend.getFriendIds().remove(id);
        log.info("The friend (id={}) removed from user (id={}) friends", friendId, id);
    }

    private long generateId() {
        return ++id;
    }
}
