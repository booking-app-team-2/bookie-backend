package booking_app_team_2.bookie.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Owner extends User {
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
            name = "owner_accommodations",
            joinColumns = @JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "accommodation_id", referencedColumnName = "id", nullable = false)
    )
    private Set<Accommodation> accommodations;

    @Column(name = "receives_reservation_request_notifications", nullable = false)
    private boolean receivesReservationRequestNotifications = true;

    @Column(name = "receives_reservation_cancellation_notifications", nullable = false)
    private boolean receivesReservationCancellationNotifiactions = true;

    @Column(name = "receives_review_notifications", nullable = false)
    private boolean receivesReviewNotifications = true;

    @Column(name = "receives_accommodation_review_notifications", nullable = false)
    private boolean receivesAccommodationReviewNotifications = true;
}
