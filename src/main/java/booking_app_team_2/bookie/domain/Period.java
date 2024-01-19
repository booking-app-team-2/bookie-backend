package booking_app_team_2.bookie.domain;

import booking_app_team_2.bookie.dto.PeriodDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

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

    private LocalDate convertTimestampToLocalDate(long timestamp) {
        return Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public Period(long startTimestamp, long endTimestamp) {
        startDate = convertTimestampToLocalDate(startTimestamp);
        endDate = convertTimestampToLocalDate(endTimestamp);
    }

    public Period(PeriodDTO periodDTO) {
        startDate = convertTimestampToLocalDate(periodDTO.getStartTimestamp());
        endDate = convertTimestampToLocalDate(periodDTO.getEndTimestamp());
    }

    @JsonIgnore
    public int getInDays() {
        return (int) DAYS.between(startDate, endDate) + 1;
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

    public boolean hasBegun() {
        return startDate.isBefore(LocalDate.now()) || startDate.isEqual(LocalDate.now());
    }
}
