package booking_app_team_2.bookie.controller;

import booking_app_team_2.bookie.domain.Notification;
import booking_app_team_2.bookie.domain.User;
import booking_app_team_2.bookie.dto.NotificationDTO;
import booking_app_team_2.bookie.service.NotificationService;
import booking_app_team_2.bookie.service.UserService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {
    private UserService userService;
    private NotificationService notificationService;

    @Autowired
    public NotificationController(UserService userService, NotificationService notificationService) {
        this.userService = userService;
        this.notificationService = notificationService;
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('Guest', 'Owner', 'Admin')")
    public ResponseEntity<NotificationDTO> createNotification(@RequestBody NotificationDTO notification){
        Notification newNotification = new Notification(notification);
        Optional<User> userOptional = userService.findOne(notification.getReceiverId());
        userOptional.ifPresent(newNotification::setReceiver);
        notificationService.addNotification(newNotification);
        return new ResponseEntity<>(notification,HttpStatus.CREATED);
    }

    //Request param pogledati kod darkovog
    @GetMapping(value = "/{userId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Notification>> getUserNotifications(@PathVariable Long userId){
        Optional<User> userOptional = userService.findOne(userId);
        Collection<Notification> notifications = Collections.emptyList();
        if(userOptional.isPresent()) {
            notifications = notificationService.findByReciever(userOptional.get());
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }
}
