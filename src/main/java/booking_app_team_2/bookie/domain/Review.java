package booking_app_team_2.bookie.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@NoArgsConstructor
@SQLDelete(sql
        = "UPDATE review "
        + "SET is_deleted = true "
        + "WHERE id = ?")
@Where(clause = "is_deleted = false")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Review {
    @Id
    @SequenceGenerator(name = "review_seq", sequenceName = "sequence_review", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_seq")
    @Column(unique = true, nullable = false)
    private Long id = null;
    @Column(nullable = false)
    private float grade;
    private String comment;
    @Column(name = "date_of_creation", nullable = false)
    private long dateOfCreation;
    @Column(name = "is_approved", nullable = false)
    private boolean isApproved = false;
    @Column(name = "is_reported", nullable = false)
    private boolean isReported = false;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id", referencedColumnName = "id", nullable = false)
    private Guest reviewer;
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;
}
