package booking_app_team_2.bookie.service;

import booking_app_team_2.bookie.domain.OwnerReview;

import java.util.Collection;
import java.util.List;

public interface OwnerReviewService extends GenericService<OwnerReview> {
    List<OwnerReview> findOwnerReviews(Long ownerId);

    Float calculateAverageGrade(Collection<OwnerReview> ownerReviews);

    void addOwnerReview(OwnerReview ownerReview);

    List<OwnerReview> findUnapprovedReviews();

    void approveReview(Long reviewId);
    void reportReview(Long reviewId);
}
