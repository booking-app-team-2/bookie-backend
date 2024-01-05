package booking_app_team_2.bookie.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@SQLDelete(sql
        = "UPDATE account_verificator "
        + "SET is_deleted = true "
        + "WHERE id = ?")
@Where(clause = "is_deleted = false")
@Entity
@Table(name = "account_verificator")
public class AccountVerificator {
    @Id
    @SequenceGenerator(name = "account_verificator_seq", sequenceName = "sequence_account_verificator", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_verificator_seq")
    @Column(unique = true, nullable = false)
    private Long id = null;

    @Column(name = "timestamp_of_registration", nullable = false)
    private LocalDateTime timestampOfRegistration;

    @Column(name = "is_verified", nullable = false)
    private boolean isVerified = false;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true, nullable = false)
    private User user;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
}
