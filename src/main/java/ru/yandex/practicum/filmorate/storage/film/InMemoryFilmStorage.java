package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.AbstractStorage;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Component
@Slf4j
public class InMemoryFilmStorage extends AbstractStorage<Film, Long> implements Likeable<Long, Long> {
    private Long id = 0L;

    @Override
    public Film create(Film film) {
        film.setId(generateId());
        return super.create(film);
    }

    @Override
    public void addLike(Long userId, Long filmId) {
        Film film = find(filmId);
        film.getLikedIdsUsers().add(userId);
        log.info("The user (id={}) liked the film (id={}, name={})", userId, filmId, film.getName());
    }

    @Override
    public void removeLike(Long userId, Long filmId) {
        find(userId);
        Film film = find(filmId);
        film.getLikedIdsUsers().remove(userId);
        log.info("The user (id={}) removed his like from the film (id={}, name={})", userId, filmId, film.getName());
    }

    public Collection<Film> getTopFilmsByLikes(int count) {
        return findAll().stream()
                .sorted(Comparator.comparing(Film::getCountLikes).reversed())
                .limit(count)
                .collect(Collectors.toUnmodifiableList());
    }

    private Long generateId() {
        return ++id;
    }
}
