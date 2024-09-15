package booking_app_team_2.bookie.controller;

import booking_app_team_2.bookie.domain.*;
import booking_app_team_2.bookie.dto.AccommodationDTO;
import booking_app_team_2.bookie.dto.ReportDTO;
import booking_app_team_2.bookie.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {

    private final UserService userService;
    private ReportService reportService;
    private OwnerService ownerService;
    private GuestService guestService;
    private ReservationService reservationService;

    @Autowired
    public ReportController(ReportService reportService, UserService userService, OwnerService ownerService,
                            GuestService guestService, ReservationService reservationService) {
        this.reportService = reportService;
        this.userService = userService;
        this.ownerService = ownerService;
        this.guestService = guestService;
        this.reservationService = reservationService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ReportDTO>>getReports(){
        Collection<Report> reports= reportService.findAll();
        if (reports.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Collection<ReportDTO> reportDTOS = reports.stream().map(ReportDTO::new).toList();
        return new ResponseEntity<>(reportDTOS, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Report> createReport(@RequestBody ReportDTO report){
        Optional<User> reporter = userService.findOneByUsername(report.getReporterName());
        Optional<User> reportee = userService.findOneByUsername(report.getReporteeName());

        String role = String.valueOf(reporter.get().getRole());

        Boolean flag = false;
        if(role.equals("Owner")){
            Optional<Owner> owner = ownerService.findOne(reporter.get().getId());
            Optional<Guest> guest = guestService.findOne(reportee.get().getId());
            Set<Accommodation> accommodations = owner.get().getAccommodations();

            List<Long> accommodationIds = accommodations.stream()
                    .map(Accommodation::getId)
                    .collect(Collectors.toList());

            List<Reservation> reservations = reservationService.findAll();
            for(Reservation reservation : reservations){
                if(accommodationIds.contains(reservation.getAccommodation().getId())){
                    if(reservation.getReservee().getId().equals(guest.get().getId())){
                        flag = true;
                        break;
                    }
                }
            }
        } else {
            Optional<Owner> owner = ownerService.findOne(reportee.get().getId());
            Optional<Guest> guest = guestService.findOne(reporter.get().getId());
            Set<Accommodation> accommodations = owner.get().getAccommodations();

            List<Long> accommodationIds = accommodations.stream()
                    .map(Accommodation::getId)
                    .collect(Collectors.toList());

            List<Reservation> reservations = reservationService.findAll();
            for(Reservation reservation : reservations){
                if(accommodationIds.contains(reservation.getAccommodation().getId())){
                    if(reservation.getReservee().getId().equals(guest.get().getId())){
                        flag = true;
                        break;
                    }
                }
            }
        }

        if(flag){
            Report newReport;
            try {
                newReport = new Report(report.getBody(), reporter.get(), reportee.get());
                reportService.save(newReport);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(newReport, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping(value = "/{Id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> blockUser(@PathVariable Long Id){
        if(reportService.findOne(Id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Boolean blocked = reportService.blockUser(Id);
        if (blocked) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
