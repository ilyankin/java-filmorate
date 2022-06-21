package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.constraint.NotContainWhitespaces;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity<Long> {
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
    private final Set<Long> friendIds = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;

        User user = (User) o;

        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", friendIds=" + friendIds +
                '}';
    }
}
