package booking_app_team_2.bookie.repository;

import booking_app_team_2.bookie.domain.Accommodation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccommodationRepository extends GenericRepository<Accommodation> {
    List<Accommodation> findAllByIsApproved(boolean isApproved);
}
