package booking_app_team_2.bookie.dto;

import booking_app_team_2.bookie.domain.AccommodationReview;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class AccommodationReviewDTO extends ReviewDTO {
    private Long accommodationId;

    @JsonIgnore
    public AccommodationReviewDTO(AccommodationReview accommodationReview) {
        super(accommodationReview);
        this.accommodationId = accommodationReview.getAccommodation().getId();
    }
}
