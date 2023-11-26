package booking_app_team_2.bookie.controller;

import booking_app_team_2.bookie.domain.AccountVerificator;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/api/v1/account-verifications")
public class AccountVerificatorController {
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccountVerificator>> getAccountVerificators() {
        Collection<AccountVerificator> verifications = Collections.emptyList();
        return new ResponseEntity<>(verifications, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountVerificator> getAccountVerification(@PathVariable("id") Long id) {
        // QUESTION: Id of verificator or user? Does it even matter?
        AccountVerificator verificator = new AccountVerificator() {};
        if (verificator == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(verificator, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountVerificator> createVerificator(@RequestBody AccountVerificator verificator) {
        AccountVerificator savedVerificator = new AccountVerificator() {};
        return new ResponseEntity<>(savedVerificator, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountVerificator> updateVerificator(@RequestBody AccountVerificator verificator, @PathVariable Long id) {
        AccountVerificator verificatorForUpdate = new AccountVerificator() {
        };

        // TODO: Create copy constructor for AccountVerificator

        AccountVerificator updatedVerificator = verificatorForUpdate;
        if (updatedVerificator == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(updatedVerificator, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteGuest(@PathVariable("id") Long id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
