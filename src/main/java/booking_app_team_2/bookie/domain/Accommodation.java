package booking_app_team_2.bookie.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.EnumSet;
import java.util.HashSet;

@NoArgsConstructor
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
    private EnumSet<Amenities> amenities;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "accommodation_id", referencedColumnName = "id", nullable = false)
    private HashSet<Image> images;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = false)
    private Owner owner;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "accommodation_id", referencedColumnName = "id", nullable = false)
    private HashSet<AvailabilityPeriod> availabilityPeriods;
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
}
