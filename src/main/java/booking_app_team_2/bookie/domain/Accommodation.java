package booking_app_team_2.bookie.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.EnumSet;
import java.util.HashSet;

@NoArgsConstructor
@SQLDelete(sql
        = "UPDATE Accommodation "
        + "SET isDeleted = true "
        + "WHERE id = ?")
@Where(clause = "isDeleted = false")
@Entity
public class Accommodation {
    @Id
    private Long id = null;
    private String name;
    private String description;
    @Embedded
    private Location location;
    private EnumSet<Amenities> amenities;
    @OneToMany
    private HashSet<Image> images;
    private int minimumGuests;
    private int maximumGuests;
    private int reservationCancellationDeadline;
    private AccommodationType type;
    private boolean isPricedPerGuest;
    private boolean isApproved = false;
    private boolean isReservationAutoAccepted = false;
    private double averageRating;
    @ManyToOne
    private Owner owner;
    @OneToMany
    private HashSet<AvailabilityPeriod> availabilityPeriods;
    private boolean isDeleted = false;
}
