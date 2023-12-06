package booking_app_team_2.bookie.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@NoArgsConstructor
@Entity
public class AvailabilityPeriod {
    @Id
    private Long id = null;
    private BigDecimal price;
    @Embedded
    private Period period;
}
