package booking_app_team_2.bookie.service;

import booking_app_team_2.bookie.domain.Accommodation;
import booking_app_team_2.bookie.domain.Guest;
import booking_app_team_2.bookie.domain.Reservation;
import booking_app_team_2.bookie.domain.ReservationStatus;
import booking_app_team_2.bookie.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {
    private ReservationRepository reservationRepository;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<Reservation> findAll() {
        return null;
    }

    @Override
    public Page<Reservation> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Reservation> findAllByReserveeAndStatusIn(Guest reservee, EnumSet<ReservationStatus> reservationStatuses) {
        return reservationRepository.findAllByReserveeAndStatusIn(reservee, reservationStatuses);
    }

    @Override
    public List<Reservation> findAllByAccommodationAndStatusIn(Accommodation accommodation,
                                                    EnumSet<ReservationStatus> reservationStatuses) {
        return reservationRepository.findAllByAccommodationAndStatusIn(accommodation, reservationStatuses);
    }

    @Override
    public Optional<Reservation> findOne(Long id) {
        return Optional.empty();
    }

    @Override
    public Reservation save(Reservation reservation) {
        return null;
    }

    @Override
    public void remove(Long id) {

    }
}
