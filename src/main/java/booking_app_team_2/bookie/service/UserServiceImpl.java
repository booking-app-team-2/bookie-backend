package booking_app_team_2.bookie.service;

import booking_app_team_2.bookie.domain.User;
import booking_app_team_2.bookie.dto.UserPasswordDTO;
import booking_app_team_2.bookie.exception.GuestHasReservationsException;
import booking_app_team_2.bookie.exception.OwnerAccommodationsHaveReservationsException;
import booking_app_team_2.bookie.exception.UserNotFoundException;
import booking_app_team_2.bookie.repository.UserRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Setter
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    private ReservationService reservationService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ReservationService reservationService) {
        this.userRepository = userRepository;
        this.reservationService = reservationService;
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
        return userRepository.findById(id);
    }

    @Override
    public boolean isCorrectPassword(UserPasswordDTO userPasswordDTO, User user) {
        return userPasswordDTO.getCurrentPassword().equals(user.getPassword());
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    private boolean hasReservations(Guest guest) {
        return !reservationService.findAllByReserveeAndStatusIn(guest,
                EnumSet.of(ReservationStatus.Accepted, ReservationStatus.Waiting)).isEmpty();
    }

    private boolean accommodationsHaveReservations(Set<Accommodation> ownerAccommodations) {
        return ownerAccommodations
                .stream()
                .anyMatch(accommodation -> !reservationService.findAllByAccommodationAndStatusIn(accommodation,
                        EnumSet.of(ReservationStatus.Accepted, ReservationStatus.Waiting)).isEmpty());
    }

    @Override
    public void remove(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty())
            throw new UserNotFoundException("User has not been found.");

        User user = userOptional.get();
        UserRole userRole = user.getRole();

        if (userRole.equals(UserRole.Guest) && hasReservations((Guest) user))
            throw new GuestHasReservationsException("Profile cannot be deleted as it has accepted reservations, " +
                    "or reservations in waiting.");
        else if (userRole.equals(UserRole.Owner) && accommodationsHaveReservations(((Owner) user).getAccommodations()))
            throw new OwnerAccommodationsHaveReservationsException
                    ("Profile cannot be deleted as it owns accommodations which have been reserved in the future.");

        userRepository.deleteById(id);
    }
}
