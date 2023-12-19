package booking_app_team_2.bookie.service;

import booking_app_team_2.bookie.domain.Accommodation;
import booking_app_team_2.bookie.domain.Guest;
import booking_app_team_2.bookie.domain.Reservation;

import java.util.List;

public interface ReservationService extends GenericService<Reservation> {
    List<Reservation> findAllByReservee(Guest reservee);

    List<Reservation> findAllByAccommodation(Accommodation accommodation);
}
