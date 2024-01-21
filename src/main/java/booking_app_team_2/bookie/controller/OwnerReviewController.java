package booking_app_team_2.bookie.controller;

import booking_app_team_2.bookie.domain.Guest;
import booking_app_team_2.bookie.domain.Owner;
import booking_app_team_2.bookie.domain.OwnerReview;
import booking_app_team_2.bookie.domain.Review;
import booking_app_team_2.bookie.dto.OwnerReviewDTO;
import booking_app_team_2.bookie.exception.HttpTransferException;
import booking_app_team_2.bookie.service.GuestService;
import booking_app_team_2.bookie.service.OwnerReviewService;
import booking_app_team_2.bookie.service.OwnerService;
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
@RequestMapping("/api/v1/owner-reviews")
public class OwnerReviewController {
    private OwnerReviewService ownerReviewService;
    private GuestService guestService;
    private OwnerService ownerService;

    @Autowired
    public OwnerReviewController(OwnerReviewService ownerReviewService, GuestService guestService, OwnerService ownerService) {
        this.ownerReviewService = ownerReviewService;
        this.guestService = guestService;
        this.ownerService = ownerService;
    }

    @GetMapping(value = "/{ownerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<OwnerReviewDTO>> getOwnersReviews(@PathVariable Long ownerId) {
        Collection<OwnerReview> ownerReviews = ownerReviewService.findOwnerReviews(ownerId);
        Collection<OwnerReviewDTO> ownerReviewDTOS = new ArrayList<>(Collections.emptyList());
        for (OwnerReview ownerReview : ownerReviews) {
            OwnerReviewDTO ownerReviewDTO = new OwnerReviewDTO(ownerReview);
            ownerReviewDTOS.add(ownerReviewDTO);
        }
        if (ownerReviewDTOS.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ownerReviewDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/{ownerId}/grade", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Float> getAverageGrade(@PathVariable Long ownerId) {
        Collection<OwnerReview> ownerReviews = ownerReviewService.findOwnerReviews(ownerId);
        if (ownerReviews.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Float averageGrade = ownerReviewService.calculateAverageGrade(ownerReviews);
        return new ResponseEntity<>(averageGrade, HttpStatus.OK);
    }

    @GetMapping(value = "/unapproved", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Collection<OwnerReviewDTO>> getUnapprovedReviews() {
        Collection<OwnerReview> ownerReviews = ownerReviewService.findUnapprovedReviews();
        Collection<OwnerReviewDTO> ownerReviewDTOS = new ArrayList<>(Collections.emptyList());
        for (OwnerReview ownerReview : ownerReviews) {
            OwnerReviewDTO ownerReviewDTO = new OwnerReviewDTO(ownerReview);
            ownerReviewDTOS.add(ownerReviewDTO);
        }
        if (ownerReviewDTOS.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ownerReviewDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/reported", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Collection<OwnerReviewDTO>> getReportedReviews() {
        Collection<OwnerReview> ownerReviews = ownerReviewService.findReportedReviews();
        Collection<OwnerReviewDTO> ownerReviewDTOS = new ArrayList<>(Collections.emptyList());
        for (OwnerReview ownerReview : ownerReviews) {
            OwnerReviewDTO ownerReviewDTO = new OwnerReviewDTO(ownerReview);
            ownerReviewDTOS.add(ownerReviewDTO);
        }
        if (ownerReviewDTOS.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ownerReviewDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/reported/{ownerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<OwnerReviewDTO>> getOwnersReportedReviews(@PathVariable Long ownerId) {
        Collection<OwnerReviewDTO> ownerReviews = Collections.emptyList();
        return new ResponseEntity<>(ownerReviews, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Float> getAverageGradeForOwner(@RequestParam Long ownerId) {
        Owner owner = new Owner();
        if (owner.equals(null))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('Guest')")
    public ResponseEntity<OwnerReviewDTO> createOwnerReview(@RequestBody OwnerReviewDTO ownerReviewDTO) {
        OwnerReview ownerReview = new OwnerReview(ownerReviewDTO);
        Optional<Guest> optionalReviewer = guestService.findOne(ownerReviewDTO.getReviewerId());
        optionalReviewer.ifPresent(ownerReview::setReviewer);
        Optional<Owner> optionalReviewee = ownerService.findOne(ownerReviewDTO.getRevieweeId());
        optionalReviewee.ifPresent(ownerReview::setReviewee);
        ownerReviewService.addOwnerReview(ownerReview);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/unapproved/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Void> approveReview(@PathVariable Long id) {
        ownerReviewService.approveReview(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('Guest')")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        ownerReviewService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "reported/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Void> deleteReportedReview(@PathVariable Long id) {
        Optional<OwnerReview> optionalOwnerReview=ownerReviewService.findOne(id);
        if(optionalOwnerReview.isPresent()){
            if(optionalOwnerReview.get().isReported()){
                ownerReviewService.remove(id);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        throw new HttpTransferException(HttpStatus.FORBIDDEN, "Review is not reported");
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('Owner')")
    public ResponseEntity<Void> reportReview(@PathVariable Long id){
        ownerReviewService.reportReview(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
