package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService extends AbstractService<User, InMemoryUserStorage> {

    @Autowired
    public UserService(InMemoryUserStorage userStorage) {
        super(userStorage);
    }

    @Override
    public User create(User user) {
        setNameToLoginIfNameIsEmpty(user);
        return super.create(user);
    }

    @Override
    public User update(User user) {
        setNameToLoginIfNameIsEmpty(user);
        return super.update(user);
    }

    public Collection<Long> getIdsFriends(Long id) {
        return storage.getIdsFriends(id);
    }

    public Collection<User> getFriends(Long id) {
        return storage.getIdsFriends(id).stream().map(this::find).collect(Collectors.toUnmodifiableList());
    }

    public void addFriend(Long id, Long friendId) {
        storage.addFriend(id, friendId);
    }

    public void deleteFriend(Long id, Long friendId) {
        storage.deleteFriend(id, friendId);
    }

    public Collection<User> getCommonFriends(Long id, Long otherId) {
        // using the HashSet wrapper in order not to modify user's set of friends
        Collection<Long> friends = new HashSet<>(getIdsFriends(id));
        friends.retainAll(getIdsFriends(otherId));
        return friends.stream().map(this::find).collect(Collectors.toUnmodifiableList());
    }

    private void setNameToLoginIfNameIsEmpty(User user) {
        String name = user.getName();
        if (name == null || name.isBlank()) user.setName(user.getLogin());
        log.info("The empty username was automatically replaced with his login=\"{}\"", user.getLogin());
    }

}
