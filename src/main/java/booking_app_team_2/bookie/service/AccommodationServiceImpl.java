package booking_app_team_2.bookie.service;

import booking_app_team_2.bookie.domain.Accommodation;
import booking_app_team_2.bookie.domain.Owner;
import booking_app_team_2.bookie.dto.AccommodationDTO;
import booking_app_team_2.bookie.repository.AccommodationRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Setter
@Service
public class AccommodationServiceImpl implements AccommodationService {
    private AccommodationRepository accommodationRepository;

    @Autowired
    public AccommodationServiceImpl(AccommodationRepository accommodationRepository) {
        this.accommodationRepository = accommodationRepository;
    }

    @Override
    public List<AccommodationDTO> getAll(){
        List<Accommodation> accommodations = accommodationRepository.findAll();
        return accommodations.stream()
                .map(accommodation -> new AccommodationDTO(accommodation.getId(),accommodation.getName(),accommodation.getDescription(),accommodation.getMinimumGuests(),accommodation.getMaximumGuests(),accommodation.getLocation(),accommodation.getReservationCancellationDeadline(),accommodation.getType()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Accommodation> findAll() {
        return accommodationRepository.findAll();
    }

    @Override
    public Page<Accommodation> findAll(Pageable pageable) {
        return accommodationRepository.findAll(pageable);
    }

    @Override
    public Optional<Accommodation> findOne(Long id) {
        return accommodationRepository.findById(id);
    }

    @Override
    public Accommodation save(Accommodation accommodation) {
        return accommodationRepository.save(accommodation);
    }

    @Override
    public void remove(Long id) {
        accommodationRepository.deleteById(id);
    }

    // Service example
    // TODO: Implement accommodation-specific service methods
}
