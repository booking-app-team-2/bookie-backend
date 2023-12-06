package booking_app_team_2.bookie.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.HashSet;

@NoArgsConstructor
@SQLDelete(sql
        = "UPDATE Guest "
        + "SET isDeleted = true "
        + "WHERE id = ?")
@Where(clause = "isDeleted = false")
@Entity
public class Guest extends User {
    private boolean receivesReservationRequestNotifications = true;
    @ManyToMany
    private HashSet<Accommodation> favouriteAccommodations;
    private boolean isDeleted = false;
}
