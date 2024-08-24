package booking_app_team_2.bookie.service;

import booking_app_team_2.bookie.domain.Accommodation;
import booking_app_team_2.bookie.domain.Guest;
import booking_app_team_2.bookie.repository.AccommodationRepository;
import booking_app_team_2.bookie.repository.GuestRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Setter
@Service
public class GuestServiceImpl implements GuestService {

    private GuestRepository guestRepository;
    private AccommodationRepository accommodationRepository;

    @Autowired
    public GuestServiceImpl(GuestRepository guestRepository, AccommodationRepository accommodationRepository) {
        this.guestRepository = guestRepository;
        this.accommodationRepository = accommodationRepository;
    }

    @Override
    public List<Guest> findAll() {
        return guestRepository.findAll();
    }

    @Override
    public Page<Guest> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<Guest> findOne(Long id) {
        return guestRepository.findById(id);
    }

    @Override
    public Guest save(Guest guest) {
        return guestRepository.save(guest);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public Boolean addFavouriteAccommodation(Long guestId, Long accommodationId) {
        Optional<Guest> guestOptional = guestRepository.findById(guestId);
        if (guestOptional.isPresent()) {
            Guest guest = guestOptional.get();
            Optional<Accommodation> optionalAccommodation = accommodationRepository.findById(accommodationId);
            if (optionalAccommodation.isPresent()) {
                Accommodation accommodation = optionalAccommodation.get();
                if (!guest.getFavouriteAccommodations().contains(accommodation)) {
                    guest.getFavouriteAccommodations().add(accommodation);
                    guestRepository.save(guest);
                    return true;
                }
            }
        }
        return false;
    }
}
