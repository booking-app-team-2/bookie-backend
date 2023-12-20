package booking_app_team_2.bookie.controller;

import booking_app_team_2.bookie.domain.*;
import booking_app_team_2.bookie.dto.AccommodationApprovalDTO;
import booking_app_team_2.bookie.dto.AccommodationAutoAcceptDTO;
import booking_app_team_2.bookie.dto.AccommodationDTO;
import booking_app_team_2.bookie.dto.OwnerDTO;
import booking_app_team_2.bookie.service.AccommodationService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

@Setter
@RestController
@RequestMapping("/api/v1/accommodations")
public class AccommodationController {
    private AccommodationService accommodationService;

    @Autowired
    public AccommodationController(AccommodationService accommodationService) {
        this.accommodationService = accommodationService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Accommodation>> getAccommodations() {
        Collection<Accommodation> accommodations = accommodationService.findAll();
        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }

    @GetMapping(value = "/unapproved", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationDTO>> getUnapprovedAccommodations() {
        Collection<AccommodationDTO> accommodations = accommodationService
                .findAllByApproved(false)
                .stream()
                .map(accommodation -> new AccommodationDTO())
                .toList();

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
    public ResponseEntity<Collection<AccommodationDTO>> getFilteredAccommodation(
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
        Collection<AccommodationDTO> accommodations = Collections.emptyList();
        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }

    @GetMapping(value ="/owner-accommodations/{owner_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationDTO>> getAccommodationsByOwner(@PathVariable Long owner_id) {
        Collection<AccommodationDTO> accommodations = Collections.emptyList();
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

    @GetMapping(value = "/{accommodationId}/owner")
    public ResponseEntity<OwnerDTO> getOwner(@PathVariable Long accommodationId) {
        return new ResponseEntity<>(new OwnerDTO(), HttpStatus.OK);
    }

    @GetMapping(value = "/{accommodationId}/average-rating")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long accommodationId) {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping(value = "/{accommodationId}/reservation-auto-accept")
    public ResponseEntity<Boolean> isReservationAutoAccepted(@PathVariable Long accommodationId) {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Accommodation> createAccommodation(@RequestBody Accommodation accommodation) {
        Accommodation savedAccommodation = new Accommodation() {};
        return new ResponseEntity<>(savedAccommodation, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}/is-approved", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationApprovalDTO> updateAccommodationIsApproved(
            @PathVariable Long id,
            @RequestBody AccommodationApprovalDTO accommodationApprovalDTO
    ) {
        accommodationService.updateIsApproved(id, accommodationApprovalDTO);

        return new ResponseEntity<>(accommodationApprovalDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/reservation-auto-accept", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationAutoAcceptDTO> updateAccommodation(@RequestBody AccommodationAutoAcceptDTO accommodationDTO,
                                                                              @PathVariable Long id) {
        AccommodationAutoAcceptDTO accommodationDTO1 = new AccommodationAutoAcceptDTO();
        if (accommodationDTO1.equals(null))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(accommodationDTO1, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteAccommodation(@PathVariable("id") Long id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
