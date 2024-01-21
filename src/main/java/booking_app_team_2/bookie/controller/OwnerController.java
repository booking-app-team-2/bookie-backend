package booking_app_team_2.bookie.controller;

import booking_app_team_2.bookie.domain.Guest;
import booking_app_team_2.bookie.domain.Owner;
import booking_app_team_2.bookie.domain.User;
import booking_app_team_2.bookie.dto.UserDTO;
import booking_app_team_2.bookie.exception.HttpTransferException;
import booking_app_team_2.bookie.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/owners")
public class OwnerController {

    private OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService){
        this.ownerService=ownerService;
    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Owner>> getOwners() {
        Collection<Owner> owners = Collections.emptyList();
        return new ResponseEntity<>(owners, HttpStatus.OK);
    }

    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Owner> getOwner(@PathVariable("id") Long id) {
        Owner owner = new Owner() {};

        if (owner.equals(null)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(owner, HttpStatus.OK);
    }

    @GetMapping(value = "accommodation/{accommodationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> getOwnerByAccommodation(@PathVariable Long accommodationId) {
        Long ownerId=ownerService.findIdByAccommodationId(accommodationId);
        return new ResponseEntity<>(ownerId, HttpStatus.OK);
    }

    @GetMapping(value = "/report",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createReport(@RequestBody String reportPreferences){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Owner> createOwner(@RequestBody Owner owner) {
        Owner savedOwner = new Owner() {};
        return new ResponseEntity<>(savedOwner, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Owner> updateOwner(@RequestBody Owner owner, @PathVariable Long id) {
        Owner ownerForUpdate = new Owner() {};

        // TODO: Create copy constructor for Guest

        Owner updatedOwner = ownerForUpdate;
        if (updatedOwner.equals(null)){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(updatedOwner, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteOwner(@PathVariable("id") Long id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
