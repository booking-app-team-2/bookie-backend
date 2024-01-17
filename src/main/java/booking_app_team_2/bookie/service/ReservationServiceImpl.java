package booking_app_team_2.bookie.service;

import booking_app_team_2.bookie.domain.Accommodation;
import booking_app_team_2.bookie.domain.Guest;
import booking_app_team_2.bookie.domain.Reservation;
import booking_app_team_2.bookie.domain.ReservationStatus;
import booking_app_team_2.bookie.domain.*;
import booking_app_team_2.bookie.dto.NumberOfCancelledReservationsDTO;
import booking_app_team_2.bookie.dto.ReservationDTO;
import booking_app_team_2.bookie.dto.ReservationGuestDTO;
import booking_app_team_2.bookie.dto.ReservationOwnerDTO;
import booking_app_team_2.bookie.exception.HttpTransferException;
import booking_app_team_2.bookie.repository.ReservationRepository;
import booking_app_team_2.bookie.util.TokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static booking_app_team_2.bookie.repository.ReservationSpecification.*;

import java.time.LocalDate;
import java.util.*;

import java.math.BigDecimal;

@Service
public class ReservationServiceImpl implements ReservationService {
    private ReservationRepository reservationRepository;

    private AccommodationService accommodationService;
    private UserService userService;
    private AccountVerificatorService accountVerificatorService;

