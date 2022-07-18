package ru.yandex.practicum.filmorate.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@EqualsAndHashCode
public abstract class BaseEntity<T> implements Mapable {
    protected T id;

    protected BaseEntity() {
    }

    protected BaseEntity(T id) {
        this.id = id;
    }
}
