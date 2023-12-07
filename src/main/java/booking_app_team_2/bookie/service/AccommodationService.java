package booking_app_team_2.bookie.service;

import booking_app_team_2.bookie.domain.Accommodation;
import booking_app_team_2.bookie.repository.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccommodationService extends GenericService<Accommodation> {
    @Autowired
    public AccommodationService(GenericRepository<Accommodation> accommodationRepository) {
        super(accommodationRepository);
    }

    // Service example
    // TODO: Implement accommodation-specific service methods
}
