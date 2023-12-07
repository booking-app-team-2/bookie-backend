package booking_app_team_2.bookie.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Embeddable
public class Period {
    @Column(name = "start_date", nullable = false)
    private long startDate;
    @Column(name = "end_date", nullable = false)
    private long endDate;
}
