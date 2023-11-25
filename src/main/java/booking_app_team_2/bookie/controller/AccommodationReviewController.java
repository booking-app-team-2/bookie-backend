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
    //Reporting reviews
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationReview> reportReview(@RequestBody AccommodationReview review, @PathVariable Long id) {
        AccommodationReview accommodationReview = new AccommodationReview();
        if (accommodationReview == null) {
            return new ResponseEntity<AccommodationReview>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<AccommodationReview>(accommodationReview, HttpStatus.OK);
    }

    //Posting a review
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationReview> createReview(@RequestBody AccommodationReview accommodationReview) {
        AccommodationReview newReview = new AccommodationReview();
        if (newReview == null) {
            return new ResponseEntity<AccommodationReview>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<AccommodationReview>(newReview, HttpStatus.CREATED);
    }

    //Getting all unapproved reviews
    @GetMapping(value = "/unapproved", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationReview>> getUnapprovedReviews() {
        Collection<AccommodationReview> accommodationReviews = Collections.emptyList();
        if (accommodationReviews.isEmpty()) {
            return new ResponseEntity<Collection<AccommodationReview>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<AccommodationReview>>(accommodationReviews, HttpStatus.OK);
    }

    //Approving an unapproved review
    @PutMapping(value = "/unapproved/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationReview> approveReview(@RequestBody AccommodationReview review, @PathVariable Long id) {
        AccommodationReview accommodationReview = new AccommodationReview();
        if (accommodationReview == null) {
            return new ResponseEntity<AccommodationReview>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<AccommodationReview>(accommodationReview, HttpStatus.OK);
    }

    //Denying a review
    @DeleteMapping(value = "/unapproved/{id}")
    public ResponseEntity<AccommodationReview> denyReview(@PathVariable Long id) {
        AccommodationReview accommodationReview = new AccommodationReview();
        if (accommodationReview == null) {
            return new ResponseEntity<AccommodationReview>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<AccommodationReview>(HttpStatus.NO_CONTENT);
    }

    //Deleting a review
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<AccommodationReview> deleteReview(@PathVariable Long id) {
        AccommodationReview accommodationReview = new AccommodationReview();
        if (accommodationReview == null) {
            return new ResponseEntity<AccommodationReview>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<AccommodationReview>(HttpStatus.NO_CONTENT);
    }

    //Getting reviews for one accommodation
    @GetMapping(value = "/{accommodationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationReview>> getAccommodationReviews(@PathVariable Long accommodationId) {
        Collection<AccommodationReview> accommodationReviews = Collections.emptyList();
        if (accommodationReviews.isEmpty()) {
            return new ResponseEntity<Collection<AccommodationReview>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<AccommodationReview>>(accommodationReviews, HttpStatus.OK);
    }

}
