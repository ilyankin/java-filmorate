package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.constraint.NotContainsWhitespaces;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
public class User {
    private long id;
    @Email(message = "User email is not valid")
    @NotBlank(message = "User email cannot be empty")
    private String email;
    @NotBlank(message = "User login cannot be empty")
    @NotContainsWhitespaces(message = "User login should not contain whitespace characters")
    private String login;
    private String name;
    @Past(message = "User birthday cannot be in the future")
    private LocalDate birthday;
}
