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
import java.time.format.DateTimeFormatter;
import java.util.Objects;

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

    public Period(PeriodDTO periodDTO) {
        startDate = convertTimestampToLocalDate(periodDTO.getStartTimestamp());
        endDate = convertTimestampToLocalDate(periodDTO.getEndTimestamp());
    }

    // TODO: Remove this once the refactor is complete
    public Period(String startDate,String endDate){
        this.startDate=convertToLocalDate(startDate);
        this.endDate=convertToLocalDate(endDate);
    }

    // TODO: Remove this once the refactor is complete
    private LocalDate convertToLocalDate(String dateString) {
        if(dateString==""){
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, formatter);
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
}
