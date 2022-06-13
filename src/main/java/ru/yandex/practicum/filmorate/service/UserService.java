package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.Set;

@Slf4j
@Service
public class UserService {

    private final UserStorage<User> userStorage;

    @Autowired
    public UserService(UserStorage<User> userStorage) {
        this.userStorage = userStorage;
    }

    public User find(long id) {
        return userStorage.find(id);
    }

    public Collection<User> findAll() {
        return userStorage.findAll();
    }

    public User create(User user) {
        setNameToLoginIfNameIsEmpty(user);
        return userStorage.create(user);
    }

    public User update(User user) {
        setNameToLoginIfNameIsEmpty(user);
        return userStorage.update(user);
    }

    public User delete(long id) {
        return userStorage.delete(id);
    }

    public void deleteAll() {
        userStorage.deleteAll();
    }

    public Collection<Long> getIdsFriends(Long id) {
        return userStorage.getIdsFriends(id);
    }

    public void addFriend(Long id, Long friendId) {
        userStorage.addFriend(id, friendId);
    }

    public void addFriends(Long id, Long... friendIds) {
        userStorage.addFriends(id, friendIds);
    }

    public void deleteFriend(Long id, Long friendId) {
        userStorage.deleteFriend(id, friendId);
    }

    public void deleteFriends(Long id, Long... friendIds) {
        userStorage.deleteFriends(id, friendIds);
    }

    public void deleteAllFriends(Long id) {
        userStorage.deleteFriends(id);
    }

    public Collection<Long> getMutualFriends(Long id1, Long id2) {
        Collection<Long> friends = getIdsFriends(id1);
        friends.retainAll(getIdsFriends(id2));
        return friends;
    }

    private void setNameToLoginIfNameIsEmpty(User user) {
        String name = user.getName();
        if (name == null || name.isBlank()) user.setName(user.getLogin());
    }

}
