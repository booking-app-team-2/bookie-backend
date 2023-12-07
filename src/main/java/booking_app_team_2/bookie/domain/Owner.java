package booking_app_team_2.bookie.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@NoArgsConstructor
@Getter
@Setter
@SQLDelete(sql
        = "UPDATE owner "
        + "SET is_deleted = true "
        + "WHERE id = ?")
@Where(clause = "is_deleted = false")
@Entity
public class Owner extends User {
    @Column(name = "receives_reservation_request_notifications", nullable = false)
    private boolean receivesReservationRequestNotifications = true;

    @Column(name = "receives_reservation_cancellation_notifications", nullable = false)
    private boolean receivesReservationCancellationNotifiactions = true;

    @Column(name = "receives_review_notifications", nullable = false)
    private boolean receivesReviewNotifications = true;

    @Column(name = "receives_accommodation_review_notifications", nullable = false)
    private boolean receivesAccommodationReviewNotifications = true;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
}
