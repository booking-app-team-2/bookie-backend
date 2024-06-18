package booking_app_team_2.bookie.controller;

import booking_app_team_2.bookie.domain.Period;
import booking_app_team_2.bookie.domain.ReservationStatus;
import booking_app_team_2.bookie.dto.*;

import static org.junit.jupiter.api.Assertions.*;

import booking_app_team_2.bookie.exception.ErrorMessage;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReservationControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    public String logIn(String username, String password) {
        UserAuthenticationDTO userAuthenticationDTO = new UserAuthenticationDTO(username, password);
        ResponseEntity<UserTokenStateDTO> response =
                restTemplate
                        .postForEntity("/authentication/login", userAuthenticationDTO, UserTokenStateDTO.class);

        return Objects.requireNonNull(response.getBody()).getJWT();
    }

    @Test
    @Order(1)
    @DisplayName("Test createReservation when reservations are automatically accepted for the reserved accommodation")
    public void testCreateReservationWhenReservationsAreAutoAcceptedForAccommodation() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + logIn("darko@gmail.com", "darko123"));

        final Long accommodationId = 1L;
        ReservationDTO reservationDTO =
                new ReservationDTO(
                        2,
                        null,
                        accommodationId,
                        3L,
                        new PeriodDTO(
                                new Period(
                                        LocalDate
                                                .from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).plusDays(1)),
                                        LocalDate.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).plusDays(2))
                                )
                        )
                );

        HttpEntity<ReservationDTO> requestEntity = new HttpEntity<>(reservationDTO, headers);
        ResponseEntity<ReservationDTO> responseEntity =
                restTemplate
                        .exchange(
                                "/api/v1/reservations",
                                HttpMethod.POST,
                                requestEntity,
                                ReservationDTO.class
                        );
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(Objects.requireNonNull(responseEntity.getBody()).getStatus(), ReservationStatus.Accepted);
        assertEquals(Objects.requireNonNull(responseEntity.getBody()).getAccommodationId(), accommodationId);
    }

    @Test
    @Order(2)
    @DisplayName(
            "Test createReservation when reservations are not automatically accepted for the reserved accommodation"
    )
    public void testCreateReservationWhenReservationsAreNotAutoAcceptedForAccommodation() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + logIn("darko@gmail.com", "darko123"));

        final Long accommodationId = 2L;
        ReservationDTO reservationDTO =
                new ReservationDTO(
                        1,
                        null,
                        accommodationId,
                        3L,
                        new PeriodDTO(
                                new Period(
                                        LocalDate
                                                .from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).plusDays(2)),
                                        LocalDate.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).plusDays(3))
                                )
                        )
                );

        HttpEntity<ReservationDTO> requestEntity = new HttpEntity<>(reservationDTO, headers);
        ResponseEntity<ReservationDTO> responseEntity =
                restTemplate
                        .exchange(
                                "/api/v1/reservations",
                                HttpMethod.POST,
                                requestEntity,
                                ReservationDTO.class
                        );
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(Objects.requireNonNull(responseEntity.getBody()).getStatus(), ReservationStatus.Waiting);
        assertEquals(Objects.requireNonNull(responseEntity.getBody()).getAccommodationId(), accommodationId);
    }

    @Test
    @Order(3)
    @DisplayName("Test acceptReservation when everything is valid")
    public void testAcceptReservation() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + logIn("owner@gmail.com", "owner123"));

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<ReservationStatusDTO> responseEntity =
                restTemplate
                        .exchange(
                                "/api/v1/reservations/3/status/accepted",
                                HttpMethod.PUT,
                                requestEntity,
                                ReservationStatusDTO.class
                        );
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(Objects.requireNonNull(responseEntity.getBody()).getStatus(), ReservationStatus.Accepted);
    }

    @Test
    @Order(4)
    @DisplayName("Test acceptReservation when reservation was not found")
    public void testAcceptReservationWhenReservationNotFound() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + logIn("owner@gmail.com", "owner123"));

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<ErrorMessage> responseEntity =
                restTemplate
                        .exchange(
                                "/api/v1/reservations/0/status/accepted",
                                HttpMethod.PUT,
                                requestEntity,
                                ErrorMessage.class
                        );
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(Objects.requireNonNull(responseEntity.getBody()).getMessage(), "Reservation not found.");
    }

    @Test
    @Order(5)
    @DisplayName("Test acceptReservation when Id from token is null")
    public void testAcceptReservationWhenIdFromTokenIsNull() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer invalid.token");

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<ErrorMessage> responseEntity =
                restTemplate
                        .exchange(
                                "/api/v1/reservations/0/status/accepted",
                                HttpMethod.PUT,
                                requestEntity,
                                ErrorMessage.class
                        );
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    @Order(6)
    @DisplayName("Test acceptReservation when owner has no verificator")
    public void testAcceptReservationWhenOwnerHasNoVerificator() {
        HttpHeaders headers = new HttpHeaders();
        headers
                .set(
                        "Authorization",
                        "Bearer " + logIn(
                                "ownerwithoutverificator@gmail.com", "ownerwithoutverificator123"
                        )
                );

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<ErrorMessage> responseEntity =
                restTemplate
                        .exchange(
                                "/api/v1/reservations/1/status/accepted",
                                HttpMethod.PUT,
                                requestEntity,
                                ErrorMessage.class
                        );
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(
                Objects.requireNonNull(responseEntity.getBody()).getMessage(),
                "A non-verified owner cannot accept reservations."
        );
    }

    @Test
    @Order(7)
    @DisplayName("Test acceptReservation when owner is not verified")
    public void testAcceptReservationWhenOwnerIsNotVerified() {
        HttpHeaders headers = new HttpHeaders();
        headers
                .set(
                        "Authorization",
                        "Bearer " + logIn("nonverifiedowner@gmail.com", "nonverifiedowner123")
                );

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<ErrorMessage> responseEntity =
                restTemplate
                        .exchange(
                                "/api/v1/reservations/1/status/accepted",
                                HttpMethod.PUT,
                                requestEntity,
                                ErrorMessage.class
                        );
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(
                Objects.requireNonNull(responseEntity.getBody()).getMessage(),
                "A non-verified owner cannot accept reservations."
        );
    }

    @Test
    @Order(8)
    @DisplayName("Test acceptReservation when owner does not own the accommodation for which the reservation was made")
    public void testAcceptReservationWhenOwnerDoesNotOwnReservedAccommodation() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + logIn("owneraccsnores@gmail.com", "owner123"));

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<ErrorMessage> responseEntity =
                restTemplate
                        .exchange(
                                "/api/v1/reservations/1/status/accepted",
                                HttpMethod.PUT,
                                requestEntity,
                                ErrorMessage.class
                        );
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(
                Objects.requireNonNull(responseEntity.getBody()).getMessage(),
                "Only the owner of the reserved accommodation can accept it's reservations."
        );
    }

    @Test
    @Order(9)
    @DisplayName("Test acceptReservation when reservation is not on waiting")
    public void testAcceptReservationWhenReservationIsNotOnWaiting() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + logIn("owner@gmail.com", "owner123"));

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<ErrorMessage> responseEntity =
                restTemplate
                        .exchange(
                                "/api/v1/reservations/1/status/accepted",
                                HttpMethod.PUT,
                                requestEntity,
                                ErrorMessage.class
                        );
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(
                Objects.requireNonNull(responseEntity.getBody()).getMessage(),
                "Reservation has already been accepted, declined or cancelled"
        );
    }

    @Test
    @Order(10)
    @DisplayName("Test acceptReservation when reservation has begun")
    public void testAcceptReservationWhenReservationHasBegun() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + logIn("owner@gmail.com", "owner123"));

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<ErrorMessage> responseEntity =
                restTemplate
                        .exchange(
                                "/api/v1/reservations/8/status/accepted",
                                HttpMethod.PUT,
                                requestEntity,
                                ErrorMessage.class
                        );
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(
                Objects.requireNonNull(responseEntity.getBody()).getMessage(),
                "Reservation has already begun and cannot be accepted."
        );
    }

    @Test
    @Order(11)
    @DisplayName("Test acceptReservation when accommodation is not available in the reserved period")
    public void testAcceptReservationWhenAccommodationIsNotAvailableInReservedPeriod() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + logIn("owner@gmail.com", "owner123"));

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<ErrorMessage> responseEntity =
                restTemplate
                        .exchange(
                                "/api/v1/reservations/9/status/accepted",
                                HttpMethod.PUT,
                                requestEntity,
                                ErrorMessage.class
                        );
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(
                Objects.requireNonNull(responseEntity.getBody()).getMessage(),
                "This accommodation is not available in the requested period."
        );
    }

    @Test
    @Order(12)
    @DisplayName("Test declineReservation")
    public void testDeclineReservation() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + logIn("owner@gmail.com", "owner123"));

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<ReservationStatusDTO> responseEntity =
                restTemplate
                        .exchange(
                                "/api/v1/reservations/4/status/declined",
                                HttpMethod.PUT,
                                requestEntity,
                                ReservationStatusDTO.class
                        );
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(Objects.requireNonNull(responseEntity.getBody()).getStatus(), ReservationStatus.Declined);
    }

    @Test
    @Order(13)
    @DisplayName("Test declineReservation when reservation was not found")
    public void testDeclineReservationWhenReservationNotFound() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + logIn("owner@gmail.com", "owner123"));

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<ErrorMessage> responseEntity =
                restTemplate
                        .exchange(
                                "/api/v1/reservations/0/status/declined",
                                HttpMethod.PUT,
                                requestEntity,
                                ErrorMessage.class
                        );
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(Objects.requireNonNull(responseEntity.getBody()).getMessage(), "Reservation not found.");
    }

    @Test
    @Order(14)
    @DisplayName("Test declineReservation when Id from token is null")
    public void testDeclineReservationWhenIdFromTokenIsNull() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer invalid.token");

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<ErrorMessage> responseEntity =
                restTemplate
                        .exchange(
                                "/api/v1/reservations/0/status/declined",
                                HttpMethod.PUT,
                                requestEntity,
                                ErrorMessage.class
                        );
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    @Order(15)
    @DisplayName("Test declineReservation when owner has no verificator")
    public void testDeclineReservationWhenOwnerHasNoVerificator() {
        HttpHeaders headers = new HttpHeaders();
        headers
                .set(
                        "Authorization",
                        "Bearer " + logIn(
                                "ownerwithoutverificator@gmail.com", "ownerwithoutverificator123"
                        )
                );

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<ErrorMessage> responseEntity =
                restTemplate
                        .exchange(
                                "/api/v1/reservations/1/status/declined",
                                HttpMethod.PUT,
                                requestEntity,
                                ErrorMessage.class
                        );
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(
                Objects.requireNonNull(responseEntity.getBody()).getMessage(),
                "A non-verified owner cannot decline reservations."
        );
    }

    @Test
    @Order(16)
    @DisplayName("Test declineReservation when owner is not verified")
    public void testDeclineReservationWhenOwnerIsNotVerified() {
        HttpHeaders headers = new HttpHeaders();
        headers
                .set(
                        "Authorization",
                        "Bearer " + logIn("nonverifiedowner@gmail.com", "nonverifiedowner123")
                );

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<ErrorMessage> responseEntity =
                restTemplate
                        .exchange(
                                "/api/v1/reservations/1/status/declined",
                                HttpMethod.PUT,
                                requestEntity,
                                ErrorMessage.class
                        );
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(
                Objects.requireNonNull(responseEntity.getBody()).getMessage(),
                "A non-verified owner cannot decline reservations."
        );
    }

    @Test
    @Order(17)
    @DisplayName("Test declineReservation when owner does not own the accommodation for which the reservation was made")
    public void testDeclineReservationWhenOwnerDoesNotOwnReservedAccommodation() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + logIn("owneraccsnores@gmail.com", "owner123"));

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<ErrorMessage> responseEntity =
                restTemplate
                        .exchange(
                                "/api/v1/reservations/1/status/declined",
                                HttpMethod.PUT,
                                requestEntity,
                                ErrorMessage.class
                        );
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(
                Objects.requireNonNull(responseEntity.getBody()).getMessage(),
                "Only the owner of the reserved accommodation can decline it's reservations."
        );
    }

    @Test
    @Order(18)
    @DisplayName("Test declineReservation when reservation is not on waiting")
    public void testDeclineReservationWhenReservationIsNotOnWaiting() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + logIn("owner@gmail.com", "owner123"));

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<ErrorMessage> responseEntity =
                restTemplate
                        .exchange(
                                "/api/v1/reservations/1/status/declined",
                                HttpMethod.PUT,
                                requestEntity,
                                ErrorMessage.class
                        );
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(
                Objects.requireNonNull(responseEntity.getBody()).getMessage(),
                "Reservation has already been accepted, denied or cancelled"
        );
    }

    @Test
    @Order(19)
    @DisplayName("Test declineReservation when reservation has begun")
    public void testDeclineReservationWhenReservationHasBegun() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + logIn("owner@gmail.com", "owner123"));

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<ErrorMessage> responseEntity =
                restTemplate
                        .exchange(
                                "/api/v1/reservations/8/status/declined",
                                HttpMethod.PUT,
                                requestEntity,
                                ErrorMessage.class
                        );
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(
                Objects.requireNonNull(responseEntity.getBody()).getMessage(),
                "Reservation has already begun and cannot be declined."
        );
    }
}
