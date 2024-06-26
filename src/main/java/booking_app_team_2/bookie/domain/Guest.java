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

    public Guest(String username, String password, String name, String surname, String addressOfResidence,
                 String telephone, UserRole role, boolean receivesReservationRequestNotifications,
                 Set<Accommodation> favouriteAccommodations) {
        super(username, password, name, surname, addressOfResidence, telephone, role);

        this.receivesReservationRequestNotifications = receivesReservationRequestNotifications;
        this.favouriteAccommodations = favouriteAccommodations;
    }
}
