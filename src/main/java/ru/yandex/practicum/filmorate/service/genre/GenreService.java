package ru.yandex.practicum.filmorate.service.genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ResourceNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreDbStorage;

import java.util.Collection;
import java.util.Objects;

@Service
public class GenreService {
    private final GenreDbStorage genreStorage;

    @Autowired
    public GenreService(GenreDbStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    public Genre find(Integer genreId) {
        return genreStorage.findById(genreId).orElseThrow(() -> new ResourceNotFoundException("genre", "id", genreId));
    }

    public Collection<Genre> findAll() {
        return genreStorage.findAll();
    }

    public Genre create(Genre genre) {
        Objects.requireNonNull(genre, "genre must not be null");
        genre.setId(genreStorage.save(genre));
        return genre;
    }

    public Genre update(Genre genre) {
        Objects.requireNonNull(genre, "genre must not be null");
        return genreStorage.update(genre).orElseThrow(() ->
                new ResourceNotFoundException("genre", "id", genre.getId()));
    }

    public boolean delete(Integer genreId) {
        return genreStorage.delete(genreId);
    }
}
