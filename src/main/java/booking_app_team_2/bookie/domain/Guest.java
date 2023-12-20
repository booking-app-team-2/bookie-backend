package booking_app_team_2.bookie.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Guest extends User {
    @Column(name = "receives_reservation_request_notifications", nullable = false)
    private boolean receivesReservationRequestNotifications = true;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "guest_favourite_accommodations",
            joinColumns = @JoinColumn(name = "guest_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "accommodation_id", referencedColumnName = "id", nullable = false)
    )
    private Set<Accommodation> favouriteAccommodations;
}
