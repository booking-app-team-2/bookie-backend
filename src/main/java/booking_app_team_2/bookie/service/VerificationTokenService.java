package booking_app_team_2.bookie.service;

import booking_app_team_2.bookie.domain.User;
import booking_app_team_2.bookie.domain.VerificationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class VerificationTokenService {

    private final Map<String, VerificationToken> tokenStore = new HashMap<>();

    public VerificationToken createToken(User user) {
        String token = UUID.randomUUID().toString();  // Generate a random token
        VerificationToken verificationToken = new VerificationToken(token, user, LocalDateTime.now().plusDays(1)); // Token valid for 1 day
        tokenStore.put(token, verificationToken);
        return verificationToken;
    }

    public Optional<VerificationToken> getToken(String token) {
        return Optional.ofNullable(tokenStore.get(token));
    }

    public void deleteToken(String token) {
        tokenStore.remove(token);
    }
}
