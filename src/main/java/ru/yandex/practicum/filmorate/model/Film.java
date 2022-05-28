package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.yandex.practicum.filmorate.constraint.AfterDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDate;


@Data
public class Film {
    private long id;
    @NotBlank(message = "Film name cannot be empty")
    private String name;
    @Length(message = "Film description length should be < 200 characters", max = 200)
    private String description;
    @AfterDate(value = "18951228", message = "Film releaseDate should be after 28.12.1895")
    private LocalDate releaseDate;
    @Positive(message = "Film duration should be positive")
    private int duration;
}
