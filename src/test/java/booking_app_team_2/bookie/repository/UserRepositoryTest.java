package booking_app_team_2.bookie.repository;

import booking_app_team_2.bookie.domain.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import booking_app_team_2.bookie.domain.UserRole;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @Order(1)
    @DisplayName("Test findById when Id exists")
    public void testFindByIdWhenIdExists() {
        Optional<User> user = userRepository.findById(1L);

        assertTrue(user.isPresent());
        assertEquals(user.get().getId(), 1L);
    }

    @Test
    @Order(2)
    @DisplayName("Test findById when Id is non-existent")
    public void testFindByIdWhenIdDoesNotExist() {
        Optional<User> user = userRepository.findById(0L);

        assertTrue(user.isEmpty());
    }

    @Test
    @Order(3)
    @DisplayName("Test save")
    public void testSave() {
        User user =
                new User(
                        "test",
                        "test",
                        "test",
                        "test",
                        "test",
                        "test",
                        UserRole.Guest
                );
        User savedUser = userRepository.save(user);

        assertThat(savedUser).usingRecursiveComparison().ignoringFields("id").isEqualTo(user);
    }
}
