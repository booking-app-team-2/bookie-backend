package booking_app_team_2.bookie.controller;

import booking_app_team_2.bookie.domain.AccommodationReview;
import booking_app_team_2.bookie.domain.Owner;
import booking_app_team_2.bookie.domain.OwnerReview;
import booking_app_team_2.bookie.domain.Review;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/api/v1/owner-reviews")
public class OwnerReviewController {
    @PutMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OwnerReview> reportReview(@RequestBody Review review, @PathVariable Long id) throws Exception{
        OwnerReview ownerReview=new OwnerReview();
        if(ownerReview==null){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<OwnerReview>(ownerReview, HttpStatus.OK);
    }
    @GetMapping(value = "/{ownerId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<OwnerReview>> getOwnersReviews(@PathVariable Long ownerId){
        Collection<OwnerReview> ownerReviews = Collections.<OwnerReview>emptyList();
        if (ownerReviews.isEmpty()) {
            return new ResponseEntity<Collection<OwnerReview>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<OwnerReview>>(ownerReviews, HttpStatus.OK);
    }
    @GetMapping(value = "/reported/{ownerId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<OwnerReview>> getOwnersReportedReviews(@PathVariable Long ownerId){
        Collection<OwnerReview> ownerReviews = Collections.<OwnerReview>emptyList();
        if (ownerReviews.isEmpty()) {
            return new ResponseEntity<Collection<OwnerReview>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<OwnerReview>>(ownerReviews, HttpStatus.OK);
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<OwnerReview> deleteReview(@PathVariable Long id){
        OwnerReview ownerReview=new OwnerReview();
        if(ownerReview==null){
            return new ResponseEntity<OwnerReview>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<OwnerReview>(HttpStatus.NO_CONTENT);
    }
}
