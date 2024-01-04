package booking_app_team_2.bookie.dto;

import booking_app_team_2.bookie.domain.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReservationStatusDTO {
    private ReservationStatus status;
}
