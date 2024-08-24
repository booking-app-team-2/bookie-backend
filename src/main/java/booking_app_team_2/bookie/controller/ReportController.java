package booking_app_team_2.bookie.controller;

import booking_app_team_2.bookie.domain.Report;
import booking_app_team_2.bookie.dto.ReportDTO;
import booking_app_team_2.bookie.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {

    private ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
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
    public ResponseEntity<Report> createReport(@RequestBody Report report){
        Report newReport=new Report();
        if(newReport.equals(null)){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
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
