package booking_app_team_2.bookie.controller;

import booking_app_team_2.bookie.dto.UserAuthenticationDTO;
import booking_app_team_2.bookie.dto.UserTokenStateDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
public class ReservationControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    public String ownerJwt;

    @BeforeAll
    public void login() {
        UserAuthenticationDTO userAuthenticationDTO =
                new UserAuthenticationDTO("owner@gmail.com", "owner123");
        ResponseEntity<UserTokenStateDTO> response =
                restTemplate.postForEntity("/authentication/login", userAuthenticationDTO, UserTokenStateDTO.class);

        ownerJwt = Objects.requireNonNull(response.getBody()).getJWT();
    }

    @Test
    @DisplayName("Test acceptReservation when everything is valid")
    public void testAcceptReservation() {

    }
}
