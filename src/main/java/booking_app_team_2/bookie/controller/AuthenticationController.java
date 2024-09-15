package booking_app_team_2.bookie.controller;

import booking_app_team_2.bookie.domain.User;
import booking_app_team_2.bookie.domain.VerificationToken;
import booking_app_team_2.bookie.dto.UserAuthenticationDTO;
import booking_app_team_2.bookie.dto.UserRegistrationDTO;
import booking_app_team_2.bookie.dto.UserTokenStateDTO;
import booking_app_team_2.bookie.service.EmailService;
import booking_app_team_2.bookie.service.UserService;
import booking_app_team_2.bookie.service.VerificationTokenService;
import booking_app_team_2.bookie.util.TokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {
    private final TokenUtils tokenUtils;

    private final AuthenticationManager authenticationManager;

    private UserService userService;

    private VerificationTokenService verificationTokenService;

    private EmailService emailService;


    @Autowired
    public AuthenticationController(TokenUtils tokenUtils, AuthenticationManager authenticationManager,
                                    UserService userService, VerificationTokenService verificationTokenService, EmailService emailService) {
        this.tokenUtils = tokenUtils;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.verificationTokenService = verificationTokenService;
        this.emailService = emailService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserTokenStateDTO> logIn(@RequestBody UserAuthenticationDTO userAuthenticationDTO,
                                                   HttpServletRequest httpServletRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userAuthenticationDTO.getUsername(),
                        userAuthenticationDTO.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();
        if (user.isBlocked())
            throw new RuntimeException("User is blocked");


        tokenUtils.setUserAgent(httpServletRequest.getHeader("User-Agent"));
        String jWT = tokenUtils.generateToken(user);
        int validityPeriod = tokenUtils.getValidityPeriod();

        return new ResponseEntity<>(new UserTokenStateDTO(jWT, (long) validityPeriod), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegistrationDTO> register(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        Optional<User> userOptional = userService.findOneByUsername(userRegistrationDTO.getUsername());
        if (userOptional.isPresent())
            throw new RuntimeException("Email already exists.");

        User user = userService.save(userRegistrationDTO);
        user.setBlocked(true);
        userService.save(user);

        VerificationToken verificationToken = verificationTokenService.createToken(user);  // Create verification token

        // Send verification email
        String verifyUrl = "http://localhost:8081/authentication/verify/" + verificationToken.getToken();
        emailService.sendVerificationEmail(userRegistrationDTO.getUsername(), verifyUrl);

        return new ResponseEntity<>(userRegistrationDTO, HttpStatus.CREATED);
    }

    @GetMapping("/verify/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable("token") String token) {
        Optional<VerificationToken> verificationTokenOptional = verificationTokenService.getToken(token);

        if (!verificationTokenOptional.isPresent()) {
            return new ResponseEntity<>("Invalid verification token", HttpStatus.BAD_REQUEST);
        }

        VerificationToken verificationToken = verificationTokenOptional.get();

        // Check if token has expired
        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return new ResponseEntity<>("Token has expired", HttpStatus.BAD_REQUEST);
        }

        // Activate the user
        User user = verificationToken.getUser();
        user.setBlocked(false);  // Assuming User has an `enabled` field
        userService.save(user);  // Save the activated user

        return new ResponseEntity<>("Account verified successfully!", HttpStatus.OK);
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
