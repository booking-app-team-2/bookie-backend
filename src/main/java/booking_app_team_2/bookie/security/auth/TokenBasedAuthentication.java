package booking_app_team_2.bookie.security.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class TokenBasedAuthentication extends AbstractAuthenticationToken {
    @Getter
    @Setter
    private String token;

    private final UserDetails principal;

    public TokenBasedAuthentication(UserDetails principal) {
        super(principal.getAuthorities());
        this.principal = principal;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public UserDetails getPrincipal() {
        return principal;
    }
}
