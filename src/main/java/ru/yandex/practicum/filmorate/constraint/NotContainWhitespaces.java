package ru.yandex.practicum.filmorate.constraint;

import ru.yandex.practicum.filmorate.constraint.validator.NotContainWhitespacesConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {NotContainWhitespacesConstraintValidator.class})
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface NotContainWhitespaces {
    String message() default "{ru.yandex.practicum.filmorate.constraint.NotContainWhitespaces.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
