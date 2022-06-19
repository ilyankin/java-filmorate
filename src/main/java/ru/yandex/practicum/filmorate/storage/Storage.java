package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.BaseEntity;

import java.util.Collection;

public interface Storage<EntityType extends BaseEntity<idType>, idType> {
    EntityType find(idType id);

    Collection<EntityType> findAll();

    EntityType create(EntityType value);

    EntityType update(EntityType value);

    EntityType delete(idType id);

    void deleteAll();
}