    private final TokenUtils tokenUtils;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository,
                                  AccommodationService accommodationService,
                                  UserService userService,
                                  AccountVerificatorService accountVerificatorService,
                                  TokenUtils tokenUtils) {
        this.reservationRepository = reservationRepository;
        this.accommodationService = accommodationService;
        this.userService = userService;
        this.accountVerificatorService = accountVerificatorService;
        this.tokenUtils = tokenUtils;
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
    public List<Reservation> findAllByReserveeAndStatusIn(Guest reservee,
                                                          EnumSet<ReservationStatus> reservationStatuses) {
        return reservationRepository.findAllByReserveeAndStatusIn(reservee, reservationStatuses);
    }

    @Override
    public List<Reservation> findAllByAccommodationAndStatusIn(Accommodation accommodation,
                                                    EnumSet<ReservationStatus> reservationStatuses) {
        return reservationRepository.findAllByAccommodationAndStatusIn(accommodation, reservationStatuses);
    }

    @Override
    public List<ReservationGuestDTO> findAllForGuest(String name, Long startTimestamp, Long endTimestamp,
                                                     List<ReservationStatus> statuses,
                                                     HttpServletRequest httpServletRequest) {
        Guest reservee = (Guest) userService.findOne(tokenUtils.getIdFromToken(tokenUtils.getToken(httpServletRequest)))
                .orElseThrow(() -> new HttpTransferException(HttpStatus.NOT_FOUND,
                        "A non-existent guest cannot search and filter reservations."));

        if (reservee.isBlocked())
            throw new HttpTransferException(HttpStatus.BAD_REQUEST,
                    "A blocked guest cannot search and filter reservations.");

        Optional<AccountVerificator> accountVerificatorOptional = accountVerificatorService.findOneByUser(reservee);
        if(accountVerificatorOptional.isEmpty() || !accountVerificatorOptional.get().isVerified())
            throw new HttpTransferException(HttpStatus.BAD_REQUEST,
                    "A non-verified guest cannot search and filter reservations.");

        Period period = new Period(startTimestamp, endTimestamp);

        return reservationRepository
                .findAll(
                        hasAccommodationNameLike(name)
                                .and(hasPeriodBetween(period))
                                .and(hasStatusIn(statuses))
                                .and(hasReserveeEqualTo(reservee))
                )
                .stream()
                .map(ReservationGuestDTO::new)
                .toList();
    }

    @Override
    public List<ReservationOwnerDTO> findAllForOwner(String name, Long startTimestamp, Long endTimestamp,
                                                     List<ReservationStatus> statuses,
                                                     HttpServletRequest httpServletRequest) {
        Owner owner = (Owner) userService.findOne(tokenUtils.getIdFromToken(tokenUtils.getToken(httpServletRequest)))
                .orElseThrow(() -> new HttpTransferException(HttpStatus.NOT_FOUND,
                        "A non-existent owner cannot search and filter reservations."));

        if (owner.isBlocked())
            throw new HttpTransferException(HttpStatus.BAD_REQUEST,
                    "A blocked owner cannot search and filter reservations.");

        Optional<AccountVerificator> accountVerificatorOptional = accountVerificatorService.findOneByUser(owner);
        if(accountVerificatorOptional.isEmpty() || !accountVerificatorOptional.get().isVerified())
            throw new HttpTransferException(HttpStatus.BAD_REQUEST,
                    "A non-verified owner cannot search and filter reservations.");

        Period period = null;

        if (startTimestamp != null && endTimestamp != null)
            period = new Period(startTimestamp, endTimestamp);

        return reservationRepository
                .findAll(
                        hasAccommodationNameLike(name)
                                .and(hasPeriodBetween(period))
                                .and(hasStatusIn(statuses))
                                .and(hasAccommodationOwnerEqualTo(owner))
                )
                .stream()
                .map(ReservationOwnerDTO::new)
                .toList();
    }

    @Override
    public Optional<Reservation> findOne(Long id) {
        return Optional.empty();
    }

    @Override
    public NumberOfCancelledReservationsDTO getNumberOfCancelledReservations(Long reserveeId,
                                                                             HttpServletRequest httpServletRequest) {
        Owner owner = (Owner) userService.findOne(tokenUtils.getIdFromToken(tokenUtils.getToken(httpServletRequest)))
                .orElseThrow(() -> new HttpTransferException(HttpStatus.NOT_FOUND,
                        "A non-existent owner cannot get the number of cancelled reservations for a reservee."));

        if (owner.isBlocked())
            throw new HttpTransferException(HttpStatus.BAD_REQUEST,
                    "A blocked owner cannot get the number of cancelled reservations for a reservee.");

        Optional<AccountVerificator> accountVerificatorOptional = accountVerificatorService.findOneByUser(owner);
        if(accountVerificatorOptional.isEmpty() || !accountVerificatorOptional.get().isVerified())
            throw new HttpTransferException(HttpStatus.BAD_REQUEST,
                    "A non-verified owner cannot get the number of cancelled reservations for a reservee.");

        User user = userService
                .findOne(reserveeId)
                .orElseThrow(() -> new HttpTransferException(HttpStatus.NOT_FOUND, "Reservee not found."));

        if (!user.getRole().equals(UserRole.Guest))
            throw new HttpTransferException(HttpStatus.BAD_REQUEST, "User is not a reservee.");

        return new NumberOfCancelledReservationsDTO(
                reservationRepository.countByStatusAndReservee(ReservationStatus.Cancelled, (Guest) user)
        );
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

        Period period = new Period(reservationDTO.getPeriodDTO());
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
                availabilityPeriod.getPeriod().setEndDate(period.getStartDate().minusDays(1));
            else if (availabilityPeriod.periodOverlapsTopOnly(period))
                availabilityPeriod.getPeriod().setStartDate(period.getEndDate().plusDays(1));
            else if (availabilityPeriod.periodExclusivelyOverlaps(period)) {
                LocalDate availabilityPeriodEndDate = availabilityPeriod.getPeriod().getEndDate();
                availabilityPeriod.getPeriod().setEndDate(period.getStartDate().minusDays(1));
                 accommodation.addAvailabilityPeriod(
                         new AvailabilityPeriod(
                                 availabilityPeriod.getPrice(),
                                 new Period(period.getEndDate().plusDays(1), availabilityPeriodEndDate)
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

    private void freeReservationPeriod(Reservation reservation) {
        Accommodation accommodation = reservation.getAccommodation();
        accommodation.addAvailabilityPeriod(
                new AvailabilityPeriod(reservation.getPricePerDayPerGuest(), reservation.getPeriod())
        );

        accommodationService.save(accommodation);
    }

    @Override
    public void cancelReservation(Long id, HttpServletRequest httpServletRequest) {
        Reservation reservation = reservationRepository
                .findById(id)
                .orElseThrow(() -> new HttpTransferException(HttpStatus.NOT_FOUND, "Reservation not found."));

        Guest reservee = (Guest) userService
                .findOne(tokenUtils.getIdFromToken(tokenUtils.getToken(httpServletRequest)))
                .orElseThrow(() -> new HttpTransferException(HttpStatus.NOT_FOUND,
                        "A non-existent guest cannot cancel a reservation."));

        if (reservee.isBlocked())
            throw new HttpTransferException(HttpStatus.BAD_REQUEST, "A blocked guest cannot cancel a reservation.");

        Optional<AccountVerificator> accountVerificatorOptional = accountVerificatorService.findOneByUser(reservee);
        if (accountVerificatorOptional.isEmpty() || !accountVerificatorOptional.get().isVerified())
            throw new HttpTransferException(HttpStatus.BAD_REQUEST,
                    "A non-verified guest cannot cancel a reservation.");

        if (!reservation.getReservee().equals(reservee))
            throw new HttpTransferException(HttpStatus.BAD_REQUEST,
                    "Only the reservee that made the reservation can cancel it.");

        if (!reservation.isCancellable())
            throw new HttpTransferException(
                    HttpStatus.BAD_REQUEST,
                    "Only an accepted reservation that has not yet reached the cancellation deadline can be cancelled."
            );

        freeReservationPeriod(reservation);

        reservation.setStatus(ReservationStatus.Cancelled);
        reservationRepository.save(reservation);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void remove(Long id, HttpServletRequest httpServletRequest) {
        Reservation reservation = reservationRepository
                .findById(id)
                .orElseThrow(() -> new HttpTransferException(HttpStatus.NOT_FOUND, "Reservation not found."));

        Guest reservee = (Guest) userService
                .findOne(tokenUtils.getIdFromToken(tokenUtils.getToken(httpServletRequest)))
                .orElseThrow(() -> new HttpTransferException(HttpStatus.NOT_FOUND,
                        "A non-existent guest cannot delete a reservation."));

        if (reservee.isBlocked())
            throw new HttpTransferException(HttpStatus.BAD_REQUEST, "A blocked guest cannot delete a reservation.");

        Optional<AccountVerificator> accountVerificatorOptional = accountVerificatorService.findOneByUser(reservee);
        if (accountVerificatorOptional.isEmpty() || !accountVerificatorOptional.get().isVerified())
            throw new HttpTransferException(HttpStatus.BAD_REQUEST, "A non-verified guest cannot delete a reservation");

        if (!reservation.getReservee().equals(reservee))
            throw new HttpTransferException(HttpStatus.BAD_REQUEST,
                    "Only the reservee that made the reservation can delete it.");

        if (reservation.getStatus() != ReservationStatus.Waiting)
            throw new HttpTransferException(HttpStatus.BAD_REQUEST,
                    "Only reservations that have not already been accepted, declined or cancelled can be deleted.");

        reservationRepository.delete(reservation);
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
