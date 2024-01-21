package booking_app_team_2.bookie.repository;

import booking_app_team_2.bookie.domain.Accommodation;
import booking_app_team_2.bookie.domain.Guest;
import booking_app_team_2.bookie.domain.Period;
import booking_app_team_2.bookie.domain.Reservation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.cglib.core.Local;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
public class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private GuestRepository guestRepository;

    @Test
    public void shouldSaveReservation() {

        Accommodation accommodation = new Accommodation();
        accommodationRepository.save(accommodation);

        Guest guest = new Guest();
        guestRepository.save(guest);

        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now().plusDays(2);

        Reservation reservation = new Reservation(3, accommodation, guest, new Period(start, end), BigDecimal.valueOf(300));

        Reservation savedReservation = reservationRepository.save(reservation);

        assertThat(savedReservation)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(reservation);

    }

}
