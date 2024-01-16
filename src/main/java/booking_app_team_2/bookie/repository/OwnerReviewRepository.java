package booking_app_team_2.bookie.repository;

import booking_app_team_2.bookie.domain.OwnerReview;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OwnerReviewRepository extends GenericRepository<OwnerReview> {
    List<OwnerReview> findAllByReviewee_IdAndIsApproved(Long ownerId, boolean approved);

    List<OwnerReview> findAllByIsApproved(boolean approved);

}
