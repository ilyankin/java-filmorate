package ru.yandex.practicum.filmorate.controller.mpa;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.ResourceNotFoundException;
import ru.yandex.practicum.filmorate.model.MpaRating;

@RestController
@RequestMapping("/mpa")
public class MpaController {
    @GetMapping("/{id}")
    public MpaRating find(@PathVariable Integer id) {
        if (MpaRating.values().length < id || id < 1) throw new ResourceNotFoundException("MPA", "id", id);
        return MpaRating.forValue(id);
    }

    @GetMapping
    public MpaRating[] findAll() {
        return MpaRating.values();
    }
}
