package booking_app_team_2.bookie.domain;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@NoArgsConstructor
@SQLDelete(sql
        = "UPDATE Owner "
        + "SET isDeleted = true "
        + "WHERE id = ?")
@Where(clause = "isDeleted = false")
@Entity
public class Owner extends User {
    private boolean receivesReservationRequestNotifications = true;
    private boolean receivesReservationCancellationNotifiactions = true;
    private boolean receivesReviewNotifications = true;
    private boolean receivesAccommodationReviewNotifications = true;
    private boolean isDeleted = false;
}
