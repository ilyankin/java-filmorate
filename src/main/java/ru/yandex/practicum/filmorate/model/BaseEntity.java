package ru.yandex.practicum.filmorate.model;

public abstract class BaseEntity<T> {
    protected T id;

    protected BaseEntity() {
    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseEntity<?> that = (BaseEntity<?>) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
