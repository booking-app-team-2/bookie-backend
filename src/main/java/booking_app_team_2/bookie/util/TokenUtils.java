package booking_app_team_2.bookie.util;

import booking_app_team_2.bookie.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenUtils {
    @Value("bookie-backend")
    private String appName;

    @Value("eikoobisevil")
    public String secret;

    @Getter
    @Value("86400000")
    private int validityPeriodMobile;

    @Getter
    @Value("1800000")
    private int validityPeriodWeb;

    @Value("Authorization")
    private String authorizationHeader;

    @Setter
    private String userAgent = "";

    private static final String AUDIENCE_UNKNOWN = "unknown";
    private static final String AUDIENCE_MOBILE = "mobile";
    private static final String AUDIENCE_TABLET = "tablet";
    private static final String AUDIENCE_WEB = "web";

    private final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    public String generateToken(User user) {
        return Jwts.builder()
                .setIssuer(appName)
                .setSubject(user.getUsername())
                .setAudience(generateAudience())
                .setIssuedAt(new Date())
                .claim("id", user.getId())
                .claim("name", user.getName())
                .claim("role", user.getRole().toString())
                .setExpiration(generateExpirationDate())
                .signWith(SIGNATURE_ALGORITHM, secret)
                .compact();
    }

    public int getValidityPeriod() {
        if (userAgent.equals("Mobile-Android"))
            return validityPeriodMobile;
        return validityPeriodWeb;
    }

    private String generateAudience() {
        if (userAgent.equals("Mobile-Android"))
            return AUDIENCE_MOBILE;
        return AUDIENCE_WEB;
    }

    private Date generateExpirationDate() {
        if (userAgent.equals("Mobile-Android"))
            return new Date(new Date().getTime() + validityPeriodMobile);
        return new Date(new Date().getTime() + validityPeriodWeb);
    }

    public String getAuthorizationHeaderFromHeader(HttpServletRequest request) {
        return request.getHeader(authorizationHeader);
    }

    public String getToken(HttpServletRequest request) {
        String authorizationHeader = getAuthorizationHeaderFromHeader(request);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer "))
            return authorizationHeader.substring(7);

        return null;
    }

    private Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException ex) {
            throw ex;
        } catch (Exception e) {
            return null;
        }
    }

    public String getUsernameFromToken(String token) {
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            assert claims != null;
            return claims.getSubject();
        } catch (ExpiredJwtException ex) {
            throw ex;
        } catch (Exception e) {
            return null;
        }
    }

    public Date getIssuedAtDateFromToken(String token) {
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            assert claims != null;
            return claims.getIssuedAt();
        } catch (ExpiredJwtException ex) {
            throw ex;
        } catch (Exception e) {
            return null;
        }
    }

    public String getAudienceFromToken(String token) {
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            assert claims != null;
            return claims.getAudience();
        } catch (ExpiredJwtException ex) {
            throw ex;
        } catch (Exception e) {
            return null;
        }
    }

    public Date getExpirationDateFromToken(String token) {
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            assert claims != null;
            return claims.getExpiration();
        } catch (ExpiredJwtException ex) {
            throw ex;
        } catch (Exception e) {
            return null;
        }
    }

    public Long getIdFromToken(String token) {
        try {
            final Claims claims = getAllClaimsFromToken(token);
            assert claims != null;
            return claims.get("id", Long.class);
        } catch (ExpiredJwtException ex) {
            throw ex;
        } catch (Exception e) {
            return null;
        }
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date dateOfCreation, Date lastPasswordResetDate) {
        return (lastPasswordResetDate != null && dateOfCreation.before(lastPasswordResetDate));
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        User user = (User) userDetails;
        final String username = getUsernameFromToken(token);
        final Date dateOfCreation = getIssuedAtDateFromToken(token);

        return username != null
                && username.equals(userDetails.getUsername())
                && !isCreatedBeforeLastPasswordReset(dateOfCreation, user.getLastPasswordResetDate());
    }
}
