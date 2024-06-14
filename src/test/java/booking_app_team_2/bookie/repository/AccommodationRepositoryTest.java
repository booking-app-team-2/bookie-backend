package booking_app_team_2.bookie.repository;

import booking_app_team_2.bookie.domain.Accommodation;
import booking_app_team_2.bookie.domain.AccommodationType;
import booking_app_team_2.bookie.domain.Location;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;

@DataJpaTest
@ActiveProfiles("test")
public class AccommodationRepositoryTest {
    @Autowired
    private AccommodationRepository accommodationRepository;

    @Test
    @Order(1)
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
        Accommodation savedAccommodation = accommodationRepository.save(accommodation);

        assertThat(savedAccommodation)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(accommodation);
    }
}
