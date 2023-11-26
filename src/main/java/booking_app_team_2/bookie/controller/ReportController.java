package booking_app_team_2.bookie.controller;

import booking_app_team_2.bookie.domain.Report;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Report>>getReports(){
        Collection<Report> reports= Collections.emptyList();
        if(reports.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Report> createReport(@RequestBody Report report){
        Report newReport=new Report();
        if(newReport==null){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/{userId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Report> blockUser(@PathVariable Long userId){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // TODO: Implement blocking users
}
