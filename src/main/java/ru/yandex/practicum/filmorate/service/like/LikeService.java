package ru.yandex.practicum.filmorate.service.like;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ResourceNotFoundException;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.like.LikeDbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;
import ru.yandex.practicum.filmorate.util.DbUtil;

import java.util.Map;
import java.util.Objects;

@Service
public class LikeService {
    private final LikeDbStorage likeStorage;
    private final UserDbStorage userStorage;
    private final FilmDbStorage filmStorage;

    @Autowired
    public LikeService(LikeDbStorage likeStorage, UserDbStorage userStorage, FilmDbStorage filmStorage) {
        this.likeStorage = likeStorage;
        this.userStorage = userStorage;
        this.filmStorage = filmStorage;
    }

    public Like find(Long userId, Long filmId) {
        checkUserAndFilmExist(userId, filmId);
        return likeStorage.find(userId, filmId).orElseThrow(() -> new ResourceNotFoundException("like",
                Map.of("userId", userId, "filmId", filmId)));
    }

    public void save(Like like) {
        Objects.requireNonNull(like, "like must not be null");
        checkUserAndFilmExist(like.getUserId(), like.getFilmId());
        likeStorage.save(like);
    }

    public boolean delete(Like like) {
        Objects.requireNonNull(like, "like must not be null");
        checkUserAndFilmExist(like.getUserId(), like.getFilmId());
        return likeStorage.delete(like);
    }

    private void checkUserAndFilmExist(Long userId, Long filmId) {
        DbUtil.checkUsersExist(userStorage, userId);
        DbUtil.checkFilmsExist(filmStorage, filmId);
    }
}
