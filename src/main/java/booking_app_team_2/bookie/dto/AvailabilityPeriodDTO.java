package booking_app_team_2.bookie.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
public class AvailabilityPeriodDTO {
    private Long id;
    private BigDecimal price;
    private PeriodDTO period;
}
