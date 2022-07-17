package ru.yandex.practicum.filmorate.model;


import lombok.Getter;

import java.util.Map;
import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Genre)) return false;
        if (!super.equals(o)) return false;

        Genre genre = (Genre) o;

        return Objects.equals(name, genre.name);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                '}';
    }
}
