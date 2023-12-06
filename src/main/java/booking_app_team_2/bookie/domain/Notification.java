package booking_app_team_2.bookie.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class Notification {
    @Id
    @SequenceGenerator(name = "NOTIFICATION_SEQ", sequenceName = "SEQUENCE_NOTIFICATION", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NOTIFICATION_SEQ")
    private Long id = null;
    private String body;
    private NotificationType type;
    @ManyToOne
    private User receiver;
}
