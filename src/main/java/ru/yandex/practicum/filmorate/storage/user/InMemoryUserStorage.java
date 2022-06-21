package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserEmailAlreadyTakenException;
import ru.yandex.practicum.filmorate.exception.UserLoginAlreadyTakenException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.AbstractStorage;

import java.util.Collection;

@Slf4j
@Component
public class InMemoryUserStorage extends AbstractStorage<User, Long> implements Friendable<Long> {
    private Long id = 0L;

    @Override
    public User create(User user) {
        checkEmailAndLoginAvailability(user);
        user.setId(generateId());
        return super.create(user);
    }

    @Override
    public User update(User user) {
        User oldUser = find(user.getId());
        if (!oldUser.getEmail().equals(user.getEmail())) {
            checkEmailAvailability(user);
        }
        if (!oldUser.getLogin().equals(user.getLogin())) {
            checkLoginAvailability(user);
        }
        return super.update(user);
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


    private void checkEmailAndLoginAvailability(User newUser) {
        checkEmailAvailability(newUser);
        checkLoginAvailability(newUser);
    }
    private void checkEmailAvailability(User newUser) {
        String email = newUser.getEmail();
        for (User user : dataMap.values()) {
            if (email.equals(user.getEmail())) {
                log.warn("This user ({}) email {} is already taken", newUser, newUser.getEmail());
                throw new UserEmailAlreadyTakenException(email);
            }
        }
    }

    private void checkLoginAvailability(User newUser) {
        String login = newUser.getLogin();
        for (User user : dataMap.values()) {
            if (login.equals(user.getLogin())) {
                log.warn("This user ({}) login {} is already taken", newUser, newUser.getLogin());
                throw new UserLoginAlreadyTakenException(login);
            }
        }
    }
}
