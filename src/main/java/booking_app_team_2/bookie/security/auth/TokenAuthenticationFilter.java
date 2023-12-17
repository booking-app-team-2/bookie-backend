package booking_app_team_2.bookie.security.auth;

import booking_app_team_2.bookie.util.TokenUtils;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final TokenUtils tokenUtils;

    private final UserDetailsService userDetailsService;

    protected final Log log = LogFactory.getLog(getClass());

    public TokenAuthenticationFilter(TokenUtils tokenUtils, UserDetailsService userDetailsService) {
        this.tokenUtils = tokenUtils;
        this.userDetailsService = userDetailsService;
    }

    private void validateTokenAndSetAuthentication(HttpServletRequest request) {
        String authorizationToken = tokenUtils.getToken(request);
        if (authorizationToken == null || authorizationToken.isEmpty())
            return;

        String username = tokenUtils.getUsernameFromToken(authorizationToken);
        if (username == null)
            return;

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (!tokenUtils.validateToken(authorizationToken, userDetails))
            return;

        TokenBasedAuthentication tokenBasedAuthentication = new TokenBasedAuthentication(userDetails);
        tokenBasedAuthentication.setToken(authorizationToken);
        SecurityContextHolder.getContext().setAuthentication(tokenBasedAuthentication);
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            validateTokenAndSetAuthentication(request);
        } catch (ExpiredJwtException ex) {
            log.debug("Token expired!");
        }

        filterChain.doFilter(request, response);
    }
}
