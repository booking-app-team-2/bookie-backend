package booking_app_team_2.bookie.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@NoArgsConstructor
@SQLDelete(sql
        = "UPDATE Reservation "
        + "SET isDeleted = true "
        + "WHERE id = ?")
@Where(clause = "isDeleted = false")
@Entity
public class Reservation {
    @Id
    @SequenceGenerator(name = "RESERVATION_SEQ", sequenceName = "SEQUENCE_RESERVATION", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RESERVATION_SEQ")
    private Long id = null;
    private int numberOfGuests;
    private ReservationStatus status = ReservationStatus.Waiting;
    @ManyToOne
    private Accommodation accommodation;
    @ManyToOne
    private Guest reservee;
    @Embedded
    private Period period;
    private boolean isDeleted = false;
}
