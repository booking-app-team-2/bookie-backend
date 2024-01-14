package booking_app_team_2.bookie.dto;

import lombok.Getter;

@Getter
public class ReservationDTO {
    private int numberOfGuests;
    private Long accommodationId;
    private Long reserveeId;
    private PeriodDTO periodDTO;
}
