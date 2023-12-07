package booking_app_team_2.bookie.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@NoArgsConstructor
@SQLDelete(sql
        = "UPDATE reservation "
        + "SET is_deleted = true "
        + "WHERE id = ?")
@Where(clause = "is_deleted = false")
@Entity
public class Reservation {
    @Id
    @SequenceGenerator(name = "reservation_seq", sequenceName = "sequence_reservation", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservation_seq")
    @Column(unique = true, nullable = false)
    private Long id = null;
    @Column(name = "number_of_guests", nullable = false)
    private int numberOfGuests;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status = ReservationStatus.Waiting;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id", referencedColumnName = "id", nullable = false)
    private Accommodation accommodation;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservee_id", referencedColumnName = "id", nullable = false)
    private Guest reservee;
    @Embedded
    @Column(nullable = false)
    private Period period;
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
}
