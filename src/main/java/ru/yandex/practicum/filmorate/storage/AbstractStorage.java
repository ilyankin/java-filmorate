package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import ru.yandex.practicum.filmorate.exception.ResourceNotFoundException;
import ru.yandex.practicum.filmorate.model.BaseEntity;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public abstract class AbstractStorage<EntityType extends BaseEntity<IdType>, IdType> implements Storage<EntityType, IdType> {
    @SuppressWarnings("unchecked")
    protected final String entityTypeClassName = ((Class<EntityType>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]).getSimpleName();
    protected final Map<IdType, EntityType> dataMap = new HashMap<>();

    @Override
    public EntityType find(IdType entityId) {
        EntityType entity = dataMap.get(entityId);
        if (entity == null) throw new ResourceNotFoundException(entityTypeClassName, "id", entityId);
        return entity;
    }

    @Override
    public Collection<EntityType> findAll() {
        return dataMap.values();
    }

    @Override
    public EntityType create(EntityType entity) {
        dataMap.put(entity.getId(), entity);
        log.info("The {} created", entity);
        return entity;
    }

    @Override
    public EntityType update(EntityType entity) {
        IdType entityId = entity.getId();
        EntityType oldEntity = find(entityId);
        if (oldEntity == null) throw new ResourceNotFoundException(entityTypeClassName, "id", entityId);
        dataMap.put(entityId, entity);
        log.info("The {} updated to {}", oldEntity, entity);
        return find(entityId);
    }

    @Override
    public EntityType delete(IdType entityId) {
        EntityType entity = find(entityId);
        if (entity == null) throw new ResourceNotFoundException(entityTypeClassName, "id", entityId);
        log.info("The {} removed", entity);
        return dataMap.remove(entityId);
    }

    @Override
    public void deleteAll() {
        log.info("All {} removed", entityTypeClassName);
        dataMap.clear();
    }

    @GetMapping
    public Collection<EntityType> getAll() {
        return dataMap.values();
    }
}