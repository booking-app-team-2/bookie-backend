package booking_app_team_2.bookie.controller;

import booking_app_team_2.bookie.domain.*;
import booking_app_team_2.bookie.dto.AccommodationApprovalDTO;
import booking_app_team_2.bookie.dto.AccommodationAutoAcceptDTO;
import booking_app_team_2.bookie.dto.AccommodationBasicInfoDTO;
import booking_app_team_2.bookie.dto.AccommodationDTO;
import booking_app_team_2.bookie.dto.OwnerDTO;
import booking_app_team_2.bookie.service.AccommodationService;
import booking_app_team_2.bookie.service.ImageService;
import booking_app_team_2.bookie.service.OwnerService;
import lombok.Setter;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Setter
@RestController
@RequestMapping("/api/v1/accommodations")
@CrossOrigin
public class AccommodationController {
    private AccommodationService accommodationService;
    private OwnerService ownerService;
    private ImageService imageService;

    @Autowired
    public AccommodationController(AccommodationService accommodationService,OwnerService ownerService,ImageService imageService) {
        this.accommodationService = accommodationService;
        this.ownerService=ownerService;
        this.imageService=imageService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Accommodation>> getAccommodations() {
        Collection<Accommodation> accommodations = accommodationService.findAll();
        return new ResponseEntity<>(accommodations,HttpStatus.OK);
    }
    @GetMapping(value = "/cards", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationDTO>> getAccommodationsForCards() {
        Collection<AccommodationDTO> accommodations = accommodationService.getAll();
        return new ResponseEntity<>(accommodations,HttpStatus.OK);
    }

    @GetMapping(value = "/unapproved", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationDTO>> getUnapprovedAccommodations() {
        Collection<AccommodationDTO> accommodations = accommodationService
                .findAllByIsApproved(false)
                .stream()
                .map(accommodation -> new AccommodationDTO(
                        accommodation.getId(),
                        accommodation.getName(),
                        accommodation.getDescription(),
                        accommodation.getMaximumGuests(),
                        accommodation.getMinimumGuests(),
                        accommodation.getLocation(),
                        accommodation.getAmenities(),
                        accommodation.getAvailabilityPeriods(),
                        accommodation.getImages(),
                        accommodation.getReservationCancellationDeadline(),
                        accommodation.getType(),
                        accommodation.isReservationAutoAccepted()
                ))
                .toList();

        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationDTO> getAccommodation(@PathVariable Long id) {
        Optional<Accommodation> accommodation = accommodationService.findOne(id);
        if (accommodation.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        AccommodationDTO accommodationDTO=accommodation.map(accommodation1 ->
                        new AccommodationDTO(
                                accommodation1.getId(),
                                accommodation1.getName(),
                                accommodation1.getDescription(),
                                accommodation1.getMinimumGuests(),
                                accommodation1.getMaximumGuests(),
                                accommodation1.getLocation(),
                                accommodation1.getAmenities(),
                                accommodation1.getAvailabilityPeriods(),
                                accommodation1.getImages(),
                                accommodation1.getReservationCancellationDeadline(),
                                accommodation1.getType(),
                                accommodation1.isReservationAutoAccepted()
                        )).orElse(null);
        return new ResponseEntity<>(accommodationDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationDTO>> getFilteredAccommodation(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "amenities", required = false) String amenities,
            @RequestParam(value = "minimumGuests", required = false) Integer minGuests,
            @RequestParam(value = "maximumGuests", required = false) Integer maxGuests,
            @RequestParam(value = "minimumPrice", required = false) Double minPrice,
            @RequestParam(value = "maximumPrice", required = false) Double maxPrice,
            @RequestParam(value = "type", required = false) AccommodationType type,
            @RequestParam(value = "isPricedPerGuest", required = false) Boolean isPricedPerGuest,
            @RequestParam(value = "availability", required = false) String availability,
            @RequestParam(value = "averageRating", required = false) Double avgRating)
    {
        Collection<AccommodationDTO> accommodations = Collections.emptyList();
        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }
    @GetMapping(value ="/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationDTO>> getSearchedAccommodations(
            @RequestParam(value = "location",required = false) String location,
            @RequestParam(value = "numberOfGuests",required = false) Integer numberOfGuests,
            @RequestParam(value = "startDate",required = false) String startDate,
            @RequestParam(value = "endDate",required = false) String endDate
            ){
        Collection<Accommodation> accommodations;
        if(location==null && numberOfGuests==null && startDate==null && endDate==null){
            accommodations=accommodationService.findAll();
        }
        else{
            accommodations = accommodationService.findSearched(location,numberOfGuests != null ? numberOfGuests.intValue() : 0,startDate,endDate);
        }
        Collection<AccommodationDTO> accommodationDTO=accommodations.stream()
                .map(accommodation -> new AccommodationDTO(accommodation.getId(),accommodation.getName(),accommodation.getDescription(),accommodation.getMinimumGuests(),accommodation.getMaximumGuests(),accommodation.getLocation(),accommodation.getAmenities(),accommodation.getAvailabilityPeriods(),accommodation.getImages(),accommodation.getReservationCancellationDeadline(),accommodation.getType(),accommodation.isReservationAutoAccepted()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(accommodationDTO, HttpStatus.OK);
    }
    @GetMapping(value ="/owner-accommodations/{owner_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationDTO>> getAccommodationsByOwner(@PathVariable Long owner_id) {
        Optional<Owner> owner=ownerService.findOne(owner_id);
        if(owner.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Collection<Accommodation> accommodations = owner.get().getAccommodations();
        Collection<AccommodationDTO> accommodationDTO=accommodations.stream()
                .map(accommodation -> new AccommodationDTO(accommodation.getId(),accommodation.getName(),accommodation.getDescription(),accommodation.getMinimumGuests(),accommodation.getMaximumGuests(),accommodation.getLocation(),accommodation.getAmenities(),accommodation.getAvailabilityPeriods(),accommodation.getImages(),accommodation.getReservationCancellationDeadline(),accommodation.getType(),accommodation.isReservationAutoAccepted()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(accommodationDTO, HttpStatus.OK);
    }

    @PutMapping(value="/{accommodationId}/basic-info",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationBasicInfoDTO> updateAccommodationBasicInfo(@PathVariable Long accommodationId,
                                                                                  @RequestBody AccommodationBasicInfoDTO accommodationBasicInfoDTO){

       Optional<Accommodation> accommodationOptional=accommodationService.findOne(accommodationId);
        if(accommodationOptional.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        accommodationBasicInfoDTO=accommodationService.updateAccommodationBasicInfo(accommodationOptional.get(),accommodationBasicInfoDTO);
        if(accommodationBasicInfoDTO==null){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(accommodationBasicInfoDTO,HttpStatus.OK);
    }

    @GetMapping(value = "/{accommodationId}/amenities")
    public ResponseEntity<Collection<Amenities>> getAmenities(@PathVariable Long accommodationId) {
        return new ResponseEntity<>(new HashSet<>(), HttpStatus.OK);
    }

    @GetMapping(value = "/{accommodationId}/availability-periods")
    public ResponseEntity<Collection<AvailabilityPeriod>> getAvailabilityPeriods(@PathVariable Long accommodationId) {
        return new ResponseEntity<>(new HashSet<>(), HttpStatus.OK);
    }

    @GetMapping(value = "/{accommodationId}/owner")
    public ResponseEntity<OwnerDTO> getOwner(@PathVariable Long accommodationId) {
        return new ResponseEntity<>(new OwnerDTO(), HttpStatus.OK);
    }

    @GetMapping(value = "/{accommodationId}/average-rating")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long accommodationId) {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping(value = "/{accommodationId}/reservation-auto-accept")
    public ResponseEntity<Boolean> isReservationAutoAccepted(@PathVariable Long accommodationId) {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Accommodation> createAccommodation(@RequestBody Accommodation accommodation) {
        Accommodation savedAccommodation = new Accommodation() {};
        return new ResponseEntity<>(savedAccommodation, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}/is-approved", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<AccommodationApprovalDTO> updateAccommodationIsApproved(
            @PathVariable Long id,
            @RequestBody AccommodationApprovalDTO accommodationApprovalDTO
    ) {
        accommodationService.updateIsApproved(id, accommodationApprovalDTO);

        return new ResponseEntity<>(accommodationApprovalDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/reservation-auto-accept", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationAutoAcceptDTO> updateAccommodation(@RequestBody AccommodationAutoAcceptDTO accommodationDTO,
                                                                              @PathVariable Long id) {
        AccommodationAutoAcceptDTO accommodationDTO1 = new AccommodationAutoAcceptDTO();
        if (accommodationDTO1.equals(null))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(accommodationDTO1, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteAccommodation(@PathVariable("id") Long id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
