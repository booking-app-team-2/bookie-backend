package booking_app_team_2.bookie.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@NoArgsConstructor
@Getter
@Setter
@SQLDelete(sql
        = "UPDATE owner_review "
        + "SET is_deleted = true "
        + "WHERE id = ?")
@Where(clause = "is_deleted = false")
@Entity
@Table(name = "owner_review")
public class OwnerReview extends Review {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewee_id", referencedColumnName = "id", nullable = false)
    private Owner reviewee;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
}
