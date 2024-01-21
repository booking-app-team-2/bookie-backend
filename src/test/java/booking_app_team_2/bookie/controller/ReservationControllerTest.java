package booking_app_team_2.bookie.controller;

import booking_app_team_2.bookie.domain.Period;
import booking_app_team_2.bookie.dto.PeriodDTO;
import booking_app_team_2.bookie.dto.ReservationDTO;
import booking_app_team_2.bookie.dto.UserAuthenticationDTO;
import booking_app_team_2.bookie.dto.UserTokenStateDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource( locations = "classpath:application-test.properties")
@ActiveProfiles("test")
public class ReservationControllerTest {

    @Autowired
    private  TestRestTemplate restTemplate;

    @Autowired
    private  ObjectMapper objectMapper;

    private String guestToken;

    @BeforeAll
    public void login(){
        UserAuthenticationDTO userAuthenticationDTO=new UserAuthenticationDTO();
        userAuthenticationDTO.setUsername("eikoob@gmail.com");
        userAuthenticationDTO.setPassword("eikoob123");
        ResponseEntity<UserTokenStateDTO> responseEntityGuest=restTemplate.postForEntity("/authentication/login",userAuthenticationDTO,UserTokenStateDTO.class);
        guestToken=responseEntityGuest.getBody().getJWT();
    }

    @Test
    @DisplayName("Test createReservation endpoint with valid data")
    public void testCreateReservation() throws JsonProcessingException {

        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now().plusDays(2);
        PeriodDTO periodDTO = new PeriodDTO(new Period(start, end));

        ReservationDTO requestReservationDTO = new ReservationDTO(3, 1L, 3L, periodDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization","Bearer "+guestToken);
        HttpEntity<String> requestEntity = new HttpEntity<>(objectMapper.writeValueAsString(requestReservationDTO), headers);
        ResponseEntity<ReservationDTO> response = restTemplate.exchange(
                "/api/v1/reservations",
                HttpMethod.POST,
                requestEntity,
                ReservationDTO.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    @DisplayName("Test createReservation endpoint with invalid date")
    public void testCreateReservationWithInvalidData() throws JsonProcessingException {

        LocalDate start = LocalDate.now().minusDays(1);
        LocalDate end = LocalDate.now().plusDays(2);
        PeriodDTO periodDTO = new PeriodDTO(new Period(start, end));

        ReservationDTO requestReservationDTO = new ReservationDTO(3, 1L, 3L, periodDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization","Bearer "+guestToken);
        HttpEntity<String> requestEntity = new HttpEntity<>(objectMapper.writeValueAsString(requestReservationDTO), headers);
        ResponseEntity<ReservationDTO> response = restTemplate.exchange(
                "/api/v1/reservations",
                HttpMethod.POST,
                requestEntity,
                ReservationDTO.class
        );

        //You cannot reserve in the past
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @DisplayName("Test createReservation endpoint with invalid accommodation")
    public void testCreateReservationWithBadRequest() throws JsonProcessingException {

        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now().plusDays(2);
        PeriodDTO periodDTO = new PeriodDTO(new Period(start, end));

        //This accommodation id does not exist
        ReservationDTO requestReservationDTO = new ReservationDTO(3, 7L, 3L, periodDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization","Bearer "+guestToken);
        HttpEntity<String> requestEntity = new HttpEntity<>(objectMapper.writeValueAsString(requestReservationDTO), headers);
        ResponseEntity<ReservationDTO> response = restTemplate.exchange(
                "/api/v1/reservations",
                HttpMethod.POST,
                requestEntity,
                ReservationDTO.class
        );
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }
    @Test
    @DisplayName("Test createReservation endpoint with invalid number of guests")
    public void testCreateReservationWithInvalidNumberOfGuests() throws JsonProcessingException {

        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now().plusDays(2);
        PeriodDTO periodDTO = new PeriodDTO(new Period(start, end));

        //This accommodation can accommodate 2 to 4 people
        ReservationDTO requestReservationDTO = new ReservationDTO(7, 1L, 3L, periodDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization","Bearer "+guestToken);
        HttpEntity<String> requestEntity = new HttpEntity<>(objectMapper.writeValueAsString(requestReservationDTO), headers);
        ResponseEntity<ReservationDTO> response = restTemplate.exchange(
                "/api/v1/reservations",
                HttpMethod.POST,
                requestEntity,
                ReservationDTO.class
        );
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }
    @Test
    @DisplayName("Test createReservation endpoint with invalid reserveeId")
    public void testCreateReservationWithInvalidReserveeId() throws JsonProcessingException {

        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now().plusDays(2);
        PeriodDTO periodDTO = new PeriodDTO(new Period(start, end));

        //This reservee does not exist
        ReservationDTO requestReservationDTO = new ReservationDTO(3, 1L, 9L, periodDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization","Bearer "+guestToken);
        HttpEntity<String> requestEntity = new HttpEntity<>(objectMapper.writeValueAsString(requestReservationDTO), headers);
        ResponseEntity<ReservationDTO> response = restTemplate.exchange(
                "/api/v1/reservations",
                HttpMethod.POST,
                requestEntity,
                ReservationDTO.class
        );
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test
    @DisplayName("Test createReservation endpoint with invalid User type")
    public void testCreateReservationWithInvalidGuest() throws JsonProcessingException {

        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now().plusDays(2);
        PeriodDTO periodDTO = new PeriodDTO(new Period(start, end));

        //This reservee is not a guest
        ReservationDTO requestReservationDTO = new ReservationDTO(3, 1L, 1L, periodDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization","Bearer "+guestToken);
        HttpEntity<String> requestEntity = new HttpEntity<>(objectMapper.writeValueAsString(requestReservationDTO), headers);
        ResponseEntity<ReservationDTO> response = restTemplate.exchange(
                "/api/v1/reservations",
                HttpMethod.POST,
                requestEntity,
                ReservationDTO.class
        );
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }
    @Test
    @DisplayName("Test createReservation endpoint with invalid Period")
    public void testCreateReservationWithInvalidPeriod() throws JsonProcessingException {

        LocalDate start = LocalDate.now().plusDays(40);
        LocalDate end = LocalDate.now().plusDays(42);
        PeriodDTO periodDTO = new PeriodDTO(new Period(start, end));

        //This accommodation is not available in this period
        ReservationDTO requestReservationDTO = new ReservationDTO(3, 1L, 3L, periodDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization","Bearer "+guestToken);
        HttpEntity<String> requestEntity = new HttpEntity<>(objectMapper.writeValueAsString(requestReservationDTO), headers);
        ResponseEntity<ReservationDTO> response = restTemplate.exchange(
                "/api/v1/reservations",
                HttpMethod.POST,
                requestEntity,
                ReservationDTO.class
        );
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }



}
