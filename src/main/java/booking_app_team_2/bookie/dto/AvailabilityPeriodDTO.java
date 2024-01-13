package booking_app_team_2.bookie.dto;

import booking_app_team_2.bookie.domain.Period;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
public class AvailabilityPeriodDTO {
    private Long id = null;
    private BigDecimal price;
    private PeriodDTO period;
    private boolean isDeleted = false;
}
