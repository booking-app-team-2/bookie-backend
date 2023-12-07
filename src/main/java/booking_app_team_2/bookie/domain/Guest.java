package booking_app_team_2.bookie.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.HashSet;

@NoArgsConstructor
@Getter
@Setter
@SQLDelete(sql
        = "UPDATE guest "
        + "SET is_deleted = true "
        + "WHERE id = ?")
@Where(clause = "is_deleted = false")
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
    private HashSet<Accommodation> favouriteAccommodations;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
}
