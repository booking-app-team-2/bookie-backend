package booking_app_team_2.bookie.service;

import booking_app_team_2.bookie.domain.*;
import booking_app_team_2.bookie.dto.PeriodDTO;
import booking_app_team_2.bookie.dto.ReservationDTO;
import booking_app_team_2.bookie.exception.HttpTransferException;
import booking_app_team_2.bookie.repository.AccommodationRepository;
import booking_app_team_2.bookie.repository.AccountVerificatorRepository;
import booking_app_team_2.bookie.repository.ReservationRepository;
import booking_app_team_2.bookie.repository.UserRepository;
import booking_app_team_2.bookie.validation.StartTimestampBeforeEndTimestamp;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
public class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @MockBean
    private AccommodationRepository accommodationRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AccountVerificatorRepository accountVerificatorRepository;

    @MockBean
    private ReservationRepository reservationRepository;

    @Captor
    private ArgumentCaptor<Reservation> reservationArgumentCaptor;

    @Test
    @DisplayName("Test should give an error for non existent accommodation")
    public void createReservationWithInvalidAccommodation(){
        ReservationDTO reservationDTO=new ReservationDTO(3,123L,1L,new PeriodDTO());
        Mockito.when(accommodationRepository.findById(123L)).thenReturn(Optional.empty());
        HttpTransferException exception = assertThrows(HttpTransferException.class, () -> {
            reservationService.createReservation(reservationDTO);
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("A reservation cannot be created for a non-existing accommodation.", exception.getMessage());
    }

    @Test
    @DisplayName("Test should give an error for non approved accommodation")
    public void createReservationWithUnapprovedAccommodation(){
        Accommodation accommodation=new Accommodation();
        accommodation.setId(123L);
        accommodation.setApproved(false);
        ReservationDTO reservationDTO=new ReservationDTO(3,123L,1L,new PeriodDTO());
        Mockito.when(accommodationRepository.findById(123L)).thenReturn(Optional.of(accommodation));
        HttpTransferException exception = assertThrows(HttpTransferException.class, () -> {
            reservationService.createReservation(reservationDTO);
        });
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("A reservation cannot be created for an unapproved accommodation.", exception.getMessage());
    }

    @Test
    @DisplayName("Test should give an error for invalid number of guests")
    public void createReservationWithInvalidGuestNumber(){
        Accommodation accommodation=new Accommodation();
        accommodation.setId(123L);
        accommodation.setApproved(true);
        accommodation.setMinimumGuests(2);
        accommodation.setMaximumGuests(5);
        ReservationDTO reservationDTO=new ReservationDTO(1,123L,1L,new PeriodDTO());
        Mockito.when(accommodationRepository.findById(123L)).thenReturn(Optional.of(accommodation));
        HttpTransferException exception = assertThrows(HttpTransferException.class, () -> {
            reservationService.createReservation(reservationDTO);
        });
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("This accommodation cannot accommodate such a number of guests.", exception.getMessage());
    }

    @Test
    @DisplayName("Test should give an error for invalid user")
    public void createReservationWithInvalidUser(){
        Accommodation accommodation=new Accommodation();
        accommodation.setId(123L);
        accommodation.setApproved(true);
        accommodation.setMinimumGuests(2);
        accommodation.setMaximumGuests(5);
        ReservationDTO reservationDTO=new ReservationDTO(3,123L,1L,new PeriodDTO());
        Mockito.when(accommodationRepository.findById(123L)).thenReturn(Optional.of(accommodation));
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());
        HttpTransferException exception = assertThrows(HttpTransferException.class, () -> {
            reservationService.createReservation(reservationDTO);
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("A non-existing user cannot create a reservation.", exception.getMessage());
    }

    @Test
    @DisplayName("Test should give an error for blocked user")
    public void createReservationWithBlockedUser(){
        Accommodation accommodation=new Accommodation();
        accommodation.setId(123L);
        accommodation.setApproved(true);
        accommodation.setMinimumGuests(2);
        accommodation.setMaximumGuests(5);
        User user=new User();
        user.setId(1L);
        user.setBlocked(true);
        ReservationDTO reservationDTO=new ReservationDTO(3,123L,1L,new PeriodDTO());
        Mockito.when(accommodationRepository.findById(123L)).thenReturn(Optional.of(accommodation));
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        HttpTransferException exception = assertThrows(HttpTransferException.class, () -> {
            reservationService.createReservation(reservationDTO);
        });
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("A blocked user cannot create a reservation.", exception.getMessage());
    }

    @Test
    @DisplayName("Test should give an error for non-verified user with no verificator")
    public void createReservationWithNonVerifiedUserWithoutVerificator(){
        Accommodation accommodation=new Accommodation();
        accommodation.setId(123L);
        accommodation.setApproved(true);
        accommodation.setMinimumGuests(2);
        accommodation.setMaximumGuests(5);
        User user=new User();
        user.setId(1L);
        user.setBlocked(false);
        ReservationDTO reservationDTO=new ReservationDTO(3,123L,1L,new PeriodDTO());
        Mockito.when(accommodationRepository.findById(123L)).thenReturn(Optional.of(accommodation));
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(accountVerificatorRepository.findOneByUser(user)).thenReturn(Optional.empty());
        HttpTransferException exception = assertThrows(HttpTransferException.class, () -> {
            reservationService.createReservation(reservationDTO);
        });
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("A non-verified user cannot create a reservation.", exception.getMessage());
    }

    @Test
    @DisplayName("Test should give an error for non-verified user with verificator")
    public void createReservationWithNonVerifiedUserWithVerificator(){
        Accommodation accommodation=new Accommodation();
        accommodation.setId(123L);
        accommodation.setApproved(true);
        accommodation.setMinimumGuests(2);
        accommodation.setMaximumGuests(5);
        User user=new User();
        user.setId(1L);
        user.setBlocked(false);
        AccountVerificator accountVerificator=new AccountVerificator();
        accountVerificator.setVerified(false);
        ReservationDTO reservationDTO=new ReservationDTO(3,123L,1L,new PeriodDTO());
        Mockito.when(accommodationRepository.findById(123L)).thenReturn(Optional.of(accommodation));
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(accountVerificatorRepository.findOneByUser(user)).thenReturn(Optional.of(accountVerificator));
        HttpTransferException exception = assertThrows(HttpTransferException.class, () -> {
            reservationService.createReservation(reservationDTO);
        });
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("A non-verified user cannot create a reservation.", exception.getMessage());
    }
    @Test
    @DisplayName("Test should give an error for user that is not a guest")
    public void createReservationWithNonGuestUser(){
        Accommodation accommodation=new Accommodation();
        accommodation.setId(123L);
        accommodation.setApproved(true);
        accommodation.setMinimumGuests(2);
        accommodation.setMaximumGuests(5);

        User user=new User();
        user.setId(1L);
        user.setBlocked(false);
        user.setRole(UserRole.Admin);

        AccountVerificator accountVerificator=new AccountVerificator();
        accountVerificator.setVerified(true);

        ReservationDTO reservationDTO=new ReservationDTO(3,123L,1L,new PeriodDTO());

        Mockito.when(accommodationRepository.findById(123L)).thenReturn(Optional.of(accommodation));
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(accountVerificatorRepository.findOneByUser(user)).thenReturn(Optional.of(accountVerificator));

        HttpTransferException exception = assertThrows(HttpTransferException.class, () -> {
            reservationService.createReservation(reservationDTO);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Only guests can create reservations.", exception.getMessage());
    }

    @Test
    @DisplayName("Test should give an error for no available period")
    public void createReservationWithNoAvailablePeriod(){
        Accommodation accommodation=new Accommodation();
        accommodation.setId(123L);
        accommodation.setApproved(true);
        accommodation.setMinimumGuests(2);
        accommodation.setMaximumGuests(5);
        Set<AvailabilityPeriod> availabilityPeriods = new HashSet<>();
        accommodation.setAvailabilityPeriods(availabilityPeriods);

        User user=new User();
        user.setId(1L);
        user.setBlocked(false);
        user.setRole(UserRole.Guest);

        AccountVerificator accountVerificator=new AccountVerificator();
        accountVerificator.setVerified(true);

        LocalDate start = LocalDate.of(2024, 1, 20);
        LocalDate end = LocalDate.of(2024, 1, 23);
        Period period=new Period();
        period.setStartDate(start);
        period.setEndDate(end);
        PeriodDTO periodDTO=new PeriodDTO(period);

        ReservationDTO reservationDTO=new ReservationDTO(3,123L,1L,periodDTO);

        Mockito.when(accommodationRepository.findById(123L)).thenReturn(Optional.of(accommodation));
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(accountVerificatorRepository.findOneByUser(user)).thenReturn(Optional.of(accountVerificator));

        HttpTransferException exception = assertThrows(HttpTransferException.class, () -> {
            reservationService.createReservation(reservationDTO);
        });

        //if there is no availability period in Accommodation then the reservation period cannot be put anywhere
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("This accommodation is not available in the requested period.", exception.getMessage());
    }

    @Test
    @DisplayName("Test should create a reservation")
    public void createReservation(){
        Accommodation accommodation=new Accommodation();
        accommodation.setId(123L);
        accommodation.setApproved(true);
        accommodation.setMinimumGuests(2);
        accommodation.setMaximumGuests(5);
        accommodation.setPricedPerGuest(true);
        accommodation.setReservationAutoAccepted(false);
        Set<AvailabilityPeriod> availabilityPeriods = new HashSet<>();
        AvailabilityPeriod availabilityPeriod=new AvailabilityPeriod();
        LocalDate start = LocalDate.of(2024, 1, 19);
        LocalDate end = LocalDate.of(2024, 1, 25);
        Period period=new Period();
        period.setStartDate(start);
        period.setEndDate(end);
        availabilityPeriod.setPeriod(period);
        availabilityPeriod.setPrice(BigDecimal.valueOf(30));
        availabilityPeriods.add(availabilityPeriod);
        accommodation.setAvailabilityPeriods(availabilityPeriods);

        User user=new Guest();
        user.setId(1L);
        user.setBlocked(false);
        user.setRole(UserRole.Guest);

        AccountVerificator accountVerificator=new AccountVerificator();
        accountVerificator.setVerified(true);

        LocalDate newStart = LocalDate.of(2024, 1, 20);
        LocalDate newEnd = LocalDate.of(2024, 1, 23);
        Period newPeriod=new Period();
        newPeriod.setStartDate(newStart);
        newPeriod.setEndDate(newEnd);
        PeriodDTO periodDTO=new PeriodDTO(newPeriod);

        ReservationDTO reservationDTO=new ReservationDTO(3,123L,1L,periodDTO);

        Mockito.when(accommodationRepository.findById(123L)).thenReturn(Optional.of(accommodation));
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(accountVerificatorRepository.findOneByUser(user)).thenReturn(Optional.of(accountVerificator));

        reservationService.createReservation(reservationDTO);

        Mockito.verify(reservationRepository, times(1)).save(reservationArgumentCaptor.capture());
    }

}
