package booking_app_team_2.bookie.domain;

import booking_app_team_2.bookie.dto.OwnerReviewDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "owner_review")
public class OwnerReview extends Review {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewee_id", referencedColumnName = "id", nullable = false)
    private Owner reviewee;

    public OwnerReview(OwnerReviewDTO ownerReviewDTO) {
        super(ownerReviewDTO);
    }
}
