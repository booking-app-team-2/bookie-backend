package booking_app_team_2.bookie.repository;

import booking_app_team_2.bookie.domain.Accommodation;
import booking_app_team_2.bookie.domain.Guest;
import booking_app_team_2.bookie.domain.Reservation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends GenericRepository<Reservation> {
    List<Reservation> findAllByReservee(Guest reservee);

    List<Reservation> findAllByAccommodation(Accommodation accommodation);
}
