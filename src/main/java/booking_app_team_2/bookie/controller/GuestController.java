package booking_app_team_2.bookie.controller;

import booking_app_team_2.bookie.domain.Guest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/api/v1/guests")
public class GuestController {

    // TODO: Implement GuestService
    /* @Autowired
    private GuestService guestService; */

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Guest>> getGuests() {
        Collection<Guest> guests = Collections.<Guest>emptyList();              // Empty list right now
        return new ResponseEntity<Collection<Guest>>(guests, HttpStatus.OK);
    }

    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Guest> getGuest(@PathVariable("id") Long id) {
        Guest guest = new Guest() {};                                           // Empty object right now

        if (guest == null) {
            return new ResponseEntity<Guest>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Guest>(guest, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Guest> createGuest(@RequestBody Guest guest) {
        Guest savedGuest = new Guest() {};                                      // Empty object right now
        return new ResponseEntity<Guest>(savedGuest, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Guest> updateGuest(@RequestBody Guest guest, @PathVariable Long id) {
        Guest guestForUpdate = new Guest() {};
        //guestForUpdate.copyValues(guest);
        // TODO: Create copy constructor for Guest
        //Guest updatedGuest = guestService.update(guestForUpdate);
        Guest updatedGuest = guestForUpdate;
        if (updatedGuest == null){
            return new ResponseEntity<Guest>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Guest>(updatedGuest, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Guest> deleteGuest(@PathVariable("id") Long id) {
        //guestService.delete(id);
        return new ResponseEntity<Guest>(HttpStatus.NO_CONTENT);
    }
}
