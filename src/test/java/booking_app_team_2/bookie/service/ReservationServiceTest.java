package booking_app_team_2.bookie.service;

import booking_app_team_2.bookie.domain.*;
import booking_app_team_2.bookie.exception.HttpTransferException;
import booking_app_team_2.bookie.repository.ReservationRepository;
import booking_app_team_2.bookie.util.TokenUtils;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReservationServiceTest {
    @Autowired
    private ReservationService reservationService;

    @MockBean
    private ReservationRepository reservationRepository;

    @MockBean
    private AccommodationService accommodationService;

    @MockBean
    private UserService userService;

    @MockBean
    private AccountVerificatorService accountVerificatorService;

    @MockBean
    private TokenUtils tokenUtils;

    @Captor
    private ArgumentCaptor<Reservation> reservationCaptor;

    // TODO: Finish this

    @Test
    @Order(1)
    @DisplayName("Test acceptReservation")
    public void testAcceptReservation() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer token");

        Accommodation accommodation =
                new Accommodation(
                        "test",
                        "test",
                        new Location(),
                        new HashSet<>(),
                        new HashSet<>(),
                        1,
                        1,
                        1,
                        AccommodationType.Apartment,
                        true,
                        true,
                        new HashSet<>()
                );
        Reservation reservation =
                new Reservation(
                        1,
                        accommodation,
                        new Guest(),
                        new Period(LocalDate.now().plusDays(5), LocalDate.now().plusDays(10)),
                        BigDecimal.valueOf(1)
                );
        reservation.setStatus(ReservationStatus.Waiting);
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(tokenUtils.getToken(request)).thenReturn("token");
        when(tokenUtils.getIdFromToken("token")).thenReturn(1L);

        Set<Accommodation> accommodations = new HashSet<>();
        accommodations.add(accommodation);
        Owner owner =
                new Owner(
                        1L,
                        "test",
                        "test",
                        "test",
                        "test",
                        "test",
                        "test",
                        UserRole.Owner,
                        false,
                        accommodations,
                        false,
                        false,
                        false,
                        false
                );
        when(userService.findOne(1L)).thenReturn(Optional.of(owner));

        AccountVerificator accountVerificator = new AccountVerificator(123, owner);
        accountVerificator.setVerified(true);
        when(accountVerificatorService.findOneByUser(owner)).thenReturn(Optional.of(accountVerificator));

        HttpTransferException exception =
                assertThrows(HttpTransferException.class, () -> reservationService.acceptReservation(1L, request));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals("This accommodation is not available in the requested period.", exception.getMessage());

        verify(reservationRepository, atMostOnce()).findById(1L);
        verify(tokenUtils, atMostOnce()).getToken(request);
        verify(tokenUtils, atMostOnce()).getIdFromToken(tokenUtils.getToken(request));
        verify(userService, atMostOnce()).findOne(tokenUtils.getIdFromToken(tokenUtils.getToken(request)));
        verify(accountVerificatorService, atMostOnce()).findOneByUser(owner);
        verifyNoInteractions(accommodationService);
        verifyNoMoreInteractions(reservationRepository);
    }

    @Test
    @Order(2)
    @DisplayName("Test acceptReservation when a reservation has not been found")
    public void testAcceptReservationWhenReservationHasNotBeenFound() {
        MockHttpServletRequest request = new MockHttpServletRequest();

        when(reservationRepository.findById(0L)).thenReturn(Optional.empty());

        HttpTransferException exception =
                assertThrows(HttpTransferException.class, () -> reservationService.acceptReservation(0L, request));
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals("Reservation not found.", exception.getMessage());

        verify(reservationRepository, atMostOnce()).findById(0L);
        verifyNoInteractions(tokenUtils);
        verifyNoInteractions(userService);
        verifyNoInteractions(accountVerificatorService);
        verifyNoInteractions(accommodationService);
        verifyNoMoreInteractions(reservationRepository);
    }

    @Test
    @Order(3)
    @DisplayName("Test acceptReservation when token is null")
    public void testAcceptReservationWhenTokenIsNull() {
        MockHttpServletRequest request = new MockHttpServletRequest();

        Reservation reservation = new Reservation();
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(tokenUtils.getToken(request)).thenReturn(null);
        when(tokenUtils.getIdFromToken(null)).thenReturn(null);
        when(userService.findOne(null)).thenReturn(Optional.empty());

        HttpTransferException exception =
                assertThrows(HttpTransferException.class, () -> reservationService.acceptReservation(1L, request));
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals("A non-existent owner cannot accept reservations.", exception.getMessage());

        verify(reservationRepository, atMostOnce()).findById(1L);
        verify(tokenUtils, atMostOnce()).getToken(request);
        verify(tokenUtils, atMostOnce()).getIdFromToken(tokenUtils.getToken(request));
        verify(userService, atMostOnce()).findOne(tokenUtils.getIdFromToken(tokenUtils.getToken(request)));
        verifyNoInteractions(accountVerificatorService);
        verifyNoInteractions(accommodationService);
        verifyNoMoreInteractions(reservationRepository);
    }

    @Test
    @Order(4)
    @DisplayName("Test acceptReservation when Id from token is null")
    public void testAcceptReservationWhenIdFromTokenIsNull() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer invalid.token");

        Reservation reservation = new Reservation();
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(tokenUtils.getToken(request)).thenReturn("invalid.token");
        when(tokenUtils.getIdFromToken("invalid.token")).thenReturn(null);
        when(userService.findOne(null)).thenReturn(Optional.empty());

        HttpTransferException exception =
                assertThrows(HttpTransferException.class, () -> reservationService.acceptReservation(1L, request));
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals("A non-existent owner cannot accept reservations.", exception.getMessage());

        verify(reservationRepository, atMostOnce()).findById(1L);
        verify(tokenUtils, atMostOnce()).getToken(request);
        verify(tokenUtils, atMostOnce()).getIdFromToken(tokenUtils.getToken(request));
        verify(userService, atMostOnce()).findOne(tokenUtils.getIdFromToken(tokenUtils.getToken(request)));
        verifyNoInteractions(accountVerificatorService);
        verifyNoInteractions(accommodationService);
        verifyNoMoreInteractions(reservationRepository);
    }

    @Test
    @Order(5)
    @DisplayName("Test acceptReservation when owner does not exist")
    public void testAcceptReservationWhenOwnerDoesNotExist() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer token");

        Reservation reservation = new Reservation();
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(tokenUtils.getToken(request)).thenReturn("token");
        when(tokenUtils.getIdFromToken("token")).thenReturn(0L);
        when(userService.findOne(0L)).thenReturn(Optional.empty());

        HttpTransferException exception =
                assertThrows(HttpTransferException.class, () -> reservationService.acceptReservation(1L, request));
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals("A non-existent owner cannot accept reservations.", exception.getMessage());

        verify(reservationRepository, atMostOnce()).findById(1L);
        verify(tokenUtils, atMostOnce()).getToken(request);
        verify(tokenUtils, atMostOnce()).getIdFromToken(tokenUtils.getToken(request));
        verify(userService, atMostOnce()).findOne(tokenUtils.getIdFromToken(tokenUtils.getToken(request)));
        verifyNoInteractions(accountVerificatorService);
        verifyNoInteractions(accommodationService);
        verifyNoMoreInteractions(reservationRepository);
    }

    @Test
    @Order(6)
    @DisplayName("Test acceptReservation when owner is blocked")
    public void testAcceptReservationWhenOwnerIsBlocked() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer token");

        Reservation reservation = new Reservation();
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(tokenUtils.getToken(request)).thenReturn("token");
        when(tokenUtils.getIdFromToken("token")).thenReturn(1L);

        Owner owner =
                new Owner(
                        1L,
                        "test",
                        "test",
                        "test",
                        "test",
                        "test",
                        "test",
                        UserRole.Owner,
                        true,
                        new HashSet<>(),
                        false,
                        false,
                        false,
                        false
                );
        when(userService.findOne(1L)).thenReturn(Optional.of(owner));

        HttpTransferException exception =
                assertThrows(HttpTransferException.class, () -> reservationService.acceptReservation(1L, request));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals("A blocked owner cannot accept reservations.", exception.getMessage());

        verify(reservationRepository, atMostOnce()).findById(1L);
        verify(tokenUtils, atMostOnce()).getToken(request);
        verify(tokenUtils, atMostOnce()).getIdFromToken(tokenUtils.getToken(request));
        verify(userService, atMostOnce()).findOne(tokenUtils.getIdFromToken(tokenUtils.getToken(request)));
        verifyNoInteractions(accountVerificatorService);
        verifyNoInteractions(accommodationService);
        verifyNoMoreInteractions(reservationRepository);
    }

    @Test
    @Order(7)
    @DisplayName("Test acceptReservation when owner has no verificator")
    public void testAcceptReservationWhenOwnerHasNoVerificator() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer token");

        Reservation reservation = new Reservation();
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(tokenUtils.getToken(request)).thenReturn("token");
        when(tokenUtils.getIdFromToken("token")).thenReturn(1L);

        Owner owner =
                new Owner(
                        1L,
                        "test",
                        "test",
                        "test",
                        "test",
                        "test",
                        "test",
                        UserRole.Owner,
                        false,
                        new HashSet<>(),
                        false,
                        false,
                        false,
                        false
                );
        when(userService.findOne(1L)).thenReturn(Optional.of(owner));
        when(accountVerificatorService.findOneByUser(owner)).thenReturn(Optional.empty());

        HttpTransferException exception =
                assertThrows(HttpTransferException.class, () -> reservationService.acceptReservation(1L, request));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals("A non-verified owner cannot accept reservations.", exception.getMessage());

        verify(reservationRepository, atMostOnce()).findById(1L);
        verify(tokenUtils, atMostOnce()).getToken(request);
        verify(tokenUtils, atMostOnce()).getIdFromToken(tokenUtils.getToken(request));
        verify(userService, atMostOnce()).findOne(tokenUtils.getIdFromToken(tokenUtils.getToken(request)));
        verify(accountVerificatorService, atMostOnce()).findOneByUser(owner);
        verifyNoInteractions(accommodationService);
        verifyNoMoreInteractions(reservationRepository);
    }

    @Test
    @Order(8)
    @DisplayName("Test acceptReservation when owner is not verified")
    public void testAcceptReservationWhenOwnerIsNotVerified() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer token");

        Reservation reservation = new Reservation();
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(tokenUtils.getToken(request)).thenReturn("token");
        when(tokenUtils.getIdFromToken("token")).thenReturn(1L);

        Owner owner =
                new Owner(
                        1L,
                        "test",
                        "test",
                        "test",
                        "test",
                        "test",
                        "test",
                        UserRole.Owner,
                        false,
                        new HashSet<>(),
                        false,
                        false,
                        false,
                        false
                );
        when(userService.findOne(1L)).thenReturn(Optional.of(owner));

        AccountVerificator accountVerificator = new AccountVerificator(123, owner);
        when(accountVerificatorService.findOneByUser(owner)).thenReturn(Optional.of(accountVerificator));

        HttpTransferException exception =
                assertThrows(HttpTransferException.class, () -> reservationService.acceptReservation(1L, request));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals("A non-verified owner cannot accept reservations.", exception.getMessage());

        verify(reservationRepository, atMostOnce()).findById(1L);
        verify(tokenUtils, atMostOnce()).getToken(request);
        verify(tokenUtils, atMostOnce()).getIdFromToken(tokenUtils.getToken(request));
        verify(userService, atMostOnce()).findOne(tokenUtils.getIdFromToken(tokenUtils.getToken(request)));
        verify(accountVerificatorService, atMostOnce()).findOneByUser(owner);
        verifyNoInteractions(accommodationService);
        verifyNoMoreInteractions(reservationRepository);
    }

    @Test
    @Order(9)
    @DisplayName("Test acceptReservation when owner does not own the reserved accommodation")
    public void testAcceptReservationWhenOwnerDoesNotOwnTheReservedAccommodation() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer token");

        Reservation reservation =
                new Reservation(1, new Accommodation(), new Guest(), new Period(), BigDecimal.valueOf(1));
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(tokenUtils.getToken(request)).thenReturn("token");
        when(tokenUtils.getIdFromToken("token")).thenReturn(1L);

        Owner owner =
                new Owner(
                        1L,
                        "test",
                        "test",
                        "test",
                        "test",
                        "test",
                        "test",
                        UserRole.Owner,
                        false,
                        new HashSet<>(),
                        false,
                        false,
                        false,
                        false
                );
        when(userService.findOne(1L)).thenReturn(Optional.of(owner));

        AccountVerificator accountVerificator = new AccountVerificator(123, owner);
        accountVerificator.setVerified(true);
        when(accountVerificatorService.findOneByUser(owner)).thenReturn(Optional.of(accountVerificator));

        HttpTransferException exception =
                assertThrows(HttpTransferException.class, () -> reservationService.acceptReservation(1L, request));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals(
                "Only the owner of the reserved accommodation can accept it's reservations.",
                exception.getMessage()
        );

        verify(reservationRepository, atMostOnce()).findById(1L);
        verify(tokenUtils, atMostOnce()).getToken(request);
        verify(tokenUtils, atMostOnce()).getIdFromToken(tokenUtils.getToken(request));
        verify(userService, atMostOnce()).findOne(tokenUtils.getIdFromToken(tokenUtils.getToken(request)));
        verify(accountVerificatorService, atMostOnce()).findOneByUser(owner);
        verifyNoInteractions(accommodationService);
        verifyNoMoreInteractions(reservationRepository);
    }

    @Test
    @Order(10)
    @DisplayName("Test acceptReservation when reservation is not on waiting")
    public void testAcceptReservationWhenReservationIsNotOnWaiting() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer token");

        Accommodation accommodation = new Accommodation();
        Reservation reservation =
                new Reservation(1, accommodation, new Guest(), new Period(), BigDecimal.valueOf(1));
        reservation.setStatus(ReservationStatus.Accepted);
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(tokenUtils.getToken(request)).thenReturn("token");
        when(tokenUtils.getIdFromToken("token")).thenReturn(1L);

        Set<Accommodation> accommodations = new HashSet<>();
        accommodations.add(accommodation);
        Owner owner =
                new Owner(
                        1L,
                        "test",
                        "test",
                        "test",
                        "test",
                        "test",
                        "test",
                        UserRole.Owner,
                        false,
                        accommodations,
                        false,
                        false,
                        false,
                        false
                );
        when(userService.findOne(1L)).thenReturn(Optional.of(owner));

        AccountVerificator accountVerificator = new AccountVerificator(123, owner);
        accountVerificator.setVerified(true);
        when(accountVerificatorService.findOneByUser(owner)).thenReturn(Optional.of(accountVerificator));

        HttpTransferException exception =
                assertThrows(HttpTransferException.class, () -> reservationService.acceptReservation(1L, request));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals("Reservation has already been accepted, declined or cancelled", exception.getMessage());

        verify(reservationRepository, atMostOnce()).findById(1L);
        verify(tokenUtils, atMostOnce()).getToken(request);
        verify(tokenUtils, atMostOnce()).getIdFromToken(tokenUtils.getToken(request));
        verify(userService, atMostOnce()).findOne(tokenUtils.getIdFromToken(tokenUtils.getToken(request)));
        verify(accountVerificatorService, atMostOnce()).findOneByUser(owner);
        verifyNoInteractions(accommodationService);
        verifyNoMoreInteractions(reservationRepository);
    }

    @Test
    @Order(11)
    @DisplayName("Test acceptReservation when reservation has begun")
    public void testAcceptReservationWhenReservationHasBegun() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer token");

        Accommodation accommodation = new Accommodation();
        Reservation reservation =
                new Reservation(
                        1,
                        accommodation,
                        new Guest(),
                        new Period(LocalDate.now().minusDays(5), LocalDate.now().plusDays(5)),
                        BigDecimal.valueOf(1)
                );
        reservation.setStatus(ReservationStatus.Waiting);
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(tokenUtils.getToken(request)).thenReturn("token");
        when(tokenUtils.getIdFromToken("token")).thenReturn(1L);

        Set<Accommodation> accommodations = new HashSet<>();
        accommodations.add(accommodation);
        Owner owner =
                new Owner(
                        1L,
                        "test",
                        "test",
                        "test",
                        "test",
                        "test",
                        "test",
                        UserRole.Owner,
                        false,
                        accommodations,
                        false,
                        false,
                        false,
                        false
                );
        when(userService.findOne(1L)).thenReturn(Optional.of(owner));

        AccountVerificator accountVerificator = new AccountVerificator(123, owner);
        accountVerificator.setVerified(true);
        when(accountVerificatorService.findOneByUser(owner)).thenReturn(Optional.of(accountVerificator));

        HttpTransferException exception =
                assertThrows(HttpTransferException.class, () -> reservationService.acceptReservation(1L, request));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals("Reservation has already begun and cannot be accepted.", exception.getMessage());

        verify(reservationRepository, atMostOnce()).findById(1L);
        verify(tokenUtils, atMostOnce()).getToken(request);
        verify(tokenUtils, atMostOnce()).getIdFromToken(tokenUtils.getToken(request));
        verify(userService, atMostOnce()).findOne(tokenUtils.getIdFromToken(tokenUtils.getToken(request)));
        verify(accountVerificatorService, atMostOnce()).findOneByUser(owner);
        verifyNoInteractions(accommodationService);
        verifyNoMoreInteractions(reservationRepository);
    }

    @Test
    @Order(12)
    @DisplayName("Test acceptReservation when accommodation is not available in the reserved period")
    public void testAcceptReservationWhenAccommodationIsNotAvailableInReservedPeriod() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer token");

        Accommodation accommodation =
                new Accommodation(
                        "test",
                        "test",
                        new Location(),
                        new HashSet<>(),
                        new HashSet<>(),
                        1,
                        1,
                        1,
                        AccommodationType.Apartment,
                        true,
                        true,
                        new HashSet<>()
                );
        Reservation reservation =
                new Reservation(
                        1,
                        accommodation,
                        new Guest(),
                        new Period(LocalDate.now().plusDays(5), LocalDate.now().plusDays(10)),
                        BigDecimal.valueOf(1)
                );
        reservation.setStatus(ReservationStatus.Waiting);
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(tokenUtils.getToken(request)).thenReturn("token");
        when(tokenUtils.getIdFromToken("token")).thenReturn(1L);

        Set<Accommodation> accommodations = new HashSet<>();
        accommodations.add(accommodation);
        Owner owner =
                new Owner(
                        1L,
                        "test",
                        "test",
                        "test",
                        "test",
                        "test",
                        "test",
                        UserRole.Owner,
                        false,
                        accommodations,
                        false,
                        false,
                        false,
                        false
                );
        when(userService.findOne(1L)).thenReturn(Optional.of(owner));

        AccountVerificator accountVerificator = new AccountVerificator(123, owner);
        accountVerificator.setVerified(true);
        when(accountVerificatorService.findOneByUser(owner)).thenReturn(Optional.of(accountVerificator));

        HttpTransferException exception =
                assertThrows(HttpTransferException.class, () -> reservationService.acceptReservation(1L, request));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals("This accommodation is not available in the requested period.", exception.getMessage());

        verify(reservationRepository, atMostOnce()).findById(1L);
        verify(tokenUtils, atMostOnce()).getToken(request);
        verify(tokenUtils, atMostOnce()).getIdFromToken(tokenUtils.getToken(request));
        verify(userService, atMostOnce()).findOne(tokenUtils.getIdFromToken(tokenUtils.getToken(request)));
        verify(accountVerificatorService, atMostOnce()).findOneByUser(owner);
        verifyNoInteractions(accommodationService);
        verifyNoMoreInteractions(reservationRepository);
    }

    @Test
    @Order(13)
    @DisplayName("Test declineReservation")
    public void testDeclineReservation() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer token");

        Accommodation accommodation = new Accommodation();
        Reservation reservation =
                new Reservation(
                        1,
                        accommodation,
                        new Guest(),
                        new Period(LocalDate.now().plusDays(5), LocalDate.now().plusDays(10)),
                        BigDecimal.valueOf(1)
                );
        reservation.setStatus(ReservationStatus.Waiting);
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(tokenUtils.getToken(request)).thenReturn("token");
        when(tokenUtils.getIdFromToken("token")).thenReturn(1L);

        Set<Accommodation> accommodations = new HashSet<>();
        accommodations.add(accommodation);
        Owner owner =
                new Owner(
                        1L,
                        "test",
                        "test",
                        "test",
                        "test",
                        "test",
                        "test",
                        UserRole.Owner,
                        false,
                        accommodations,
                        false,
                        false,
                        false,
                        false
                );
        when(userService.findOne(1L)).thenReturn(Optional.of(owner));

        AccountVerificator accountVerificator = new AccountVerificator(123, owner);
        accountVerificator.setVerified(true);
        when(accountVerificatorService.findOneByUser(owner)).thenReturn(Optional.of(accountVerificator));

        reservationService.declineReservation(1L, request);

        verify(reservationRepository, atMostOnce()).findById(1L);
        verify(tokenUtils, atMostOnce()).getToken(request);
        verify(tokenUtils, atMostOnce()).getIdFromToken(tokenUtils.getToken(request));
        verify(userService, atMostOnce()).findOne(tokenUtils.getIdFromToken(tokenUtils.getToken(request)));
        verify(accountVerificatorService, atMostOnce()).findOneByUser(owner);
        verify(reservationRepository, atMostOnce()).save(reservationCaptor.capture());

        assertEquals(reservationCaptor.getValue().getId(), reservation.getId());
        assertEquals(reservationCaptor.getValue().getStatus(), ReservationStatus.Declined);

        verifyNoInteractions(accommodationService);
    }

    @Test
    @Order(14)
    @DisplayName("Test declineReservation when a reservation has not been found")
    public void testDeclineReservationWhenReservationNotFound() {
        MockHttpServletRequest request = new MockHttpServletRequest();

        when(reservationRepository.findById(0L)).thenReturn(Optional.empty());

        HttpTransferException exception =
                assertThrows(HttpTransferException.class, () -> reservationService.acceptReservation(0L, request));
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals("Reservation not found.", exception.getMessage());

        verify(reservationRepository, atMostOnce()).findById(0L);
        verifyNoInteractions(tokenUtils);
        verifyNoInteractions(userService);
        verifyNoInteractions(accountVerificatorService);
        verifyNoInteractions(accommodationService);
        verifyNoMoreInteractions(reservationRepository);
    }

    @Test
    @Order(15)
    @DisplayName("Test declineReservation when token is null")
    public void testDeclineReservationWhenTokenIsNull() {
        MockHttpServletRequest request = new MockHttpServletRequest();

        Reservation reservation = new Reservation();
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(tokenUtils.getToken(request)).thenReturn(null);
        when(tokenUtils.getIdFromToken(null)).thenReturn(null);
        when(userService.findOne(null)).thenReturn(Optional.empty());

        HttpTransferException exception =
                assertThrows(HttpTransferException.class, () -> reservationService.declineReservation(1L, request));
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals("A non-existent owner cannot decline reservations.", exception.getMessage());

        verify(reservationRepository, atMostOnce()).findById(1L);
        verify(tokenUtils, atMostOnce()).getToken(request);
        verify(tokenUtils, atMostOnce()).getIdFromToken(tokenUtils.getToken(request));
        verify(userService, atMostOnce()).findOne(tokenUtils.getIdFromToken(tokenUtils.getToken(request)));
        verifyNoInteractions(accountVerificatorService);
        verifyNoInteractions(accommodationService);
        verifyNoMoreInteractions(reservationRepository);
    }

    @Test
    @Order(16)
    @DisplayName("Test declineReservation when Id from token is null")
    public void testDeclineReservationWhenIdFromTokenIsNull() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer invalid.token");

        Reservation reservation = new Reservation();
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(tokenUtils.getToken(request)).thenReturn("invalid.token");
        when(tokenUtils.getIdFromToken("invalid.token")).thenReturn(null);
        when(userService.findOne(null)).thenReturn(Optional.empty());

        HttpTransferException exception =
                assertThrows(HttpTransferException.class, () -> reservationService.declineReservation(1L, request));
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals("A non-existent owner cannot decline reservations.", exception.getMessage());

        verify(reservationRepository, atMostOnce()).findById(1L);
        verify(tokenUtils, atMostOnce()).getToken(request);
        verify(tokenUtils, atMostOnce()).getIdFromToken(tokenUtils.getToken(request));
        verify(userService, atMostOnce()).findOne(tokenUtils.getIdFromToken(tokenUtils.getToken(request)));
        verifyNoInteractions(accountVerificatorService);
        verifyNoInteractions(accommodationService);
        verifyNoMoreInteractions(reservationRepository);
    }

    @Test
    @Order(17)
    @DisplayName("Test declineReservation when owner does not exist")
    public void testDeclineReservationWhenOwnerDoesNotExist() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer token");

        Reservation reservation = new Reservation();
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(tokenUtils.getToken(request)).thenReturn("token");
        when(tokenUtils.getIdFromToken("token")).thenReturn(0L);
        when(userService.findOne(0L)).thenReturn(Optional.empty());

        HttpTransferException exception =
                assertThrows(HttpTransferException.class, () -> reservationService.declineReservation(1L, request));
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals("A non-existent owner cannot decline reservations.", exception.getMessage());

        verify(reservationRepository, atMostOnce()).findById(1L);
        verify(tokenUtils, atMostOnce()).getToken(request);
        verify(tokenUtils, atMostOnce()).getIdFromToken(tokenUtils.getToken(request));
        verify(userService, atMostOnce()).findOne(tokenUtils.getIdFromToken(tokenUtils.getToken(request)));
        verifyNoInteractions(accountVerificatorService);
        verifyNoInteractions(accommodationService);
        verifyNoMoreInteractions(reservationRepository);
    }

    @Test
    @Order(18)
    @DisplayName("Test declineReservation when owner is blocked")
    public void testDeclineReservationWhenOwnerIsBlocked() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer token");

        Reservation reservation = new Reservation();
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(tokenUtils.getToken(request)).thenReturn("token");
        when(tokenUtils.getIdFromToken("token")).thenReturn(1L);

        Owner owner =
                new Owner(
                        1L,
                        "test",
                        "test",
                        "test",
                        "test",
                        "test",
                        "test",
                        UserRole.Owner,
                        true,
                        new HashSet<>(),
                        false,
                        false,
                        false,
                        false
                );
        when(userService.findOne(1L)).thenReturn(Optional.of(owner));

        HttpTransferException exception =
                assertThrows(HttpTransferException.class, () -> reservationService.declineReservation(1L, request));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals("A blocked owner cannot decline reservations.", exception.getMessage());

        verify(reservationRepository, atMostOnce()).findById(1L);
        verify(tokenUtils, atMostOnce()).getToken(request);
        verify(tokenUtils, atMostOnce()).getIdFromToken(tokenUtils.getToken(request));
        verify(userService, atMostOnce()).findOne(tokenUtils.getIdFromToken(tokenUtils.getToken(request)));
        verifyNoInteractions(accountVerificatorService);
        verifyNoInteractions(accommodationService);
        verifyNoMoreInteractions(reservationRepository);
    }

    @Test
    @Order(19)
    @DisplayName("Test declineReservation when owner has no verificator")
    public void testDeclineReservationWhenOwnerHasNoVerificator() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer token");

        Reservation reservation = new Reservation();
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(tokenUtils.getToken(request)).thenReturn("token");
        when(tokenUtils.getIdFromToken("token")).thenReturn(1L);

        Owner owner =
                new Owner(
                        1L,
                        "test",
                        "test",
                        "test",
                        "test",
                        "test",
                        "test",
                        UserRole.Owner,
                        false,
                        new HashSet<>(),
                        false,
                        false,
                        false,
                        false
                );
        when(userService.findOne(1L)).thenReturn(Optional.of(owner));
        when(accountVerificatorService.findOneByUser(owner)).thenReturn(Optional.empty());

        HttpTransferException exception =
                assertThrows(HttpTransferException.class, () -> reservationService.declineReservation(1L, request));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals("A non-verified owner cannot decline reservations.", exception.getMessage());

        verify(reservationRepository, atMostOnce()).findById(1L);
        verify(tokenUtils, atMostOnce()).getToken(request);
        verify(tokenUtils, atMostOnce()).getIdFromToken(tokenUtils.getToken(request));
        verify(userService, atMostOnce()).findOne(tokenUtils.getIdFromToken(tokenUtils.getToken(request)));
        verify(accountVerificatorService, atMostOnce()).findOneByUser(owner);
        verifyNoInteractions(accommodationService);
        verifyNoMoreInteractions(reservationRepository);
    }

    @Test
    @Order(20)
    @DisplayName("Test declineReservation when owner is not verified")
    public void testDeclineReservationWhenOwnerIsNotVerified() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer token");

        Reservation reservation = new Reservation();
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(tokenUtils.getToken(request)).thenReturn("token");
        when(tokenUtils.getIdFromToken("token")).thenReturn(1L);

        Owner owner =
                new Owner(
                        1L,
                        "test",
                        "test",
                        "test",
                        "test",
                        "test",
                        "test",
                        UserRole.Owner,
                        false,
                        new HashSet<>(),
                        false,
                        false,
                        false,
                        false
                );
        when(userService.findOne(1L)).thenReturn(Optional.of(owner));

        AccountVerificator accountVerificator = new AccountVerificator(123, owner);
        when(accountVerificatorService.findOneByUser(owner)).thenReturn(Optional.of(accountVerificator));

        HttpTransferException exception =
                assertThrows(HttpTransferException.class, () -> reservationService.declineReservation(1L, request));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals("A non-verified owner cannot decline reservations.", exception.getMessage());

        verify(reservationRepository, atMostOnce()).findById(1L);
        verify(tokenUtils, atMostOnce()).getToken(request);
        verify(tokenUtils, atMostOnce()).getIdFromToken(tokenUtils.getToken(request));
        verify(userService, atMostOnce()).findOne(tokenUtils.getIdFromToken(tokenUtils.getToken(request)));
        verify(accountVerificatorService, atMostOnce()).findOneByUser(owner);
        verifyNoInteractions(accommodationService);
        verifyNoMoreInteractions(reservationRepository);
    }

    @Test
    @Order(21)
    @DisplayName("Test declineReservation when owner does not own the reserved accommodation")
    public void testDeclineReservationWhenOwnerDoesNotOwnTheReservedAccommodation() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer token");

        Reservation reservation =
                new Reservation(1, new Accommodation(), new Guest(), new Period(), BigDecimal.valueOf(1));
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(tokenUtils.getToken(request)).thenReturn("token");
        when(tokenUtils.getIdFromToken("token")).thenReturn(1L);

        Owner owner =
                new Owner(
                        1L,
                        "test",
                        "test",
                        "test",
                        "test",
                        "test",
                        "test",
                        UserRole.Owner,
                        false,
                        new HashSet<>(),
                        false,
                        false,
                        false,
                        false
                );
        when(userService.findOne(1L)).thenReturn(Optional.of(owner));

        AccountVerificator accountVerificator = new AccountVerificator(123, owner);
        accountVerificator.setVerified(true);
        when(accountVerificatorService.findOneByUser(owner)).thenReturn(Optional.of(accountVerificator));

        HttpTransferException exception =
                assertThrows(HttpTransferException.class, () -> reservationService.declineReservation(1L, request));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals(
                "Only the owner of the reserved accommodation can decline it's reservations.",
                exception.getMessage()
        );

        verify(reservationRepository, atMostOnce()).findById(1L);
        verify(tokenUtils, atMostOnce()).getToken(request);
        verify(tokenUtils, atMostOnce()).getIdFromToken(tokenUtils.getToken(request));
        verify(userService, atMostOnce()).findOne(tokenUtils.getIdFromToken(tokenUtils.getToken(request)));
        verify(accountVerificatorService, atMostOnce()).findOneByUser(owner);
        verifyNoInteractions(accommodationService);
        verifyNoMoreInteractions(reservationRepository);
    }

    @Test
    @Order(22)
    @DisplayName("Test declineReservation when reservation is not on waiting")
    public void testDeclineReservationWhenReservationIsNotOnWaiting() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer token");

        Accommodation accommodation = new Accommodation();
        Reservation reservation =
                new Reservation(1, accommodation, new Guest(), new Period(), BigDecimal.valueOf(1));
        reservation.setStatus(ReservationStatus.Accepted);
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(tokenUtils.getToken(request)).thenReturn("token");
        when(tokenUtils.getIdFromToken("token")).thenReturn(1L);

        Set<Accommodation> accommodations = new HashSet<>();
        accommodations.add(accommodation);
        Owner owner =
                new Owner(
                        1L,
                        "test",
                        "test",
                        "test",
                        "test",
                        "test",
                        "test",
                        UserRole.Owner,
                        false,
                        accommodations,
                        false,
                        false,
                        false,
                        false
                );
        when(userService.findOne(1L)).thenReturn(Optional.of(owner));

        AccountVerificator accountVerificator = new AccountVerificator(123, owner);
        accountVerificator.setVerified(true);
        when(accountVerificatorService.findOneByUser(owner)).thenReturn(Optional.of(accountVerificator));

        HttpTransferException exception =
                assertThrows(HttpTransferException.class, () -> reservationService.declineReservation(1L, request));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals("Reservation has already been accepted, denied or cancelled", exception.getMessage());

        verify(reservationRepository, atMostOnce()).findById(1L);
        verify(tokenUtils, atMostOnce()).getToken(request);
        verify(tokenUtils, atMostOnce()).getIdFromToken(tokenUtils.getToken(request));
        verify(userService, atMostOnce()).findOne(tokenUtils.getIdFromToken(tokenUtils.getToken(request)));
        verify(accountVerificatorService, atMostOnce()).findOneByUser(owner);
        verifyNoInteractions(accommodationService);
        verifyNoMoreInteractions(reservationRepository);
    }

    @Test
    @Order(23)
    @DisplayName("Test declineReservation when reservation has begun")
    public void testDeclineReservationWhenReservationHasBegun() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer token");

        Accommodation accommodation = new Accommodation();
        Reservation reservation =
                new Reservation(
                        1,
                        accommodation,
                        new Guest(),
                        new Period(LocalDate.now().minusDays(5), LocalDate.now().plusDays(5)),
                        BigDecimal.valueOf(1)
                );
        reservation.setStatus(ReservationStatus.Waiting);
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(tokenUtils.getToken(request)).thenReturn("token");
        when(tokenUtils.getIdFromToken("token")).thenReturn(1L);

        Set<Accommodation> accommodations = new HashSet<>();
        accommodations.add(accommodation);
        Owner owner =
                new Owner(
                        1L,
                        "test",
                        "test",
                        "test",
                        "test",
                        "test",
                        "test",
                        UserRole.Owner,
                        false,
                        accommodations,
                        false,
                        false,
                        false,
                        false
                );
        when(userService.findOne(1L)).thenReturn(Optional.of(owner));

        AccountVerificator accountVerificator = new AccountVerificator(123, owner);
        accountVerificator.setVerified(true);
        when(accountVerificatorService.findOneByUser(owner)).thenReturn(Optional.of(accountVerificator));

        HttpTransferException exception =
                assertThrows(HttpTransferException.class, () -> reservationService.declineReservation(1L, request));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals("Reservation has already begun and cannot be declined.", exception.getMessage());

        verify(reservationRepository, atMostOnce()).findById(1L);
        verify(tokenUtils, atMostOnce()).getToken(request);
        verify(tokenUtils, atMostOnce()).getIdFromToken(tokenUtils.getToken(request));
        verify(userService, atMostOnce()).findOne(tokenUtils.getIdFromToken(tokenUtils.getToken(request)));
        verify(accountVerificatorService, atMostOnce()).findOneByUser(owner);
        verifyNoInteractions(accommodationService);
        verifyNoMoreInteractions(reservationRepository);
    }
}
