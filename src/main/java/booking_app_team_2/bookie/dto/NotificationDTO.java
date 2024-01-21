package booking_app_team_2.bookie.dto;

import booking_app_team_2.bookie.domain.Notification;
import booking_app_team_2.bookie.domain.NotificationType;
import booking_app_team_2.bookie.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NotificationDTO {
    private Long id;
    private String body;
    private NotificationType type;
    private Long receiverId;

    @JsonIgnore
    public NotificationDTO(Notification notification) {
        id = notification.getId();
        body = notification.getBody();
        type = notification.getType();
        receiverId = notification.getReceiver().getId();
    }
}
