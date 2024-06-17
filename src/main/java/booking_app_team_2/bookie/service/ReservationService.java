package booking_app_team_2.bookie.service;

import booking_app_team_2.bookie.domain.Accommodation;
import booking_app_team_2.bookie.domain.Guest;
import booking_app_team_2.bookie.domain.Reservation;
import booking_app_team_2.bookie.domain.ReservationStatus;
import booking_app_team_2.bookie.dto.NumberOfCancelledReservationsDTO;
import booking_app_team_2.bookie.dto.ReservationDTO;
import booking_app_team_2.bookie.dto.ReservationGuestDTO;
import booking_app_team_2.bookie.dto.ReservationOwnerDTO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.EnumSet;
import java.util.List;

public interface ReservationService extends GenericService<Reservation> {
    List<Reservation> findAllByReserveeAndStatusIn(Guest reservee, EnumSet<ReservationStatus> reservationStatuses);

    List<Reservation> findAllByAccommodationAndStatusIn(Accommodation accommodation,
                                                        EnumSet<ReservationStatus> reservationStatuses);

    List<ReservationGuestDTO> findAllForGuest(String name, Long startTimestamp, Long endTimestamp,
                                              List<ReservationStatus> statuses, HttpServletRequest httpServletRequest);

    List<ReservationOwnerDTO> findAllForOwner(String name, Long startTimestamp, Long endTimestamp,
                                              List<ReservationStatus> statuses, HttpServletRequest httpServletRequest);

    NumberOfCancelledReservationsDTO getNumberOfCancelledReservations(Long reserveeId,
                                                                      HttpServletRequest httpServletRequest);

    ReservationDTO createReservation(ReservationDTO reservationDTO);

    void acceptReservation(Long id, HttpServletRequest httpServletRequest);

    void declineReservation(Long id, HttpServletRequest httpServletRequest);

    void cancelReservation(Long id, HttpServletRequest httpServletRequest);

    void remove(Long id, HttpServletRequest httpServletRequest);
}
