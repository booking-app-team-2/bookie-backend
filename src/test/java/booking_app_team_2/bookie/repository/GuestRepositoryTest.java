package booking_app_team_2.bookie.repository;

import booking_app_team_2.bookie.domain.Guest;
import booking_app_team_2.bookie.domain.UserRole;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;

@DataJpaTest
@ActiveProfiles("test")
public class GuestRepositoryTest {
    @Autowired
    private GuestRepository guestRepository;

    @Test
    @DisplayName("Test save")
    public void testSave() {
        Guest guest =
                new Guest(
                        "test",
                        "test",
                        "test",
                        "test",
                        "test",
                        "test",
                        UserRole.Guest,
                        false,
                        new HashSet<>()
                );
        Guest savedGuest = guestRepository.save(guest);

        assertThat(savedGuest).usingRecursiveComparison().ignoringFields("id").isEqualTo(guest);
    }
}
