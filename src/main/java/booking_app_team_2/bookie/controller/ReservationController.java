package booking_app_team_2.bookie.controller;

import booking_app_team_2.bookie.domain.Guest;
import booking_app_team_2.bookie.domain.ReservationStatus;
import booking_app_team_2.bookie.dto.*;
import booking_app_team_2.bookie.service.ReservationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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

    @GetMapping(value = "/status/cancelled")
    @PreAuthorize("hasAuthority('Owner')")
    public ResponseEntity<NumberOfCancelledReservationsDTO> getNumberOfCancelledReservationsForReservee(
            @RequestParam(value = "reservee_id") Long reserveeId,
            HttpServletRequest httpServletRequest
    ) {
        return new ResponseEntity<>(
                reservationService.getNumberOfCancelledReservations(reserveeId, httpServletRequest),
                HttpStatus.OK
        );
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
    public ResponseEntity<ReservationDTO> createReservation(@Valid @RequestBody ReservationDTO reservationDTO) {
        return new ResponseEntity<>(reservationService.createReservation(reservationDTO), HttpStatus.CREATED);
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

    @PutMapping(value = "/{id}/status/cancelled", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationStatusDTO> cancelReservation(@PathVariable Long id,
                                                                  HttpServletRequest httpServletRequest) {
        reservationService.cancelReservation(id, httpServletRequest);

        return new ResponseEntity<>(new ReservationStatusDTO(ReservationStatus.Cancelled), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('Guest')")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        reservationService.remove(id, httpServletRequest);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Autowired
    public void setReservationService(ReservationService reservationService) {
        this.reservationService = reservationService;
    }
}
