package ru.yandex.practicum.filmorate.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.yandex.practicum.filmorate.constraint.NotContainWhitespaces;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity<Long> {
    @Email(message = "User email is not valid")
    @NotBlank(message = "User email cannot be empty")
    private String email;
    @NotBlank(message = "User login cannot be empty")
    @NotContainWhitespaces(message = "User login should not contain whitespace characters")
    private String login;
    private String name;
    @Past(message = "User birthday cannot be in the future")
    private LocalDate birthday;

    public User(Long id, String email, String login, String name, LocalDate birthday) {
        super(id);
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }

    @Override
    public Map<String, Object> toMap() {
        return new HashMap<>() {{
            put("id", id == null ? 0L : id);
            put("email", email);
            put("login", login);
            put("name", name);
            put("birthday", birthday);
        }};
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
