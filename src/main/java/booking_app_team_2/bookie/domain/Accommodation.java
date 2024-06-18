package booking_app_team_2.bookie.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@SQLDelete(sql
        = "UPDATE accommodation "
        + "SET is_deleted = true "
        + "WHERE id = ?")
@Where(clause = "is_deleted = false")
@Entity
public class Accommodation {
    @Id
    @SequenceGenerator(name = "accommodation_seq", sequenceName = "sequence_accommodation", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accommodation_seq")
    @Column(unique = true, nullable = false)
    private Long id = null;

    @Column(nullable = false)
    private String name;

    private String description;

    @Embedded
    @Column(nullable = false)
    private Location location;

    @ElementCollection(targetClass = Amenities.class)
    @CollectionTable(
            name = "accommodation_amenities",
            joinColumns = @JoinColumn(name = "accommodation_id", referencedColumnName = "id", nullable = false)
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "amenity", nullable = false)
    private Set<Amenities> amenities;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
            name = "accommodation_images",
            joinColumns = @JoinColumn(name = "accommodation_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "image_id", referencedColumnName = "id", nullable = false)
    )
    private Set<Image> images;

    @Column(name = "minimum_guests", nullable = false)
    private int minimumGuests;

    @Column(name = "maximum_guests", nullable = false)
    private int maximumGuests;

    @Column(name = "reservation_cancellation_deadline", nullable = false)
    private int reservationCancellationDeadline;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccommodationType type;

    @Column(name = "is_priced_per_guest", nullable = false)
    private boolean isPricedPerGuest;

    @Column(name = "is_approved", nullable = false)
    private boolean isApproved = false;

    @Column(name = "is_reservation_auto_accepted", nullable = false)
    private boolean isReservationAutoAccepted = false;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
            name = "accommodation_availability_periods",
            joinColumns = @JoinColumn(name = "accommodation_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(
                    name = "availability_period_id",
                    referencedColumnName = "id",
                    nullable = false
            )
    )
    private Set<AvailabilityPeriod> availabilityPeriods;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    public Accommodation(String name, String description, Location location, Set<Amenities> amenities,
                         Set<Image> images, int minimumGuests, int maximumGuests, int reservationCancellationDeadline,
                         AccommodationType type, boolean isPricedPerGuest, boolean isReservationAutoAccepted,
                         Set<AvailabilityPeriod> availabilityPeriods) {
        this.name = name;
        this.location = location;
        this.description = description;
        this.amenities = amenities;
        this.images = images;
        this.minimumGuests = minimumGuests;
        this.maximumGuests = maximumGuests;
        this.reservationCancellationDeadline = reservationCancellationDeadline;
        this.type = type;
        this.isPricedPerGuest = isPricedPerGuest;
        this.isReservationAutoAccepted = isReservationAutoAccepted;
        this.availabilityPeriods = availabilityPeriods;
    }

    public boolean addAvailabilityPeriod(AvailabilityPeriod availabilityPeriod) {
        return availabilityPeriods.add(availabilityPeriod);
    }

    public boolean removeAvailabilityPeriod(AvailabilityPeriod availabilityPeriod) {
        return availabilityPeriods.remove(availabilityPeriod);
    }

    public boolean canAccommodate(int numberOfGuests) {
        return minimumGuests <= numberOfGuests && maximumGuests >= numberOfGuests;
    }

}
