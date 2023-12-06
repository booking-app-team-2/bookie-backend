package booking_app_team_2.bookie.domain;

import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Embeddable
public class Period {
    private long startDate;
    private long endDate;
}
