package booking_app_team_2.bookie.dto;

import booking_app_team_2.bookie.domain.Accommodation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

@Getter
public class AccommodationNameDTO {
    private final String name;
    private final int reservationCancellationDeadline;

    @JsonIgnore
    public AccommodationNameDTO(Accommodation accommodation) {
        name = accommodation.getName();
        reservationCancellationDeadline = accommodation.getReservationCancellationDeadline();
    }
}
