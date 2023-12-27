package booking_app_team_2.bookie.dto;

import booking_app_team_2.bookie.domain.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationDTO {
    private String username;
    private String password;
    private String name;
    private String surname;
    private String addressOfResidence;
    private String telephone;
    private UserRole role;
}
