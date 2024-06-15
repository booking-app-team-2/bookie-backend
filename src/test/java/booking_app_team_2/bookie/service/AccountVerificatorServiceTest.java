package booking_app_team_2.bookie.service;

import booking_app_team_2.bookie.domain.AccountVerificator;
import booking_app_team_2.bookie.domain.User;
import booking_app_team_2.bookie.domain.UserRole;
import booking_app_team_2.bookie.repository.AccountVerificatorRepository;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountVerificatorServiceTest {
    @Autowired
    private AccountVerificatorService accountVerificatorService;

    @MockBean
    private AccountVerificatorRepository accountVerificatorRepository;

    @Test
    @Order(1)
    @DisplayName("Test findOneByUser when user exists")
    public void testFindOneByUserWhenUserExists() {
        User user = new User("test", "test", "test", "test", "test", "test", UserRole.Guest);
        AccountVerificator accountVerificator = new AccountVerificator(123, user);
        when(accountVerificatorRepository.findOneByUser(user)).thenReturn(Optional.of(accountVerificator));

        Optional<AccountVerificator> foundAccountVerificator = accountVerificatorService.findOneByUser(user);
        assertTrue(foundAccountVerificator.isPresent());
        assertEquals(foundAccountVerificator.get(), accountVerificator);

        verify(accountVerificatorRepository, atMostOnce()).findOneByUser(user);
    }

    @Test
    @Order(2)
    @DisplayName("Test findOneByUser when user does not exist")
    public void testFindOneByUserWhenUserDoesNotExist() {
        when(accountVerificatorRepository.findOneByUser(new User())).thenReturn(Optional.empty());

        assertTrue(accountVerificatorService.findOneByUser(new User()).isEmpty());

        verify(accountVerificatorRepository, atMostOnce()).findOneByUser(new User());
    }
}
