package booking_app_team_2.bookie.service;

import booking_app_team_2.bookie.domain.*;
import booking_app_team_2.bookie.repository.UserRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Setter
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    private ReservationService reservationService;
    private AccommodationService accommodationService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ReservationService reservationService,
                           AccommodationService accommodationService) {
        this.userRepository = userRepository;
        this.reservationService = reservationService;
        this.accommodationService = accommodationService;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<User> findOne(Long id) {
        return Optional.empty();
    }

    @Override
    public User save(User user) {
        return null;
    }

    private boolean hasReservations(Guest guest) {
        return !reservationService.findAllByReservee(guest).isEmpty();
    }

    private boolean accommodationsHaveReservations(Owner owner) {
        List<Accommodation> ownerAccommodations = accommodationService.findAllByOwner(owner);
        return ownerAccommodations
                .stream()
                .anyMatch(accommodation -> !reservationService.findAllByAccommodation(accommodation).isEmpty());
    }

    @Override
    public void remove(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty())
            return;

        if (user.get().getRole().equals(UserRole.Guest) && hasReservations((Guest) user.get())) {
            return;
        }
        else if (user.get().getRole().equals(UserRole.Owner) && accommodationsHaveReservations((Owner) user.get()))
            return;

        userRepository.deleteById(id);
    }
}
