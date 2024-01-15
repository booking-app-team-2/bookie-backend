package booking_app_team_2.bookie.dto;

import booking_app_team_2.bookie.domain.Period;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.ZoneId;

@NoArgsConstructor
@Getter
public class PeriodDTO {
    private long startTimestamp;
    private long endTimestamp;

    @JsonIgnore
    private long convertLocalDateToTimestamp(LocalDate localDate) {
        return localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public PeriodDTO(Period period) {
        startTimestamp = convertLocalDateToTimestamp(period.getStartDate());
        endTimestamp = convertLocalDateToTimestamp(period.getEndDate());
    }
}
