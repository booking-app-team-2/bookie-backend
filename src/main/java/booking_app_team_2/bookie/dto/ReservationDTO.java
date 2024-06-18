package booking_app_team_2.bookie.dto;

import booking_app_team_2.bookie.domain.Reservation;
import booking_app_team_2.bookie.domain.ReservationStatus;
import booking_app_team_2.bookie.validation.StartTimestampBeforeEndTimestamp;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ReservationDTO {
    @Min(value = 0, message = "The number of guests must not be less than 0.")
    private int numberOfGuests;
    private ReservationStatus status;
    @NotNull(message = "The accommodation ID must not be null.")
    private Long accommodationId;
    @NotNull(message = "The reservee ID must not be null.")
    private Long reserveeId;
    @Valid
    @NotNull(message = "Reservation period must not be null.")
    @StartTimestampBeforeEndTimestamp
    private PeriodDTO periodDTO;

    public ReservationDTO(Reservation reservation) {
        numberOfGuests = reservation.getNumberOfGuests();
        status = reservation.getStatus();
        accommodationId = reservation.getAccommodation().getId();
        reserveeId = reservation.getReservee().getId();
        periodDTO = new PeriodDTO(reservation.getPeriod());
    }
}
