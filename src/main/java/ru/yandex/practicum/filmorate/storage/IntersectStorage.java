package ru.yandex.practicum.filmorate.storage;

import java.util.Optional;

public interface IntersectStorage<EntityType, idType> {
    Optional<EntityType> find(idType id1, idType id2);

    void save(EntityType entity);

    boolean delete(EntityType entity);
}
