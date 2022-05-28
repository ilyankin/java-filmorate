package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserValidationTest {
    private static Validator validator;

    private final User user = new User();

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @BeforeEach
    private void updateData() {
        user.setEmail("user@mail.com");
        user.setLogin("UserLogin");
        user.setName("Name user");
        user.setBirthday(LocalDate.of(1999, 12, 7));
    }


    @NullAndEmptySource
    @ParameterizedTest
    public void shouldThrowExceptionWhenUserEmailIsNullOrEmpty(String email) {
        user.setEmail(email);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals("User email cannot be empty", violations.iterator().next().getMessage());
    }


    private static Stream<Arguments> provideEmailsAreValid() {
        return Stream.of(
                Arguments.of("example@email.com"),
                Arguments.of("example.first.middle.lastname@email.com"),
                Arguments.of("example@subdomain.email.com"),
                Arguments.of("example+firstname+lastname@email.com"),
                Arguments.of("example@234.234.234.234"),
                Arguments.of("example@[234.234.234.234]"),
                Arguments.of("example”@email.com"),
                Arguments.of("0987654321@example.com"),
                Arguments.of("example@email-one.com"),
                Arguments.of("_______@email.com"),
                Arguments.of("example@email.name"),
                Arguments.of("example@email.co.jp"),
                Arguments.of("example.firstname-lastname@email.com")
        );
    }

    @ParameterizedTest
    @MethodSource("provideEmailsAreValid")
    public void shouldThrowExceptionWhenUserEmailIsValid(String email) {
        user.setEmail(email);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
        assertEquals(email, user.getEmail());
    }


    private static Stream<Arguments> provideEmailsAreNotValid() {
        return Stream.of(
                Arguments.of("plaintextaddress"),
                Arguments.of("@#@@##@%^%#$@#$@#.com"),
                Arguments.of("@email.com"),
                Arguments.of("John Doe <example@email.com>"),
                Arguments.of("example.email.com"),
                Arguments.of(".example@email.com"),
                Arguments.of("example.@email.com"),
                Arguments.of("example@email.com (John Doe)"),
                Arguments.of("example@-email.com"),
                Arguments.of("”(),:;<>[\\]@email.com"),
                Arguments.of("example\\ is”especially”not\\allowed@email.com")
        );
    }

    @ParameterizedTest
    @MethodSource("provideEmailsAreNotValid")
    public void shouldThrowExceptionWhenUserEmailIsNotValid(String email) {
        user.setEmail(email);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals("User email is not valid", violations.iterator().next().getMessage());
    }

    @NullAndEmptySource
    @ParameterizedTest
    public void shouldThrowExceptionWhenUserLoginIsNullOrEmpty(String login) {
        user.setLogin(login);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals("User login cannot be empty", violations.iterator().next().getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenUserLoginContainsWhitespaces() {
        user.setLogin("User login");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals("User login should not contain whitespace characters",
                violations.iterator().next().getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenUserBirthdayInFuture() {
        user.setBirthday(LocalDate.now().plusDays(1));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals("User birthday cannot be in the future",
                violations.iterator().next().getMessage());
    }
}
