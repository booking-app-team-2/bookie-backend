package booking_app_team_2.bookie.service;

import booking_app_team_2.bookie.domain.Reservation;
import booking_app_team_2.bookie.exception.HttpTransferException;
import booking_app_team_2.bookie.repository.ReservationRepository;
import booking_app_team_2.bookie.util.TokenUtils;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
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

    @Test
    @DisplayName("Test acceptReservation when a reservation has not been found")
    public void testAcceptReservationWhenReservationHasNotBeenFound() {
        MockHttpServletRequest request = new MockHttpServletRequest();

        when(reservationRepository.findById(1L)).thenReturn(Optional.empty());

        HttpTransferException exception =
                assertThrows(HttpTransferException.class, () -> reservationService.acceptReservation(1L, request));
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals("Reservation not found.", exception.getMessage());

        verify(reservationRepository, atMostOnce()).findById(1L);
        verifyNoInteractions(tokenUtils);
        verifyNoInteractions(userService);
        verifyNoInteractions(accountVerificatorService);
        verifyNoInteractions(accommodationService);
        verifyNoMoreInteractions(reservationRepository);
    }

    @Test
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
}
