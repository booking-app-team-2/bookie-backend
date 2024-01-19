package booking_app_team_2.bookie.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TimestampNotBeforeTodayValidator.class)
public @interface TimestampNotBeforeToday {
    String message() default "This date must not be before today.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
