package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import ru.yandex.practicum.filmorate.constraint.AfterDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Film extends BaseEntity<Long> {
    @NotBlank(message = "Film name cannot be empty")
    private String name;
    @Length(message = "Film description length should be < 200 characters", max = 200)
    private String description;
    @AfterDate(date = "28.12.1895", message = "Film releaseDate should be after 28.12.1895")
    private LocalDate releaseDate;
    @Positive(message = "Film duration should be positive")
    private int duration;
    private Set<Long> likedIdsUsers = new HashSet<>();

    public int getCountLikes() {
        return likedIdsUsers.size();
    }

    @Override
    public String toString() {
        return "Film{" +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", releaseDate=" + releaseDate +
                ", duration=" + duration +
                ", likedIdsUsers=" + likedIdsUsers +
                '}';
    }
}
