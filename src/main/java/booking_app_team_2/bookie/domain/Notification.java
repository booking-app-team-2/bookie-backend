package booking_app_team_2.bookie.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@NoArgsConstructor
@SQLDelete(sql
        = "UPDATE notification "
        + "SET is_deleted = true "
        + "WHERE id = ?")
@Where(clause = "is_deleted = false")
@Entity
public class Notification {
    @Id
    @SequenceGenerator(name = "notification_seq", sequenceName = "sequence_notification", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_seq")
    @Column(unique = true, nullable = false)
    private Long id = null;
    @Column(nullable = false)
    private String body;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", referencedColumnName = "id", nullable = false)
    private User receiver;
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;
}
