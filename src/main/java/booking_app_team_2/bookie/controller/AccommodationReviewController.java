package booking_app_team_2.bookie.controller;

import booking_app_team_2.bookie.domain.AccommodationReview;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/api/v1/accommodation-reviews")
public class AccommodationReviewController {
    @GetMapping(value = "/{accommodationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationReview>> getAccommodationReview(@PathVariable Long accommodationId) {
        Collection<AccommodationReview> accommodationReviews = Collections.emptyList();
        return new ResponseEntity<>(accommodationReviews, HttpStatus.OK);
    }

    @GetMapping(value = "/unapproved", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationReview>> getUnapprovedReviews() {
        Collection<AccommodationReview> accommodationReviews = Collections.emptyList();
        return new ResponseEntity<>(accommodationReviews, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationReview> createReview(@RequestBody AccommodationReview accommodationReview) {
        AccommodationReview newReview = new AccommodationReview();
        if (newReview.equals(null)) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(newReview, HttpStatus.CREATED);
    }

    @PutMapping(value = "/unapproved/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationReview> approveReview(@RequestBody AccommodationReview review, @PathVariable Long id) {
        AccommodationReview accommodationReview = new AccommodationReview();
        if (accommodationReview.equals(null)) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(accommodationReview, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationReview> reportReview(@RequestBody AccommodationReview review, @PathVariable Long id) {
        AccommodationReview accommodationReview = new AccommodationReview();
        if (accommodationReview.equals(null)) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(accommodationReview, HttpStatus.OK);
    }

    @DeleteMapping(value = "/unapproved/{id}")
    public ResponseEntity<Void> denyReview(@PathVariable Long id) {
        AccommodationReview accommodationReview = new AccommodationReview();
        if (accommodationReview.equals(null)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        AccommodationReview accommodationReview = new AccommodationReview();
        if (accommodationReview.equals(null)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
