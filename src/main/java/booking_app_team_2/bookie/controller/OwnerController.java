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
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Owner>> getOwners() {
        Collection<Owner> owners = Collections.emptyList();              // Empty list right now
        return new ResponseEntity<>(owners, HttpStatus.OK);
    }

    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Owner> getOwner(@PathVariable("id") Long id) {
        Owner owner = new Owner() {};                                           // Empty object right now

        if (owner == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(owner, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Owner> createOwner(@RequestBody Owner owner) {
        Owner savedOwner = new Owner() {};                                      // Empty object right now
        return new ResponseEntity<>(savedOwner, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Owner> updateOwner(@RequestBody Owner owner, @PathVariable Long id) {
        Owner ownerForUpdate = new Owner() {};
        //ownerForUpdate.copyValues(owner);
        // TODO: Create copy constructor for Guest
        //Owner updatedOwner = ownerService.update(ownerForUpdate);
        Owner updatedOwner = ownerForUpdate;
        if (updatedOwner == null){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(updatedOwner, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Owner> deleteOwner(@PathVariable("id") Long id) {
        //ownerService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/report",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createReport(@RequestBody String reportPreferences){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
