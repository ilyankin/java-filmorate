package ru.yandex.practicum.filmorate.service.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ResourceNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserEmailAlreadyTakenException;
import ru.yandex.practicum.filmorate.exception.UserLoginAlreadyTakenException;
import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.friendship.FriendshipDbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;
import ru.yandex.practicum.filmorate.util.DbUtil;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;


@Slf4j
@Service
public class UserService {
    private final UserDbStorage userStorage;
    private final FriendshipDbStorage friendshipStorage;

    @Autowired
    public UserService(UserDbStorage userStorage, FriendshipDbStorage friendshipStorage) {
        this.userStorage = userStorage;
        this.friendshipStorage = friendshipStorage;
    }

    public User find(Long userId) {
        return userStorage.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));
    }

    public Collection<User> findAll() {
        return userStorage.findAll();
    }

    public User create(User user) {
        Objects.requireNonNull(user, "user must not be null");
        checkEmailAndLoginAvailability(user);
        setNameToLoginIfNameIsEmpty(user);
        Long id = userStorage.save(user);
        user.setId(id);
        return user;
    }

    public User update(User user) {
        Objects.requireNonNull(user, "user must not be null");
        checkEmailAndLoginAvailability(user);
        setNameToLoginIfNameIsEmpty(user);
        return userStorage.update(user).orElseThrow(() -> new ResourceNotFoundException("user", "id", user.getId()));
    }

    public boolean delete(Long userId) {
        return userStorage.delete(userId);
    }

    public void createFriendship(Long userId, Long friendId) {
        DbUtil.checkUsersExist(userStorage, userId, friendId);

        Friendship friendship = friendshipStorage.find(userId, friendId).orElse(null);

        if (friendship == null) {
            friendshipStorage.save(new Friendship(userId, friendId, false));
            return;
        }

        if (Objects.equals(friendship.getUserId(), userId) || friendship.isConfirmed()) return;

        friendshipStorage.save(new Friendship(userId, friendId, true));
    }

    public void removeFriendship(Long userId, Long friendId) {
        DbUtil.checkUsersExist(userStorage, userId, friendId);

        Optional<Friendship> friendship = friendshipStorage.find(userId, friendId);
        friendship.ifPresent(friendshipStorage::delete);
    }

    public Collection<User> getUserFriends(Long userId) {
        DbUtil.checkUsersExist(userStorage, userId);
        return userStorage.getUserFriends(userId);
    }

    public Collection<User> getCommonFriends(Long userId, Long otherUserId) {
        DbUtil.checkUsersExist(userStorage, userId, otherUserId);
        return userStorage.getCommonUserFriends(userId, otherUserId);
    }

    private void setNameToLoginIfNameIsEmpty(User user) {
        String name = user.getName();
        if (name == null || name.isBlank()) user.setName(user.getLogin());
        log.info("The empty username was automatically replaced with his login=\"{}\"", user.getLogin());
    }

    private void checkEmailAndLoginAvailability(User user) {
        checkEmailAvailability(user);
        checkLoginAvailability(user);
    }

    private void checkEmailAvailability(User user) {
        String email = user.getEmail();
        if (userStorage.findByEmail(email).isPresent()) {
            log.warn("This user ({}) email {} is already taken", user, user.getEmail());
            throw new UserEmailAlreadyTakenException(email);
        }
    }

    private void checkLoginAvailability(User user) {
        String login = user.getLogin();
        if (userStorage.findByLogin(login).isPresent()) {
            log.warn("This user ({}) email {} is already taken", user, user.getLogin());
            throw new UserLoginAlreadyTakenException(login);
        }
    }
}
