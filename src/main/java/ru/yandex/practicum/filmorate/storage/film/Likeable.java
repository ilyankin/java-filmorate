package ru.yandex.practicum.filmorate.storage.film;

public interface Likeable<LikingId, LikeableId> {
    void addLike(LikingId likingId, LikeableId likeableId);

    void removeLike(LikingId likingId, LikeableId likeableId);
}
