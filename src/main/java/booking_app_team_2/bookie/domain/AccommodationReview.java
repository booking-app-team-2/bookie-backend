package booking_app_team_2.bookie.domain;

import booking_app_team_2.bookie.dto.AccommodationReviewDTO;
import booking_app_team_2.bookie.dto.ReviewDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "accommodation_review")
public class AccommodationReview extends Review {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id", referencedColumnName = "id", nullable = false)
    private Accommodation accommodation;

    public AccommodationReview(AccommodationReviewDTO accommodationReviewDTO){
        super(accommodationReviewDTO);
    }
}
