package booking_app_team_2.bookie.controller;

import booking_app_team_2.bookie.domain.Guest;
import booking_app_team_2.bookie.domain.Owner;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/api/v1/owners")
public class OwnerController {
    // TODO: Implement OwnerService
    /* @Autowired
    private OwnerService ownerService; */

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Owner>> getOwners() {
        Collection<Owner> owners = Collections.<Owner>emptyList();              // Empty list right now
        return new ResponseEntity<Collection<Owner>>(owners, HttpStatus.OK);
    }

    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Owner> getOwner(@PathVariable("id") Long id) {
        Owner owner = new Owner() {};                                           // Empty object right now

        if (owner == null) {
            return new ResponseEntity<Owner>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Owner>(owner, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Owner> createOwner(@RequestBody Owner owner) {
        Owner savedOwner = new Owner() {};                                      // Empty object right now
        return new ResponseEntity<Owner>(savedOwner, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Owner> updateOwner(@RequestBody Owner owner, @PathVariable Long id) {
        Owner ownerForUpdate = new Owner() {};
        //ownerForUpdate.copyValues(owner);
        // TODO: Create copy constructor for Guest
        //Owner updatedOwner = ownerService.update(ownerForUpdate);
        Owner updatedOwner = ownerForUpdate;
        if (updatedOwner == null){
            return new ResponseEntity<Owner>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Owner>(updatedOwner, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Owner> deleteOwner(@PathVariable("id") Long id) {
        //ownerService.delete(id);
        return new ResponseEntity<Owner>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/report",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createReport(@RequestBody String reportPreferences){
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
