package booking_app_team_2.bookie.controller;

import booking_app_team_2.bookie.domain.Notification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification){
        Notification newNotification=new Notification();
        return new ResponseEntity<>(newNotification, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{userId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Notification>> getUserNotifications(@PathVariable Long userId){
        Collection<Notification> notifications= Collections.emptyList();
        if(notifications.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }
}
