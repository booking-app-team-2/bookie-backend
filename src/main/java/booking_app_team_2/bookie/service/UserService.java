package booking_app_team_2.bookie.service;

import booking_app_team_2.bookie.domain.User;
import booking_app_team_2.bookie.dto.UserPasswordDTO;
import booking_app_team_2.bookie.dto.UserRegistrationDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends GenericService<User>, UserDetailsService {
    Optional<User> findOneByUsername(String username);

    boolean isCorrectPassword(UserPasswordDTO userPasswordDTO, User user);

    User save(UserRegistrationDTO userRegistrationDTO);
}
