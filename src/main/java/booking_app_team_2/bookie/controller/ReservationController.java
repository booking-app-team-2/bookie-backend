package booking_app_team_2.bookie.controller;

import booking_app_team_2.bookie.domain.Guest;
import booking_app_team_2.bookie.domain.ReservationStatus;
import booking_app_team_2.bookie.dto.ReservationGuestDTO;
import booking_app_team_2.bookie.dto.ReservationOwnerDTO;
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
import java.util.List;

@RestController
@RequestMapping("/api/v1/reservations")
@CrossOrigin
public class ReservationController {
    private ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping(value = "/reservee")
    @PreAuthorize("hasAuthority('Guest')")
    public ResponseEntity<Collection<ReservationGuestDTO>> searchAndFilterReservationsGuest (
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            @RequestParam(value = "start_timestamp", required = false) Long startTimestamp,
            @RequestParam(value = "end_timestamp", required = false) Long endTimestamp,
            @RequestParam(
                    value = "status",
                    required = false,
                    defaultValue = "Waiting, Accepted, Declined, Cancelled"
            ) List<ReservationStatus> statuses,
            HttpServletRequest httpServletRequest) {
        return new ResponseEntity<>(
                reservationService.findAllForGuest(name, startTimestamp, endTimestamp, statuses, httpServletRequest),
                HttpStatus.OK
        );
    }

    @GetMapping(value = "/accommodation/owner")
    @PreAuthorize("hasAuthority('Owner')")
    public ResponseEntity<Collection<ReservationOwnerDTO>> searchAndFilterReservationsOwner (
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            @RequestParam(value = "start_timestamp", required = false) Long startTimestamp,
            @RequestParam(value = "end_timestamp", required = false) Long endTimestamp,
            @RequestParam(
                    value = "status",
                    required = false,
                    defaultValue = "Waiting, Accepted, Declined, Cancelled"
            ) List<ReservationStatus> statuses,
            HttpServletRequest httpServletRequest) {
        return new ResponseEntity<>(
                reservationService.findAllForOwner(name, startTimestamp, endTimestamp, statuses, httpServletRequest),
                HttpStatus.OK
        );
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

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationStatusDTO> updateReservation(@RequestBody ReservationStatusDTO reservationDTO,
                                                            @PathVariable Long id) {
        ReservationStatusDTO reservationDTO1 = new ReservationStatusDTO();
        if (reservationDTO1.equals(null))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(reservationDTO1, HttpStatus.OK);
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
