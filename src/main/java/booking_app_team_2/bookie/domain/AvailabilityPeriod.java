package booking_app_team_2.bookie.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@NoArgsConstructor
@Entity
public class AvailabilityPeriod {
    @Id
    @SequenceGenerator(name = "AVAILABILITY_PERIOD_SEQ", sequenceName = "SEQUENCE_AVAILABILITY_PERIOD", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AVAILABILITY_PERIOD_SEQ")
    private Long id = null;
    private BigDecimal price;
    @Embedded
    private Period period;
}
