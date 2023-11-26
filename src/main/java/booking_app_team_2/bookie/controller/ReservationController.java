package booking_app_team_2.bookie.controller;

import booking_app_team_2.bookie.dto.ReservationDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashSet;

@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationController {
    @GetMapping
    public ResponseEntity<Collection<ReservationDTO>> getReservations(@RequestParam(required = false) String name,
                                                                      @RequestParam(required = false) Long startDate,
                                                                      @RequestParam(required = false) Long endDate,
                                                                      @RequestParam(required = false) String status) {
        return new ResponseEntity<>(new HashSet<>(), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationDTO reservation) {
        return new ResponseEntity<>(new ReservationDTO(), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationDTO> updateReservation(@RequestBody ReservationDTO reservationDTO,
                                                            @PathVariable Long id) {
        ReservationDTO reservationDTO1 = new ReservationDTO();
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
}