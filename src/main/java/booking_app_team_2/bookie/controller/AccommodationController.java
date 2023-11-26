package booking_app_team_2.bookie.controller;

import booking_app_team_2.bookie.domain.Accommodation;
import booking_app_team_2.bookie.domain.Amenities;
import booking_app_team_2.bookie.domain.AvailabilityPeriod;
import booking_app_team_2.bookie.domain.Image;
import booking_app_team_2.bookie.dto.AccommodationDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashSet;

@RestController
@RequestMapping("/api/v1/accommodations")
public class AccommodationController {
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationDTO> getAccommodation(@PathVariable Long id) {
        AccommodationDTO accommodationDTO = new AccommodationDTO();
        if (accommodationDTO.equals(null))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(accommodationDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{accommodationId}/images")
    public ResponseEntity<Collection<Image>> getImages(@PathVariable Long accommodationId) {
        return new ResponseEntity<>(new HashSet<>(), HttpStatus.OK);
    }

    @GetMapping(value = "/{accommodationId}/amenities")
    public ResponseEntity<Collection<Amenities>> getAmenities(@PathVariable Long accommodationId) {
        return new ResponseEntity<>(new HashSet<>(), HttpStatus.OK);
    }

    @GetMapping(value = "/{accommodationId}/availability-periods")
    public ResponseEntity<Collection<AvailabilityPeriod>> getAvailabilityPeriods(@PathVariable Long accommodationId) {
        return new ResponseEntity<>(new HashSet<>(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationDTO> updateAccommodation(@RequestBody AccommodationDTO accommodationDTO,
                                                                @PathVariable Long id) {
        AccommodationDTO accommodationDTO1 = new AccommodationDTO();
        if (accommodationDTO1.equals(null))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(accommodationDTO1, HttpStatus.OK);
    }
}
