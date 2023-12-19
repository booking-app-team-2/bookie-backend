package booking_app_team_2.bookie.repository;

import booking_app_team_2.bookie.domain.Reservation;

import java.util.List;

public interface ReservationRepository extends GenericRepository<Reservation> {
    List<Reservation> findReservationsByAccommodation_Id(Long id);
}
