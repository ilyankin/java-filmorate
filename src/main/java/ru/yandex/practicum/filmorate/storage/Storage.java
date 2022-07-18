package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.BaseEntity;

import java.util.Collection;
import java.util.Optional;

public interface Storage<EntityType extends BaseEntity<idType>, idType> {
    Optional<EntityType> findById(idType id);

    Collection<EntityType> findAll();

    idType save(EntityType value);

    Optional<EntityType> update(EntityType value);

    boolean delete(idType id);
}
