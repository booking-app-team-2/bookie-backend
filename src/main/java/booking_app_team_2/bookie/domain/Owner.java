package booking_app_team_2.bookie.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Setter
    @Column(name = "receives_reservation_request_notifications", nullable = false)
    private boolean receivesReservationRequestNotifications = true;

    @Setter
    @Column(name = "receives_reservation_cancellation_notifications", nullable = false)
    private boolean receivesReservationCancellationNotifiactions = true;

    @Setter
    @Column(name = "receives_review_notifications", nullable = false)
    private boolean receivesReviewNotifications = true;

    @Setter
    @Column(name = "receives_accommodation_review_notifications", nullable = false)
    private boolean receivesAccommodationReviewNotifications = true;

    @JsonIgnore
    public boolean owns(Accommodation accommodation) {
        return accommodations.contains(accommodation);
    }

    public Owner(Long id, String username, String password, String name, String surname, String addressOfResidence,
                 String telephone, UserRole role, boolean isBlocked, Set<Accommodation> accommodations,
                 boolean receivesReservationRequestNotifications, boolean receivesReservationCancellationNotifiactions,
                 boolean receivesReviewNotifications, boolean receivesAccommodationReviewNotifications) {
        super(id, username, password, name, surname, addressOfResidence, telephone, role, isBlocked);
        this.accommodations = accommodations;
        this.receivesReservationRequestNotifications = receivesReservationRequestNotifications;
        this.receivesReservationCancellationNotifiactions = receivesReservationCancellationNotifiactions;
        this.receivesReviewNotifications = receivesReviewNotifications;
        this.receivesAccommodationReviewNotifications = receivesAccommodationReviewNotifications;
    }
}
