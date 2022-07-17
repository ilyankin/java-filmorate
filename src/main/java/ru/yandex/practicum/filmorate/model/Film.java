package ru.yandex.practicum.filmorate.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import ru.yandex.practicum.filmorate.constraint.AfterDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class Film extends BaseEntity<Long> {
    @NotBlank(message = "Film name cannot be empty")
    private String name;
    @Length(message = "Film description length should be < 200 characters", max = 200)
    private String description;
    @AfterDate(date = "28.12.1895", message = "Film releaseDate should be after 28.12.1895")
    private LocalDate releaseDate;
    @Positive(message = "Film duration should be positive")
    private int duration;
    private int rate;
    @NotNull(message = "Mpa cannot be null")
    private MpaRating mpa;

    private Set<Genre> genres;

    public Film(Long id, String name, String description, LocalDate releaseDate, int duration, int rate,
                MpaRating mpa, Set<Genre> genres) {
        super(id);
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.rate = rate;
        this.mpa = mpa;
        this.genres = genres;
    }

    public Film(Long id, String name, String description, LocalDate releaseDate, int duration, int rate,
                MpaRating mpa) {
        super(id);
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.rate = rate;
        this.mpa = mpa;
        this.genres = Collections.emptySet();
    }

    @Override
    public String toString() {
        return "Film{" +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", releaseDate=" + releaseDate +
                ", duration=" + duration +
                ", rate=" + rate +
                ", mpa=" + mpa +
                ", genres=" + genres +
                '}';
    }

    @Override
    public Map<String, Object> toMap() {
        return new HashMap<>() {{
            put("id", id);
            put("name", name);
            put("description", description);
            put("release_date", releaseDate == null ? null : Date.valueOf(releaseDate));
            put("duration", duration);
            put("rate", rate);
            put("mpa", mpa.getName());
        }};
    }
}
