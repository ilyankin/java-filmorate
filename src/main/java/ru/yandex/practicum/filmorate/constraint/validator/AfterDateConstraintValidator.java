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
        String value = constraintAnnotation.date();
        date = LocalDate.parse(value, DateTimeFormatter.ofPattern(constraintAnnotation.datePattern()));
    }

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext context) {
        if (date == null) return true;
        return this.date.isBefore(date);
    }
}
