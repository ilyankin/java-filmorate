package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundByIdException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private long id = 0;
    private final Map<Long, User> userMap = new HashMap<>();

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        setNameToLoginIfNameIsEmpty(user);
        long userId = generateId();
        user.setId(userId);
        userMap.put(userId, user);
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        setNameToLoginIfNameIsEmpty(user);
        long userId = user.getId();

        if (userMap.get(userId) == null) throw new NotFoundByIdException("user", userId);

        userMap.put(userId, user);
        return user;
    }

    @GetMapping
    public Collection<User> getAll() {
        return userMap.values();
    }

    private void setNameToLoginIfNameIsEmpty(User user) {
        String name = user.getName();
        if (name == null || name.isBlank()) user.setName(user.getLogin());
    }

    private long generateId() {
        return ++id;
    }
}
