package booking_app_team_2.bookie.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StartTimestampBeforeEndTimestampValidator.class)
public @interface StartTimestampBeforeEndTimestamp {
    String message() default "The period start date must be before or equal to the period end date.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
