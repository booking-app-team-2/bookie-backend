package booking_app_team_2.bookie.dto;

import booking_app_team_2.bookie.domain.NotificationType;
import booking_app_team_2.bookie.domain.User;

public class NotificationDTO {
    private Long id;
    private String body;
    private NotificationType type;
    private Long receiverId;
}
