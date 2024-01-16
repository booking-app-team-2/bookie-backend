package booking_app_team_2.bookie.service;

import booking_app_team_2.bookie.domain.AccommodationReview;

import java.util.Collection;
import java.util.List;

public interface AccommodationReviewService extends GenericService<AccommodationReview> {
    List<AccommodationReview> findAccommodationReviews(Long accommodationId);

    void addAccommodationReview(AccommodationReview accommodationReview);

    Float calculateAverageGrade(Collection<AccommodationReview> accommodationReviews);

    void approveReview(Long reviewId);
}
