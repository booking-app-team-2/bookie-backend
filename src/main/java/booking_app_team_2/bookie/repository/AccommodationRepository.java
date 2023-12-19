package booking_app_team_2.bookie.repository;

import booking_app_team_2.bookie.domain.Accommodation;
import booking_app_team_2.bookie.domain.Owner;
import booking_app_team_2.bookie.dto.AccommodationDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccommodationRepository extends GenericRepository<Accommodation> {
    // Example repository interface
    // TODO: Declare accommodation-specific repository methods
    List<Accommodation> findAccommodationByOwner(Owner owner);
}
