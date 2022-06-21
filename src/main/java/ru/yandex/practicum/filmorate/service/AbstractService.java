package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.BaseEntity;
import ru.yandex.practicum.filmorate.storage.AbstractStorage;

import java.util.Collection;

public abstract class AbstractService<EntityType extends BaseEntity<Long>, StorageType extends AbstractStorage<EntityType, Long>> {

    protected final StorageType storage;

    public AbstractService(StorageType storage) {
        this.storage = storage;
    }

    public EntityType find(Long entityId) {
        return storage.find(entityId);
    }

    public Collection<EntityType> findAll() {
        return storage.findAll();
    }

    public EntityType create(EntityType entity) {
        return storage.create(entity);
    }

    public EntityType update(EntityType entity) {
        return storage.update(entity);
    }

    public EntityType delete(Long entityId) {
        return storage.delete(entityId);
    }

    public void deleteAll() {
        storage.deleteAll();
    }
}
