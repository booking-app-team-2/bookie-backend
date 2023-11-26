package booking_app_team_2.bookie.controller;

import booking_app_team_2.bookie.domain.Review;
import booking_app_team_2.bookie.dto.OwnerReviewDTO;
import booking_app_team_2.bookie.domain.OwnerReview;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Collections;

@RestController
@RequestMapping("/api/v1/owner-reviews")
public class OwnerReviewController {
    @GetMapping
    public ResponseEntity<Collection<OwnerReviewDTO>> getOwnerReviews() {
        return new ResponseEntity<>(new HashSet<>(), HttpStatus.OK);
    }

    @GetMapping(value = "/{ownerId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<OwnerReview>> getOwnersReviews(@PathVariable Long ownerId){
        Collection<OwnerReview> ownerReviews = Collections.emptyList();
        if (ownerReviews.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ownerReviews, HttpStatus.OK);
    }

    @GetMapping(value = "/reported/{ownerId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<OwnerReview>> getOwnersReportedReviews(@PathVariable Long ownerId){
        Collection<OwnerReview> ownerReviews = Collections.emptyList();
        if (ownerReviews.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ownerReviews, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OwnerReviewDTO> createOwnerReview(@RequestBody OwnerReviewDTO ownerReviewDTO) {
        return new ResponseEntity<>(new OwnerReviewDTO(), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OwnerReview> reportReview(@RequestBody Review review, @PathVariable Long id) throws Exception{
        OwnerReview ownerReview=new OwnerReview();
        if(ownerReview==null){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(ownerReview, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteOwnerReview(@PathVariable Long id) {
        OwnerReviewDTO ownerReviewDTO = new OwnerReviewDTO();
        if (ownerReviewDTO.equals(null))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
