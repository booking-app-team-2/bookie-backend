package booking_app_team_2.bookie.repository;

import booking_app_team_2.bookie.domain.AccommodationReview;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccommodationReviewRepository extends GenericRepository<AccommodationReview> {
    List<AccommodationReview> findAllByAccommodation_Id(Long accommodationId);

    List<AccommodationReview> findAllByAccommodation_IdAndIsApproved(Long accommodationId, boolean approved);
}
