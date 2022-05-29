package ru.yandex.practicum.filmorate.constraint;

import ru.yandex.practicum.filmorate.constraint.validator.AfterDateConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {AfterDateConstraintValidator.class})
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface AfterDate {
    String message() default "{ru.yandex.practicum.filmorate.constraint.AfterDate.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String date();

    String datePattern() default "dd.MM.yyyy";
}
