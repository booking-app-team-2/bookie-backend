package booking_app_team_2.bookie.controller;

import booking_app_team_2.bookie.domain.Guest;
import booking_app_team_2.bookie.domain.ReservationStatus;
import booking_app_team_2.bookie.dto.ReservationStatusDTO;
import booking_app_team_2.bookie.dto.ReservationDTO;
import booking_app_team_2.bookie.service.ReservationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashSet;

@RestController
@RequestMapping("/api/v1/reservations")
@CrossOrigin
public class ReservationController {
    private ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<Collection<ReservationDTO>> getReservations(@RequestParam(required = false) String name,
                                                                      @RequestParam(required = false) Long startDate,
                                                                      @RequestParam(required = false) Long endDate,
                                                                      @RequestParam(required = false) String status,
                                                                      @RequestParam(required = false) Long ownerId) {
        return new ResponseEntity<>(new HashSet<>(), HttpStatus.OK);
    }

    @GetMapping(value = "/cancelled")
    public ResponseEntity<Integer> getNumberOfCancelledReservationsForReservee(@RequestParam Long reserveeId) {
        Guest reservee = new Guest();
        if (reservee.equals(null))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping(value = "/accepted")
    public ResponseEntity<Integer> hasApprovedReservationForReservee(@RequestParam Long reserveeId) {
        Guest reservee = new Guest();
        if (reservee.equals(null))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('Guest')")
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationDTO reservationDTO) {
        reservationService.createReservation(reservationDTO);

        return new ResponseEntity<>(reservationDTO, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}/status/accepted", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('Owner')")
    public ResponseEntity<ReservationStatusDTO> acceptReservation(
            @PathVariable Long id,
            HttpServletRequest httpServletRequest
    ) {
        reservationService.acceptReservation(id, httpServletRequest);

        return new ResponseEntity<>(new ReservationStatusDTO(ReservationStatus.Accepted), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/status/declined", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('Owner')")
    public ResponseEntity<ReservationStatusDTO> declineReservation(
            @PathVariable Long id,
            HttpServletRequest httpServletRequest
    ) {
        reservationService.declineReservation(id, httpServletRequest);

        return new ResponseEntity<>(new ReservationStatusDTO(ReservationStatus.Declined), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        ReservationDTO reservationDTO = new ReservationDTO();
        if (reservationDTO.equals(null))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Autowired
    public void setReservationService(ReservationService reservationService) {
        this.reservationService = reservationService;
    }
}
