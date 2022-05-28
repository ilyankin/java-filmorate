package ru.yandex.practicum.filmorate.controller;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilmValidationTest {
    private static Validator validator;

    private final Film film = new Film();

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @BeforeEach
    private void updateData() {
        film.setName("Film Name");
        film.setDescription("Film Description");
        film.setReleaseDate(LocalDateTime.now().toLocalDate());
        film.setDuration(1);
    }

    @Test
    public void shouldThrowExceptionWhenFilmNameIsNull() {
        Set<ConstraintViolation<Film>> violations = validator.validate(new Film());
        assertEquals("Film name cannot be empty", violations.iterator().next().getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenFilmDescriptionLengthMoreThan200Characters() {
        film.setDescription("a".repeat(201));
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals("Film description length should be < 200 characters",
                violations.iterator().next().getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenFilmReleaseDateIsBefore1895year12month28day() {
        film.setReleaseDate(LocalDate.of(1895, 12, 27));
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals("Film releaseDate should be after 28.12.1895", violations.iterator().next().getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenFilmDurationNegative() {
        film.setDuration(-1);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals("Film duration should be positive", violations.iterator().next().getMessage());
    }
}
