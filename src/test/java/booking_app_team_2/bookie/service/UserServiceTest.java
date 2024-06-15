package booking_app_team_2.bookie.service;


import booking_app_team_2.bookie.domain.User;
import booking_app_team_2.bookie.repository.UserRepository;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    @Order(1)
    @DisplayName("Test findOne when Id exists")
    public void testFindOneWhenIdExists() {
        User user = new User();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.findOne(1L);

        assertTrue(foundUser.isPresent());
        assertEquals(foundUser.get(), user);

        verify(userRepository, atMostOnce()).findById(1L);
        verifyNoInteractions(reservationService);
        verifyNoInteractions(passwordEncoder);
    }

    @Test
    @Order(2)
    @DisplayName("Test findOne when Id doesn't exist")
    public void testFindOneWhenIdDoesNotExist() {
        when(userRepository.findById(0L)).thenReturn(Optional.empty());

        assertFalse(userService.findOne(0L).isPresent());

        verify(userRepository, atMostOnce()).findById(0L);
        verifyNoInteractions(reservationService);
        verifyNoInteractions(passwordEncoder);
    }
}
