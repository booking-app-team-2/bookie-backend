package booking_app_team_2.bookie.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;


@NoArgsConstructor
@Getter
@Setter
@SQLDelete(sql
        = "UPDATE availability_period "
        + "SET is_deleted = true "
        + "WHERE id = ?")
@Where(clause = "is_deleted = false")
@Entity
@Table(name = "availability_period")
public class AvailabilityPeriod {
    @Id
    @SequenceGenerator(name = "availability_period_seq", sequenceName = "sequence_availability_period", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "availability_period_seq")
    @Column(unique = true, nullable = false)
    private Long id = null;

    @Column(nullable = false)
    private BigDecimal price;

    @Embedded
    @Column(nullable = false)
    private Period period;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
}
