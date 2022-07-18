package ru.yandex.practicum.filmorate.service.mpa;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ResourceNotFoundException;
import ru.yandex.practicum.filmorate.model.MpaRating;

@Service
public class MpaService {
    public MpaRating find(Integer id) {
        if (MpaRating.values().length < id || id < 1) throw new ResourceNotFoundException("MPA", "id", id);
        return MpaRating.forValue(id);
    }

    public MpaRating[] findAll() {
        return MpaRating.values();
    }
}
