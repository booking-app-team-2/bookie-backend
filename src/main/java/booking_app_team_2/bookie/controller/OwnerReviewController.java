package booking_app_team_2.bookie.controller;

import booking_app_team_2.bookie.dto.OwnerReviewDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashSet;

@RestController
@RequestMapping("/api/v1/owner-reviews")
public class OwnerReviewController {
    @GetMapping
    public ResponseEntity<Collection<OwnerReviewDTO>> getOwnerReviews() {
        return new ResponseEntity<>(new HashSet<>(), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OwnerReviewDTO> createOwnerReview(@RequestBody OwnerReviewDTO ownerReviewDTO) {
        return new ResponseEntity<>(new OwnerReviewDTO(), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteOwnerReview(@PathVariable Long id) {
        OwnerReviewDTO ownerReviewDTO = new OwnerReviewDTO();
        if (ownerReviewDTO.equals(null))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
