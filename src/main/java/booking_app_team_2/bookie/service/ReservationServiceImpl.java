package booking_app_team_2.bookie.service;

import booking_app_team_2.bookie.domain.Accommodation;
import booking_app_team_2.bookie.domain.Guest;
import booking_app_team_2.bookie.domain.Reservation;
import booking_app_team_2.bookie.domain.ReservationStatus;
import booking_app_team_2.bookie.domain.*;
import booking_app_team_2.bookie.dto.ReservationDTO;
import booking_app_team_2.bookie.exception.HttpTransferException;
import booking_app_team_2.bookie.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.EnumSet;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {
    private ReservationRepository reservationRepository;

    private AccommodationService accommodationService;
    private UserService userService;
    private AccountVerificatorService accountVerificatorService;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository,
                                  AccommodationService accommodationService,
                                  UserService userService,
                                  AccountVerificatorService accountVerificatorService) {
        this.reservationRepository = reservationRepository;
        this.accommodationService = accommodationService;
        this.userService = userService;
        this.accountVerificatorService = accountVerificatorService;
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
    public void createReservation(ReservationDTO reservationDTO) {
        Optional<Accommodation> accommodationOptional =
                accommodationService.findOne(reservationDTO.getAccommodationId());
        if (accommodationOptional.isEmpty())
            throw new HttpTransferException(HttpStatus.NOT_FOUND, "A reservation cannot be created for a non-existing" +
                    " accommodation.");

        Accommodation accommodation = accommodationOptional.get();
        if (!accommodation.isApproved())
            throw new HttpTransferException(HttpStatus.BAD_REQUEST, "A reservation cannot be created for an " +
                    "unapproved accommodation.");

        int numberOfGuests = reservationDTO.getNumberOfGuests();
        if (!accommodation.canAccommodate(numberOfGuests))
            throw new HttpTransferException(HttpStatus.BAD_REQUEST, "This accommodation cannot accommodate such a " +
                    "number of guests.");

        Optional<User> userOptional = userService.findOne(reservationDTO.getReserveeId());
        if (userOptional.isEmpty())
            throw new HttpTransferException(HttpStatus.NOT_FOUND, "A non-existing user cannot create a reservation.");

        User user = userOptional.get();
        if (user.isBlocked())
            throw new HttpTransferException(HttpStatus.BAD_REQUEST, "A blocked user cannot create a reservation.");

        Optional<AccountVerificator> accountVerificatorOptional = accountVerificatorService.findOneByUser(user);
        if (accountVerificatorOptional.isEmpty() || !accountVerificatorOptional.get().isVerified())
            throw new HttpTransferException(HttpStatus.BAD_REQUEST, "A non-verified user cannot create a reservation.");

        if (!user.getRole().equals(UserRole.Guest))
            throw new HttpTransferException(HttpStatus.BAD_REQUEST, "Only guests can create reservations.");

        Period period = reservationDTO.getPeriod();
        Optional<AvailabilityPeriod> availabilityPeriodOptional = accommodation
                .getAvailabilityPeriods()
                .stream()
                .filter(availabilityPeriod -> availabilityPeriod.canFitPeriod(period))
                .findFirst();
        if (availabilityPeriodOptional.isEmpty())
            throw new HttpTransferException(HttpStatus.BAD_REQUEST, "This accommodation is not available in the " +
                    "requested period.");

        AvailabilityPeriod availabilityPeriod = availabilityPeriodOptional.get();

        int priceMultiplier = accommodation.isPricedPerGuest() ? numberOfGuests : 1;
        Reservation reservation = new Reservation(numberOfGuests, accommodation, (Guest) userOptional.get(), period,
                availabilityPeriod
                         .getPrice()
                         .multiply(BigDecimal.valueOf(period.getInDays()))
                         .multiply(BigDecimal.valueOf(priceMultiplier)));

        if (accommodation.isReservationAutoAccepted()) {
            reservation.setStatus(ReservationStatus.Accepted);

            if (availabilityPeriod.isPeriodEqualTo(period))
                accommodation.removeAvailabilityPeriod(availabilityPeriod);
            else if (availabilityPeriod.periodOverlapsBottomOnly(period))
                availabilityPeriod.getPeriod().setEndDate(period.getStartDate());
            else if (availabilityPeriod.periodOverlapsTopOnly(period))
                availabilityPeriod.getPeriod().setStartDate(period.getEndDate());
            else if (availabilityPeriod.periodExclusivelyOverlaps(period)) {
                LocalDate availabilityPeriodEndDate = availabilityPeriod.getPeriod().getEndDate();
                availabilityPeriod.getPeriod().setEndDate(period.getStartDate());
                 accommodation.addAvailabilityPeriod(
                         new AvailabilityPeriod(
                                 availabilityPeriod.getPrice(),
                                 new Period(period.getEndDate(), availabilityPeriodEndDate)
                         )
                 );
            }
            accommodationService.save(accommodation);
        }

        reservationRepository.save(reservation);
    }

    @Override
    public Reservation save(Reservation reservation) {
        return null;
    }

    @Override
    public void remove(Long id) {

    }

    @Autowired
    public void setReservationRepository(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Autowired
    public void setAccommodationService(AccommodationService accommodationService) {
        this.accommodationService = accommodationService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setAccountVerificatorService(AccountVerificatorService accountVerificatorService) {
        this.accountVerificatorService = accountVerificatorService;
    }
}
