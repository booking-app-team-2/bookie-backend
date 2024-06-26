package booking_app_team_2.bookie.repository;

import booking_app_team_2.bookie.domain.*;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

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

    private static Stream<Arguments> argumentSource() {
        return
                Stream
                        .of(
                                Arguments
                                        .arguments(
                                                new Reservation(
                                                        1,
                                                        new Accommodation(),
                                                        new Guest(),
                                                        new Period(
                                                                LocalDate
                                                                        .from(
                                                                                LocalDate
                                                                                        .now()
                                                                                        .atStartOfDay(
                                                                                                ZoneId.systemDefault()
                                                                                        )
                                                                                        .minusDays(22)
                                                                        ),
                                                                LocalDate
                                                                        .from(
                                                                                LocalDate
                                                                                        .now()
                                                                                        .atStartOfDay(
                                                                                                ZoneId.systemDefault()
                                                                                        )
                                                                                        .minusDays(21)
                                                                        )
                                                        ),
                                                        BigDecimal.valueOf(1)
                                                ),
                                                0
                                        ),
                                Arguments
                                        .arguments(
                                                new Reservation(
                                                        1,
                                                        new Accommodation(),
                                                        new Guest(),
                                                        new Period(
                                                                LocalDate
                                                                        .from(
                                                                                LocalDate
                                                                                        .now()
                                                                                        .atStartOfDay(
                                                                                                ZoneId.systemDefault()
                                                                                        )
                                                                                        .minusDays(22)
                                                                        ),
                                                                LocalDate
                                                                        .from(
                                                                                LocalDate
                                                                                        .now()
                                                                                        .atStartOfDay(
                                                                                                ZoneId.systemDefault()
                                                                                        )
                                                                                        .minusDays(17)
                                                                        )
                                                        ),
                                                        BigDecimal.valueOf(1)
                                                ),
                                                1
                                        ),
                                Arguments
                                        .arguments(
                                                new Reservation(
                                                        1,
                                                        new Accommodation(),
                                                        new Guest(),
                                                        new Period(
                                                                LocalDate
                                                                        .from(
                                                                                LocalDate
                                                                                        .now()
                                                                                        .atStartOfDay(
                                                                                                ZoneId.systemDefault()
                                                                                        )
                                                                                        .minusDays(17)
                                                                        ),
                                                                LocalDate
                                                                        .from(
                                                                                LocalDate
                                                                                        .now()
                                                                                        .atStartOfDay(
                                                                                                ZoneId.systemDefault()
                                                                                        )
                                                                                        .plusDays(23)
                                                                        )
                                                        ),
                                                        BigDecimal.valueOf(1)
                                                ),
                                                2
                                        ),
                                Arguments
                                        .arguments(
                                                new Reservation(
                                                        1,
                                                        new Accommodation(),
                                                        new Guest(),
                                                        new Period(
                                                                LocalDate
                                                                        .from(
                                                                                LocalDate
                                                                                        .now()
                                                                                        .atStartOfDay(
                                                                                                ZoneId.systemDefault()
                                                                                        )
                                                                                        .plusDays(18)
                                                                        ),
                                                                LocalDate
                                                                        .from(
                                                                                LocalDate
                                                                                        .now()
                                                                                        .atStartOfDay(
                                                                                                ZoneId.systemDefault()
                                                                                        )
                                                                                        .plusDays(28)
                                                                        )
                                                        ),
                                                        BigDecimal.valueOf(1)
                                                ),
                                                1
                                        ),
                                Arguments
                                        .arguments(
                                                new Reservation(
                                                        1,
                                                        new Accommodation(),
                                                        new Guest(),
                                                        new Period(
                                                                LocalDate
                                                                        .from(
                                                                                LocalDate
                                                                                        .now()
                                                                                        .atStartOfDay(
                                                                                                ZoneId.systemDefault()
                                                                                        )
                                                                                        .plusDays(28)
                                                                        ),
                                                                LocalDate
                                                                        .from(
                                                                                LocalDate
                                                                                        .now()
                                                                                        .atStartOfDay(
                                                                                                ZoneId.systemDefault()
                                                                                        )
                                                                                        .plusDays(29)
                                                                        )
                                                        ),
                                                        BigDecimal.valueOf(1)
                                                ),
                                                0
                                        ),
                                Arguments
                                        .arguments(
                                                new Reservation(
                                                        1,
                                                        new Accommodation(),
                                                        new Guest(),
                                                        new Period(
                                                                LocalDate
                                                                        .from(
                                                                                LocalDate
                                                                                        .now()
                                                                                        .atStartOfDay(
                                                                                                ZoneId.systemDefault()
                                                                                        )
                                                                                        .plusDays(21)
                                                                        ),
                                                                LocalDate
                                                                        .from(
                                                                                LocalDate
                                                                                        .now()
                                                                                        .atStartOfDay(
                                                                                                ZoneId.systemDefault()
                                                                                        )
                                                                                        .plusDays(24)
                                                                        )
                                                        ),
                                                        BigDecimal.valueOf(1)
                                                ),
                                                1
                                        ),
                                Arguments
                                        .arguments(
                                                new Reservation(
                                                        1,
                                                        new Accommodation(),
                                                        new Guest(),
                                                        new Period(
                                                                LocalDate
                                                                        .from(
                                                                                LocalDate
                                                                                        .now()
                                                                                        .atStartOfDay(
                                                                                                ZoneId.systemDefault()
                                                                                        )
                                                                                        .plusDays(23)
                                                                        ),
                                                                LocalDate
                                                                        .from(
                                                                                LocalDate
                                                                                        .now()
                                                                                        .atStartOfDay(
                                                                                                ZoneId.systemDefault()
                                                                                        )
                                                                                        .plusDays(30)
                                                                        )
                                                        ),
                                                        BigDecimal.valueOf(1)
                                                ),
                                                1
                                        ),
                                Arguments
                                        .arguments(
                                                new Reservation(
                                                        1,
                                                        new Accommodation(),
                                                        new Guest(),
                                                        new Period(
                                                                LocalDate
                                                                        .from(
                                                                                LocalDate
                                                                                        .now()
                                                                                        .atStartOfDay(
                                                                                                ZoneId.systemDefault()
                                                                                        )
                                                                                        .plusDays(22)
                                                                        ),
                                                                LocalDate
                                                                        .from(
                                                                                LocalDate
                                                                                        .now()
                                                                                        .atStartOfDay(
                                                                                                ZoneId.systemDefault()
                                                                                        )
                                                                                        .plusDays(23)
                                                                        )
                                                        ),
                                                        BigDecimal.valueOf(1)
                                                ),
                                                1
                                        )
                        );
    }

    @ParameterizedTest
    @MethodSource(value = "argumentSource")
    @Order(4)
    @DisplayName("Test findAllByIntersectingPeriod")
    public void testFindAllByIntersectingPeriod(Reservation reservation, int expectedNumberOfIntersectingReservations) {
        Optional<Accommodation> accommodation = accommodationRepository.findById(1L);
        if (accommodation.isEmpty())
            throw new RuntimeException("Something went terribly wrong if the test got here...");

        reservation.setId(0L);
        reservation.setAccommodation(accommodation.get());
        List<Reservation> reservations = reservationRepository.findAllByIntersectingPeriod(reservation);
        assertEquals(expectedNumberOfIntersectingReservations, reservations.size());
        reservations.forEach(foundReservation -> assertEquals(reservation.getStatus(), foundReservation.getStatus()));
    }

    @Test
    @Order(5)
    @DisplayName("Test findAllByIntersectingPeriod when reservation does not exist")
    public void testFindAllByIntersectingPeriodWhenReservationDoesNotExist() {
        Optional<Reservation> reservation = reservationRepository.findById(0L);
        assertFalse(reservation.isPresent());

        NoSuchElementException exception =
                assertThrows(
                        NoSuchElementException.class,
                        () -> reservationRepository.findAllByIntersectingPeriod(reservation.get())
                );
        assertEquals("No value present", exception.getMessage());
    }

    @Test
    @Order(6)
    @DisplayName("Test findAllByIntersectingPeriod so that no found reservations are the same as the passed one")
    public void testFindAllByIntersectingPeriodSoThatNoFoundReservationsAreTheSameAsPassedOne() {
        Optional<Reservation> reservation = reservationRepository.findById(4L);
        assertTrue(reservation.isPresent());

        List<Reservation> reservations = reservationRepository.findAllByIntersectingPeriod(reservation.get());
        assertFalse(reservations.isEmpty());

        reservations.forEach(foundReservation -> assertNotEquals(foundReservation, reservation.get()));
    }

    @Test
    @Order(7)
    @DisplayName(
            "Test findAllByIntersectingPeriod so that all found reservations are for the same accommodation as the " +
                    "passed one"
    )
    public void testFindAllByIntersectingPeriodSoThatAllFoundReservationsAreForTheSameAccommodationAsPassedOne() {
        Optional<Reservation> reservation = reservationRepository.findById(4L);
        assertTrue(reservation.isPresent());

        List<Reservation> reservations = reservationRepository.findAllByIntersectingPeriod(reservation.get());
        assertFalse(reservations.isEmpty());

        reservations
                .forEach(
                        foundReservation ->
                                assertEquals(foundReservation.getAccommodation(), reservation.get().getAccommodation())
                );
    }

    @Test
    @Order(8)
    @DisplayName("Test findAllByIntersectingPeriod so that all found reservations are on waiting")
    public void testFindAllByIntersectingPeriodSoThatAllFoundReservationsAreOnWaiting() {
        Optional<Reservation> reservation = reservationRepository.findById(4L);
        assertTrue(reservation.isPresent());

        List<Reservation> reservations = reservationRepository.findAllByIntersectingPeriod(reservation.get());
        assertFalse(reservations.isEmpty());

        reservations.forEach(foundReservation -> assertEquals(foundReservation.getStatus(), ReservationStatus.Waiting));
    }
}
