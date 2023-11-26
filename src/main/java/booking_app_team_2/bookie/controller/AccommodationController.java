package booking_app_team_2.bookie.controller;

import booking_app_team_2.bookie.domain.*;
import booking_app_team_2.bookie.dto.AccommodationDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

@RestController
@RequestMapping("/api/v1/accommodations")
public class AccommodationController {
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Accommodation>> getAccommodations() {
        Collection<Accommodation> accommodations = Collections.emptyList();
        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationDTO> getAccommodation(@PathVariable Long id) {
        AccommodationDTO accommodationDTO = new AccommodationDTO();
        if (accommodationDTO.equals(null))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(accommodationDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Accommodation>> getFilteredAccommodation(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "amenities", required = false) String amenities,
            @RequestParam(value = "minimumGuests", required = false) Integer minGuests,
            @RequestParam(value = "maximumGuests", required = false) Integer maxGuests,
            @RequestParam(value = "minimumPrice", required = false) Double minPrice,
            @RequestParam(value = "maximumPrice", required = false) Double maxPrice,
            @RequestParam(value = "type", required = false) AccommodationType type,
            @RequestParam(value = "isPricedPerGuest", required = false) Boolean isPricedPerGuest,
            @RequestParam(value = "availability", required = false) String availability,
            @RequestParam(value = "averageRating", required = false) Double avgRating)
    {
        Collection<Accommodation> accommodations = Collections.emptyList();
        if (accommodations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }

    @GetMapping(value ="/owner-accommodations/{owner_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Accommodation>> getAccommodationsByOwner(@PathVariable Long owner_id) {
        Collection<Accommodation> accommodations = Collections.emptyList();
        return new ResponseEntity<>(accommodations, HttpStatus.OK);
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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Accommodation> createAccommodation(@RequestBody Accommodation accommodation) {
        Accommodation savedAccommodation = new Accommodation() {};
        return new ResponseEntity<>(savedAccommodation, HttpStatus.CREATED);
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

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteAccommodation(@PathVariable("id") Long id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
