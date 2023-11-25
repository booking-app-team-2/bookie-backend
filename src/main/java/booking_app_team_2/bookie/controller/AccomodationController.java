package booking_app_team_2.bookie.controller;

import booking_app_team_2.bookie.domain.Accommodation;
import booking_app_team_2.bookie.domain.AccommodationType;
import booking_app_team_2.bookie.domain.Guest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/api/v1/accomodations")
public class AccomodationController {

    // TODO: Implement AccomodationService and PeriodService
    /* @Autowired
    private AccomodationService accomodationService;
    private PeriodService periodService*/

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Accommodation>> getAccomodations() {
        Collection<Accommodation> accommodations = Collections.<Accommodation>emptyList();              // Empty list right now
        return new ResponseEntity<Collection<Accommodation>>(accommodations, HttpStatus.OK);
    }

    @GetMapping(value ="/owner-accomodations/{owner_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Accommodation>> getAccomodationsByOwner() {
        Collection<Accommodation> accommodations = Collections.<Accommodation>emptyList();
        return new ResponseEntity<Collection<Accommodation>>(accommodations, HttpStatus.OK);
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
        Collection<Accommodation> accommodations = Collections.<Accommodation>emptyList();
        if (accommodations.isEmpty()) {
            return new ResponseEntity<Collection<Accommodation>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<Accommodation>>(accommodations, HttpStatus.OK);
    }


    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Accommodation> getAccomodation(@PathVariable("id") Long id) {
        Accommodation accommodation = new Accommodation() {};                                           // Empty object right now

        if (accommodation == null) {
            return new ResponseEntity<Accommodation>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Accommodation>(accommodation, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Accommodation> createAccomodation(@RequestBody Accommodation accommodation) {
        Accommodation savedAccomodation = new Accommodation() {};                                      // Empty object right now
        return new ResponseEntity<Accommodation>(savedAccomodation, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Accommodation> updateAccomodation(@RequestBody Accommodation accommodation, @PathVariable Long id) {
        Accommodation accommodationForUpdate = new Accommodation() {};
        //accommodationForUpdate.copyValues(accomodation);
        // TODO: Create copy constructor for Accomodation
        //Accomodation updatedAccomodation = accomodationService.update(accommodationForUpdate);
        Accommodation updatedAccomodation = accommodationForUpdate;
        if (updatedAccomodation == null){
            return new ResponseEntity<Accommodation>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Accommodation>(updatedAccomodation, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Accommodation> deleteAccomodation(@PathVariable("id") Long id) {
        //accomodationService.delete(id);
        return new ResponseEntity<Accommodation>(HttpStatus.NO_CONTENT);
    }
}
