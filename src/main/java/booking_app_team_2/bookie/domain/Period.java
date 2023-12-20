package booking_app_team_2.bookie.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class Period {
    @Column(name = "start_date", nullable = false)
    private long startDate;

    @Column(name = "end_date", nullable = false)
    private long endDate;

    public int getInDays() {
        return (int) ((endDate - startDate) / (60 * 60 * 24));
    }

    public boolean overlaps(Period period) {
        return this.startDate <= period.getStartDate() && this.endDate >= period.getEndDate();
    }

    public boolean overlapsBottomOnly(Period period) {
        return this.startDate < period.getStartDate() && this.endDate == period.getEndDate();
    }

    public boolean overlapsTopOnly(Period period) {
        return this.startDate == period.getStartDate() && this.endDate > period.getEndDate();
    }
    public boolean exclusivelyOverlaps(Period period) {
        return this.startDate < period.getStartDate() && this.endDate > period.getEndDate();
    }

    public boolean isEqualTo(Period period) {
        return this.startDate == period.getStartDate() && this.endDate == period.getEndDate();
    }
}
