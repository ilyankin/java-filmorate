package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.constraint.NotContainWhitespaces;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    @EqualsAndHashCode.Include
    private long id;
    @Email(message = "User email is not valid")
    @NotBlank(message = "User email cannot be empty")
    @EqualsAndHashCode.Include
    private String email;
    @NotBlank(message = "User login cannot be empty")
    @NotContainWhitespaces(message = "User login should not contain whitespace characters")
    private String login;
    private String name;
    @Past(message = "User birthday cannot be in the future")
    private LocalDate birthday;
}
