package booking_app_team_2.bookie.service;

import booking_app_team_2.bookie.domain.*;
import booking_app_team_2.bookie.dto.AccommodationAutoAcceptDTO;
import booking_app_team_2.bookie.dto.*;
import booking_app_team_2.bookie.dto.AccommodationBasicInfoDTO;
import booking_app_team_2.bookie.dto.AccommodationDTO;
import booking_app_team_2.bookie.dto.AccommodationApprovalDTO;
import booking_app_team_2.bookie.exception.HttpTransferException;
import booking_app_team_2.bookie.repository.AccommodationRepository;
import booking_app_team_2.bookie.repository.ReservationRepository;
import booking_app_team_2.bookie.util.TokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccommodationServiceImpl implements AccommodationService {
    private AccommodationRepository accommodationRepository;
    private ReservationRepository reservationRepository;

    private UserService userService;
    private AccountVerificatorService accountVerificatorService;

    private final TokenUtils tokenUtils;

    @Autowired
    public AccommodationServiceImpl(AccommodationRepository accommodationRepository,
                                    ReservationRepository reservationRepository,
                                    UserService userService, TokenUtils tokenUtils,
                                    AccountVerificatorService accountVerificatorService) {
        this.accommodationRepository = accommodationRepository;
        this.reservationRepository=reservationRepository;
        this.userService = userService;
        this.accountVerificatorService = accountVerificatorService;
        this.tokenUtils = tokenUtils;
    }

    @Override
    public List<Accommodation> findSearched(String location, int numberOfGuests, Long startDate, Long endDate) {
        List<Accommodation> accommodations = accommodationRepository.findAll();
        List<Accommodation> newAccommodations = new ArrayList<>(Collections.emptyList());
        Period period = new Period(startDate, endDate);
        if (period.getStartDate() == null && period.getEndDate() == null) {
            return accommodations;
        }
        for (Accommodation accommodation : accommodations) {
            if ((numberOfGuests >= accommodation.getMinimumGuests() && numberOfGuests <= accommodation.getMaximumGuests()) || (numberOfGuests == 0)) {
                for (AvailabilityPeriod availabilityPeriod : accommodation.getAvailabilityPeriods()) {
                    if (availabilityPeriod.getPeriod().overlaps(period)) {
                        newAccommodations.add(accommodation);
                        break;
                    }
                }
            }
        }
        return newAccommodations;
    }

    @Override
    public List<AccommodationDTO> getAll() {
        List<Accommodation> accommodations = accommodationRepository.findAll();
        return accommodations.stream()
                .map(accommodation -> new AccommodationDTO(accommodation.getId(), accommodation.getName(), accommodation.getDescription(), accommodation.getMinimumGuests(), accommodation.getMaximumGuests(), accommodation.getLocation(), accommodation.getAmenities(), accommodation.getAvailabilityPeriods(), accommodation.getImages(), accommodation.getReservationCancellationDeadline(), accommodation.getType(), accommodation.isReservationAutoAccepted()))
                .collect(Collectors.toList());
    }

    @Override
    public AccommodationBasicInfoDTO updateAccommodationBasicInfo(Accommodation accommodation, AccommodationBasicInfoDTO accommodationBasicInfoDTO) {
        accommodation.setName(accommodationBasicInfoDTO.getName());
        accommodation.setDescription(accommodationBasicInfoDTO.getDescription());
        accommodation.setLocation(accommodationBasicInfoDTO.getLocation());
        accommodation.setAmenities(accommodationBasicInfoDTO.getAmenities());
        for (Reservation reservation : reservationRepository.findReservationsByAccommodation_Id(accommodation.getId())) {
            if (accommodationBasicInfoDTO.getMinimumGuests() > reservation.getNumberOfGuests() || accommodationBasicInfoDTO.getMaximumGuests() < reservation.getNumberOfGuests()) {
                return null;
            }
            for (AvailabilityPeriod period : accommodation.getAvailabilityPeriods()) {
                if (period.getPeriod().overlaps(reservation.getPeriod())) {
                    boolean flag = true;
                    for (AvailabilityPeriodDTO afterPeriod : accommodationBasicInfoDTO.getAvailabilityPeriods()) {
                        Period newPeriod = new Period(afterPeriod.getPeriod().getStartTimestamp(), afterPeriod.getPeriod().getEndTimestamp());
                        if (afterPeriod.getId().equals(period.getId()) && newPeriod.getEndDate().toString() == period.getPeriod().getEndDate().toString() && newPeriod.getStartDate().toString() == period.getPeriod().getStartDate().toString() && afterPeriod.getPrice().setScale(2).equals(period.getPrice())) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        return null;
                    }
                }
            }
        }
        accommodation.getAvailabilityPeriods().clear();
        Set<AvailabilityPeriod> availabilityPeriods = new HashSet<>();
        for (AvailabilityPeriodDTO availabilityPeriod : accommodationBasicInfoDTO.getAvailabilityPeriods()) {
            availabilityPeriods.add(new AvailabilityPeriod(availabilityPeriod));
        }
        accommodation.getAvailabilityPeriods().addAll(availabilityPeriods);
        accommodation.setType(accommodationBasicInfoDTO.getType());
        accommodation.setMinimumGuests(accommodationBasicInfoDTO.getMinimumGuests());
        accommodation.setMaximumGuests(accommodationBasicInfoDTO.getMaximumGuests());
        this.save(accommodation);
        return accommodationBasicInfoDTO;
    }

    @Override
    public List<Accommodation> findAll() {
        return accommodationRepository.findAll();
    }

    @Override
    public Page<Accommodation> findAll(Pageable pageable) {
        return accommodationRepository.findAll(pageable);
    }

    @Override
    public List<Accommodation> findAllByIsApproved(boolean isApproved) {
        return accommodationRepository.findAllByIsApproved(isApproved);
    }

    @Override
    public Optional<Accommodation> findOne(Long id) {
        return accommodationRepository.findById(id);
    }

    @Override
    public Accommodation save(Accommodation accommodation) {
        return accommodationRepository.save(accommodation);
    }

    @Override
    public void updateIsApproved(Long id, AccommodationApprovalDTO accommodationApprovalDTO) {
        Optional<Accommodation> accommodationOptional = accommodationRepository.findById(id);
        if (accommodationOptional.isEmpty())
            throw new HttpTransferException(HttpStatus.NOT_FOUND, "No such accommodation exists.");

        Accommodation accommodation = accommodationOptional.get();

        accommodation.setApproved(accommodationApprovalDTO.isApproved());
        accommodationRepository.save(accommodation);
    }

    @Override
    public void updateAutoAccept(Long id, AccommodationAutoAcceptDTO accommodationAutoAcceptDTO,
                                 HttpServletRequest httpServletRequest) {
        Accommodation accommodation = accommodationRepository
                .findById(id)
                .orElseThrow(() -> new HttpTransferException(HttpStatus.NOT_FOUND, "Accommodation not found."));

        Owner owner = (Owner) userService.findOne(tokenUtils.getIdFromToken(tokenUtils.getToken(httpServletRequest)))
                .orElseThrow(() -> new HttpTransferException(HttpStatus.NOT_FOUND,
                        "A non-existent owner cannot change the reservation auto-acceptance."));

        if (owner.isBlocked())
            throw new HttpTransferException(HttpStatus.BAD_REQUEST,
                    "A blocked owner cannot change the reservation auto-acceptance.");

        Optional<AccountVerificator> accountVerificatorOptional = accountVerificatorService.findOneByUser(owner);
        if (accountVerificatorOptional.isEmpty() || !accountVerificatorOptional.get().isVerified())
            throw new HttpTransferException(HttpStatus.BAD_REQUEST,
                    "A non-verified owner cannot change the reservation auto-acceptance.");

        if (!owner.owns(accommodation))
            throw new HttpTransferException(HttpStatus.BAD_REQUEST,
                    "Only the owner of the accommodation can change it's reservation auto-acceptance status.");

        accommodation.setReservationAutoAccepted(accommodationAutoAcceptDTO.isReservationAutoAccepted());
        accommodationRepository.save(accommodation);
    }

    @Override
    public void remove(Long id) {
        accommodationRepository.deleteById(id);
    }

    @Autowired
    public void setAccommodationRepository(AccommodationRepository accommodationRepository) {
        this.accommodationRepository = accommodationRepository;
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
