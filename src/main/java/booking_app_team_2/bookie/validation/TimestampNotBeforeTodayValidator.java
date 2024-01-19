package booking_app_team_2.bookie.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.ZoneId;

public class TimestampNotBeforeTodayValidator implements ConstraintValidator<TimestampNotBeforeToday, Long> {
    @Override
    public void initialize(TimestampNotBeforeToday constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long timestamp, ConstraintValidatorContext constraintValidatorContext) {
        return timestamp >= LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
