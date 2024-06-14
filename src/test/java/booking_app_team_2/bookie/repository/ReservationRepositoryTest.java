package booking_app_team_2.bookie.repository;

import booking_app_team_2.bookie.domain.Accommodation;
import booking_app_team_2.bookie.domain.Guest;
import booking_app_team_2.bookie.domain.Period;
import booking_app_team_2.bookie.domain.Reservation;
import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReservationRepositoryTest {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private GuestRepository guestRepository;

    @Test
    @Order(1)
    @DisplayName("Test findById")
    public void testFindByIdWhenIdExists() {
        Optional<Reservation> reservation = reservationRepository.findById(1L);

        assertTrue(reservation.isPresent());
        assertEquals(reservation.get().getId(), 1L);
    }

    @Test
    @Order(2)
    @DisplayName("Test findById when Id is non-existent")
    public void testFindByIdWhenIdDoesNotExist() {
        Optional<Reservation> reservation = reservationRepository.findById(0L);

        assertFalse(reservation.isPresent());
    }

    @Test
    @Order(3)
    @DisplayName("Test save")
    public void testSave() {
        Accommodation accommodation = new Accommodation();
        accommodationRepository.save(accommodation);

        Guest guest = new Guest();
        guestRepository.save(guest);

        Reservation reservation =
                new Reservation(
                        1,
                        accommodation,
                        guest,
                        new Period(LocalDate.now(), LocalDate.now().plusDays(2)),
                        BigDecimal.valueOf(1)
                );
        Reservation savedReservation = reservationRepository.save(reservation);

        assertThat(savedReservation)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(reservation);
    }
}
