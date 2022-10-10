package ru.yandex.practicum.filmorate.model;


import lombok.Getter;

import java.util.Map;

@Getter
public class Genre extends BaseEntity<Integer> {
    private final String name;

    public Genre(Integer id, String name) {
        super(id);
        this.name = name;
    }

    @Override
    public Map<String, Object> toMap() {
        return Map.of("id", id, "name", name);
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                '}';
    }
}
