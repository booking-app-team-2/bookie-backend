package booking_app_team_2.bookie.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserAuthenticationDTO {
    private String username;
    private String password;
}
