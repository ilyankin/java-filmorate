package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundByIdException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
        setNameToLoginIfNameIsEmpty(user);
        long userId = generateId();
        user.setId(userId);
        userMap.put(userId, user);
        return user;
    }

    @Override
    public User update(User user) {
        setNameToLoginIfNameIsEmpty(user);
        long userId = user.getId();

        if (userMap.get(userId) == null) throw new NotFoundByIdException("user", userId);

        userMap.put(userId, user);
        return user;
    }

    @Override
    public User delete(long id) {
        return userMap.remove(id);
    }


    @Override
    public void deleteAll() {
        userMap.clear();
    }

    private void setNameToLoginIfNameIsEmpty(User user) {
        String name = user.getName();
        if (name == null || name.isBlank()) user.setName(user.getLogin());
    }

    private long generateId() {
        return ++id;
    }
}
