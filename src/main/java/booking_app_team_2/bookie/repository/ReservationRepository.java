package booking_app_team_2.bookie.repository;

import booking_app_team_2.bookie.domain.Accommodation;
import booking_app_team_2.bookie.domain.Guest;
import booking_app_team_2.bookie.domain.Reservation;
import booking_app_team_2.bookie.domain.ReservationStatus;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.EnumSet;
import java.util.List;

@Repository
public interface ReservationRepository extends GenericRepository<Reservation>, JpaSpecificationExecutor<Reservation> {
    List<Reservation> findAllByReserveeAndStatusIn(Guest reservee, EnumSet<ReservationStatus> reservationStatuses);

    List<Reservation> findAllByAccommodationAndStatusIn(Accommodation accommodation,
                                                        EnumSet<ReservationStatus> reservationStatuses);

    List<Reservation> findReservationsByAccommodation_Id(Long id);

    List<Reservation> findAllByReserveeAndStatusAndAccommodation_Id(Guest reservee, ReservationStatus status, Long accommodationId);
}
