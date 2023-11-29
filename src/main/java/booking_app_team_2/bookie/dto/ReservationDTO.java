package booking_app_team_2.bookie.dto;

import booking_app_team_2.bookie.domain.Period;
import booking_app_team_2.bookie.domain.ReservationStatus;

public class ReservationDTO {
    private Long Id = null;
    private int numberOfGuests;
    private ReservationStatus status = ReservationStatus.Waiting;
    private Long accommodationId;
    private Long reserveeId;
    private Period period;
}
