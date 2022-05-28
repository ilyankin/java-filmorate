package ru.yandex.practicum.filmorate.constraint.validator;

import ru.yandex.practicum.filmorate.constraint.AfterDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class AfterDateConstraintValidator implements ConstraintValidator<AfterDate, LocalDate> {
    private LocalDate date;

    @Override
    public void initialize(AfterDate constraintAnnotation) {
        String value = constraintAnnotation.value();
        date = LocalDate.parse(value, DateTimeFormatter.BASIC_ISO_DATE);
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) return true;
        return date.isBefore(value);
    }
}
