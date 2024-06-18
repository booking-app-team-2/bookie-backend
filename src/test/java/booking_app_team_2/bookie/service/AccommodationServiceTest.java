package booking_app_team_2.bookie.service;

import booking_app_team_2.bookie.domain.Accommodation;
import booking_app_team_2.bookie.domain.AccommodationType;
import booking_app_team_2.bookie.domain.Location;
import booking_app_team_2.bookie.repository.AccommodationRepository;
import booking_app_team_2.bookie.repository.ReservationRepository;
import booking_app_team_2.bookie.util.TokenUtils;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class AccommodationServiceTest {
    @Autowired
    private AccommodationService accommodationService;

    @MockBean
    private AccommodationRepository accommodationRepository;

    @MockBean
    private ReservationRepository reservationRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private AccountVerificatorService accountVerificatorService;

    @MockBean
    private TokenUtils tokenUtils;

    @Test
    @DisplayName("Test save")
    public void testSave() {
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
        when(accommodationRepository.save(accommodation)).thenReturn(accommodation);

        Accommodation savedAccommodation = accommodationService.save(accommodation);

        assertEquals(savedAccommodation, accommodation);

        verify(accommodationRepository, atMostOnce()).save(accommodation);
        verifyNoInteractions(reservationRepository);
        verifyNoInteractions(userService);
        verifyNoInteractions(accountVerificatorService);
        verifyNoInteractions(tokenUtils);
    }
}
