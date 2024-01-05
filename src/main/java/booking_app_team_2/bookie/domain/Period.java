package booking_app_team_2.bookie.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class Period {
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @JsonIgnore
    public int getInDays() {
        return (int) DAYS.between(startDate, endDate);
    }

    public boolean overlaps(Period period) {
        return (startDate.isBefore(period.getStartDate()) || startDate.isEqual(period.getStartDate()))
                && (endDate.isAfter(period.getEndDate()) || endDate.isEqual(period.getEndDate()));
    }

    public boolean overlapsBottomOnly(Period period) {
        return startDate.isBefore(period.getStartDate()) && endDate.isEqual(period.getEndDate());
    }

    public boolean overlapsTopOnly(Period period) {
        return startDate.isEqual(period.getStartDate()) && endDate.isAfter(period.getEndDate());
    }

    public boolean exclusivelyOverlaps(Period period) {
        return startDate.isBefore(period.getStartDate()) && endDate.isAfter(period.getEndDate());
    }

    public boolean isEqualTo(Period period) {
        return startDate.isEqual(period.getStartDate()) && endDate.isEqual(period.getEndDate());
    }
}
