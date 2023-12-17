package booking_app_team_2.bookie.service;

import booking_app_team_2.bookie.domain.User;
import booking_app_team_2.bookie.dto.UserPasswordDTO;
import booking_app_team_2.bookie.dto.UserRegistrationDTO;
import booking_app_team_2.bookie.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Setter
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
    public Optional<User> findOneByUsername(String username) {
        return userRepository.findOneByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findOneByUsername(username);
        if (userOptional.isEmpty())
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));

        return userOptional.get();
    }

    @Override
    public boolean isCorrectPassword(UserPasswordDTO userPasswordDTO, User user) {
        return userPasswordDTO.getCurrentPassword().equals(user.getPassword());
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
    @Override
    public User save(UserRegistrationDTO userRegistrationDTO) {
    return userRepository.save(new User(userRegistrationDTO.getUsername(),
        passwordEncoder.encode(userRegistrationDTO.getPassword()), userRegistrationDTO.getName(),
        userRegistrationDTO.getSurname(), userRegistrationDTO.getAddressOfResidence(),
        userRegistrationDTO.getTelephone(), userRegistrationDTO.getRole()));
    }

    @Override
    public void remove(Long id) {

    }
}
