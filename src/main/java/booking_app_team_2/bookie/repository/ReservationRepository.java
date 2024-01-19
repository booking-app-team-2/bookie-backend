package booking_app_team_2.bookie.repository;

import booking_app_team_2.bookie.domain.Accommodation;
import booking_app_team_2.bookie.domain.Guest;
import booking_app_team_2.bookie.domain.Reservation;
import booking_app_team_2.bookie.domain.ReservationStatus;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.EnumSet;
import java.util.List;

@Repository
public interface ReservationRepository extends GenericRepository<Reservation>, JpaSpecificationExecutor<Reservation> {
    List<Reservation> findAllByReserveeAndStatusIn(Guest reservee, EnumSet<ReservationStatus> reservationStatuses);

    List<Reservation> findAllByAccommodationAndStatusIn(Accommodation accommodation,
                                                        EnumSet<ReservationStatus> reservationStatuses);

    @Query("from Reservation r" +
            " where r.id != :#{#reservation.id} and r.accommodation = :#{#reservation.accommodation}" +
            " and r.status = 'Waiting'" +
            " and r.period.startDate <= :#{#reservation.period.endDate}" +
            " and :#{#reservation.period.startDate} <= r.period.endDate")
    List<Reservation> findAllByIntersectingPeriod(@Param("reservation") Reservation reservation);

    List<Reservation> findReservationsByAccommodation_Id(Long id);

    List<Reservation> findAllByReserveeAndStatusAndAccommodation_Id(Guest reservee, ReservationStatus status, Long accommodationId);
    
    int countByStatusAndReservee(ReservationStatus status, Guest reservee);
}
