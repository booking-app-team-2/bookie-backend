package booking_app_team_2.bookie.dto;

import booking_app_team_2.bookie.domain.OwnerReview;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class OwnerReviewDTO extends ReviewDTO {
    private Long revieweeId;

    @JsonIgnore
    public OwnerReviewDTO(OwnerReview ownerReview) {
        super(ownerReview);
        this.revieweeId = ownerReview.getReviewee().getId();
    }
}
