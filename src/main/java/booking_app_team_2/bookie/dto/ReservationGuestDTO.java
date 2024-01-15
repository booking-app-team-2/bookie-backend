package booking_app_team_2.bookie.dto;

import booking_app_team_2.bookie.domain.Reservation;
import booking_app_team_2.bookie.domain.ReservationStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ReservationGuestDTO {
    private final Long id;
    private final int numberOfGuests;
    private final ReservationStatus status;
    private final AccommodationNameDTO accommodationNameDTO;
    private final PeriodDTO periodDTO;
    private final BigDecimal price;

    @JsonIgnore
    public ReservationGuestDTO(Reservation reservation) {
        id = reservation.getId();
        numberOfGuests = reservation.getNumberOfGuests();
        status = reservation.getStatus();
        accommodationNameDTO = new AccommodationNameDTO(reservation.getAccommodation());
        periodDTO = new PeriodDTO(reservation.getPeriod());
        price = reservation.getPrice();
    }
}
