package booking_app_team_2.bookie.controller;

import booking_app_team_2.bookie.domain.User;
import booking_app_team_2.bookie.dto.UserAuthenticationDTO;
import booking_app_team_2.bookie.dto.UserRegistrationDTO;
import booking_app_team_2.bookie.dto.UserTokenStateDTO;
import booking_app_team_2.bookie.service.UserService;
import booking_app_team_2.bookie.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/authentication", produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {
    private final TokenUtils tokenUtils;

    private final AuthenticationManager authenticationManager;

    private UserService userService;

    @Autowired
    public AuthenticationController(TokenUtils tokenUtils, AuthenticationManager authenticationManager,
                                    UserService userService) {
        this.tokenUtils = tokenUtils;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserTokenStateDTO> logIn(@RequestBody UserAuthenticationDTO userAuthenticationDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userAuthenticationDTO.getUsername(),
                        userAuthenticationDTO.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();
        String jWT = tokenUtils.generateToken(user);
        int validityPeriod = tokenUtils.getValidityPeriod();

        return new ResponseEntity<>(new UserTokenStateDTO(jWT, (long) validityPeriod), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegistrationDTO> register(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        Optional<User> userOptional = userService.findOneByUsername(userRegistrationDTO.getUsername());
        if (userOptional.isPresent())
            throw new RuntimeException("Email already exists.");

        userService.save(userRegistrationDTO);

        return new ResponseEntity<>(userRegistrationDTO, HttpStatus.CREATED);
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
