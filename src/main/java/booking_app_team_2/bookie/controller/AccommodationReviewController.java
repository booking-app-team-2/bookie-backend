package booking_app_team_2.bookie.controller;

import booking_app_team_2.bookie.domain.Accommodation;
import booking_app_team_2.bookie.domain.AccommodationReview;
import booking_app_team_2.bookie.domain.Guest;
import booking_app_team_2.bookie.dto.AccommodationReviewDTO;
import booking_app_team_2.bookie.dto.PeriodDTO;
import booking_app_team_2.bookie.dto.UserDTO;
import booking_app_team_2.bookie.repository.GuestRepository;
import booking_app_team_2.bookie.service.AccommodationReviewService;
import booking_app_team_2.bookie.service.AccommodationService;
import booking_app_team_2.bookie.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/accommodation-reviews")
public class AccommodationReviewController {
    private AccommodationReviewService accommodationReviewService;
    private AccommodationService accommodationService;
    private GuestService guestService;

    @Autowired
    public AccommodationReviewController(AccommodationReviewService accommodationReviewService, AccommodationService accommodationService, GuestService guestService){
        this.accommodationReviewService=accommodationReviewService;
        this.accommodationService=accommodationService;
        this.guestService=guestService;
    }
    @GetMapping(value = "/{accommodationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationReviewDTO>> getAccommodationReviews(@PathVariable Long accommodationId) {
        Collection<AccommodationReview> accommodationReviews = accommodationReviewService.findAccommodationReviews(accommodationId);
        Collection<AccommodationReviewDTO> accommodationReviewDTOS= new ArrayList<>(Collections.emptyList());
        for (AccommodationReview accommodationReview : accommodationReviews) {
            AccommodationReviewDTO reviewDTO = new AccommodationReviewDTO(accommodationReview);
            accommodationReviewDTOS.add(reviewDTO);
        }
        if(accommodationReviewDTOS.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(accommodationReviewDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/{accommodationId}/grade", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Float> getAverageGrade(@PathVariable Long accommodationId) {
        Collection<AccommodationReview> accommodationReviews = accommodationReviewService.findAccommodationReviews(accommodationId);
        if(accommodationReviews.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Float averageGrade= accommodationReviewService.calculateAverageGrade(accommodationReviews);
        return new ResponseEntity<>(averageGrade, HttpStatus.OK);
    }

    @GetMapping(value = "/unapproved", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationReviewDTO>> getUnapprovedReviews() {
        Collection<AccommodationReview> accommodationReviews = accommodationReviewService.findUnapprovedReviews();
        Collection<AccommodationReviewDTO> accommodationReviewDTOS= new ArrayList<>(Collections.emptyList());
        for (AccommodationReview accommodationReview : accommodationReviews) {
            AccommodationReviewDTO reviewDTO = new AccommodationReviewDTO(accommodationReview);
            accommodationReviewDTOS.add(reviewDTO);
        }
        if(accommodationReviewDTOS.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(accommodationReviewDTOS, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('Guest')")
    public ResponseEntity<AccommodationReviewDTO> createReview(@RequestBody AccommodationReviewDTO accommodationReviewDTO) {
        AccommodationReview newReview = new AccommodationReview(accommodationReviewDTO);
        Optional<Accommodation> optionalAccommodation=accommodationService.findOne(accommodationReviewDTO.getAccommodationId());
        optionalAccommodation.ifPresent(newReview::setAccommodation);
        Optional<Guest> optionalGuest=guestService.findOne(accommodationReviewDTO.getReviewerId());
        optionalGuest.ifPresent(newReview::setReviewer);
        accommodationReviewService.addAccommodationReview(newReview);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/unapproved/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Void> approveReview(@PathVariable Long id) {
        accommodationReviewService.approveReview(id);
        return new ResponseEntity<>(HttpStatus.OK);
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
    @PreAuthorize("hasAuthority('Guest')")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        accommodationReviewService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
