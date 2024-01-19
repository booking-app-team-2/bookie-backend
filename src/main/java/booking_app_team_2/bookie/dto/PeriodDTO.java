package booking_app_team_2.bookie.dto;

import booking_app_team_2.bookie.domain.Period;
import booking_app_team_2.bookie.validation.TimestampNotBeforeToday;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.ZoneId;

@NoArgsConstructor
@Getter
@NoArgsConstructor
public class PeriodDTO {

    @TimestampNotBeforeToday(message = "The period start date must not be earlier than today.")
    private long startTimestamp;
    @TimestampNotBeforeToday(message = "The period end date must not be earlier than today.")
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
