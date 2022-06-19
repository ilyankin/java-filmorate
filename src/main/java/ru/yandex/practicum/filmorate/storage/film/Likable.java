package ru.yandex.practicum.filmorate.storage.film;

public interface Likable<LikingId, LikableId> {
    void addLike(LikingId likingId, LikableId likableId);

    void removeLike(LikingId likingId, LikableId likableId);
}
