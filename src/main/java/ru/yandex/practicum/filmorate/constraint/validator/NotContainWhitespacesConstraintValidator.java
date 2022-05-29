package ru.yandex.practicum.filmorate.constraint.validator;

import ru.yandex.practicum.filmorate.constraint.NotContainWhitespaces;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotContainWhitespacesConstraintValidator implements ConstraintValidator<NotContainWhitespaces, String> {

    @Override
    public void initialize(NotContainWhitespaces constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.length() == 0) return true;
        for (int i = 0; i < value.length(); i++) {
            if (Character.isWhitespace(value.charAt(i))) return false;
        }
        return true;
    }
}
