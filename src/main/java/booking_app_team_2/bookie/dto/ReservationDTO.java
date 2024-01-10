package booking_app_team_2.bookie.dto;

import booking_app_team_2.bookie.domain.Period;
import lombok.Getter;

@Getter
public class ReservationDTO {
    private int numberOfGuests;
    private Long accommodationId;
    private Long reserveeId;

    // TODO: Refactor this.

    private Period period;
}
