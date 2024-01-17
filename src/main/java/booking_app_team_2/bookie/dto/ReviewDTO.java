package booking_app_team_2.bookie.dto;

import booking_app_team_2.bookie.domain.Review;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZoneId;

@NoArgsConstructor
@Getter
public class ReviewDTO {
    private Long id = null;
    private Float grade;
    private String comment;
    private Long timestampOfCreation;
    private Long reviewerId;

    @JsonIgnore
    public ReviewDTO(Review review) {
        id = review.getId();
        grade = review.getGrade();
        comment = review.getComment();
        timestampOfCreation = review.getTimestampOfCreation().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        reviewerId = review.getReviewer().getId();
    }
}
