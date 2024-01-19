package booking_app_team_2.bookie.validation;

import booking_app_team_2.bookie.dto.PeriodDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StartTimestampBeforeEndTimestampValidator implements
        ConstraintValidator<StartTimestampBeforeEndTimestamp, PeriodDTO> {
    @Override
    public void initialize(StartTimestampBeforeEndTimestamp constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(PeriodDTO periodDTO, ConstraintValidatorContext constraintValidatorContext) {
        return periodDTO.getEndTimestamp() >= periodDTO.getStartTimestamp();
    }
}
