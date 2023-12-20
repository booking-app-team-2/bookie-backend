package booking_app_team_2.bookie.service;

import booking_app_team_2.bookie.domain.Reservation;
import booking_app_team_2.bookie.dto.ReservationDTO;

public interface ReservationService extends GenericService<Reservation> {
    void createReservation(ReservationDTO reservationDTO);
}
