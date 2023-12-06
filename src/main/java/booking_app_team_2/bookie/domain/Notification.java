package booking_app_team_2.bookie.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class Notification {
    @Id
    private Long id = null;
    private String body;
    private NotificationType type;
    @ManyToOne
    private User receiver;
}
