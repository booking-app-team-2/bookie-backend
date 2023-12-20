package booking_app_team_2.bookie.controller;

import booking_app_team_2.bookie.domain.Accommodation;
import booking_app_team_2.bookie.domain.Guest;
import booking_app_team_2.bookie.repository.GuestRepository;
import booking_app_team_2.bookie.service.GuestService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@Setter
@RestController
@RequestMapping("/api/v1/guests")
public class GuestController {

    private GuestService guestService;
    @Autowired
    public GuestController(GuestService guestService){
        this.guestService=guestService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Guest>> getGuests() {
        Collection<Guest> guests = guestService.findAll();
        return new ResponseEntity<>(guests, HttpStatus.OK);
    }

    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Guest> getGuest(@PathVariable("id") Long id) {
        Guest guest = new Guest() {};                                           // Empty object right now

        if (guest.equals(null)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(guest, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Guest> createGuest(@RequestBody Guest guest) {
        Guest savedGuest = new Guest() {};                                      // Empty object right now
        return new ResponseEntity<>(savedGuest, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Guest> updateGuest(@RequestBody Guest guest, @PathVariable Long id) {
        Guest guestForUpdate = new Guest() {};
        //guestForUpdate.copyValues(guest);
        // TODO: Create copy constructor for Guest
        //Guest updatedGuest = guestService.update(guestForUpdate);
        Guest updatedGuest = guestForUpdate;
        if (updatedGuest.equals(null)){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(updatedGuest, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Guest> deleteGuest(@PathVariable("id") Long id) {
        //guestService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping(value = "/{id}/favourite-accommodations",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Accommodation>> getFavouriteAccommodations(@PathVariable Long id){
        Collection<Accommodation> accommodations= Collections.emptyList();
        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }
    @PostMapping(value = "/{id}/favourite-accommodations",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Guest> addFavouriteAccommodation(@PathVariable Long id, @RequestBody Long accommodationId){
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
