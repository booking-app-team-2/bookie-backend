package booking_app_team_2.bookie.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@NoArgsConstructor
@Getter
@Setter
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

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    public Reservation(int numberOfGuests, Accommodation accommodation, Guest reservee, Period period,
                       BigDecimal price) {
        this.numberOfGuests = numberOfGuests;
        this.accommodation = accommodation;
        this.reservee = reservee;
        this.period = period;
        this.price = price;
    }

    public boolean hasBegun() {
        return period.hasBegun();
    }

    public boolean isCancellable() {
        return status == ReservationStatus.Accepted &&
                LocalDate
                        .now()
                        .isBefore(period.getStartDate().minusDays(accommodation.getReservationCancellationDeadline()));
    }

    public BigDecimal getPricePerDayPerGuest() {
        int priceDivisor = accommodation.isPricedPerGuest() ? numberOfGuests : 1;
        return price
                .divide(BigDecimal.valueOf(period.getInDays()), 2, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(priceDivisor), 2, RoundingMode.HALF_UP);
    }

    public boolean isRecent(){
        return period.getEndDate().isBefore(LocalDate.now()) && ChronoUnit.DAYS.between(period.getEndDate(), LocalDate.now()) <= 7;
    }
}
