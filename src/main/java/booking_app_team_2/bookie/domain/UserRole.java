package booking_app_team_2.bookie.domain;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    Guest,
    Owner,
    Admin;

    @Override
    public String getAuthority() {
        return this.toString();
    }
}
