package booking_app_team_2.bookie.repository;

import booking_app_team_2.bookie.domain.AccountVerificator;
import booking_app_team_2.bookie.domain.User;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountVerificatorRepositoryTest {
    @Autowired
    private AccountVerificatorRepository accountVerificatorRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Order(1)
    @DisplayName("Test findOneByUser when one with user exists")
    public void testFindOneByUserWhenOneWithUserExists() {
        Optional<User> user = userRepository.findById(1L);
        assertTrue(user.isPresent());

        Optional<AccountVerificator> accountVerificator = accountVerificatorRepository.findOneByUser(user.get());

        assertTrue(accountVerificator.isPresent());
    }

    @Test
    @Order(2)
    @DisplayName("Test findOneByUser when one with user does not exist")
    public void testFindOneByUserWhenOneWithUserDoesNotExist() {
        User user = new User();
        userRepository.save(user);

        Optional<AccountVerificator> accountVerificator = accountVerificatorRepository.findOneByUser(user);

        assertTrue(accountVerificator.isEmpty());
    }
}
