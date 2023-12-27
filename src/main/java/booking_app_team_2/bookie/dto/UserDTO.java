package booking_app_team_2.bookie.dto;

import booking_app_team_2.bookie.domain.Location;
import booking_app_team_2.bookie.domain.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserDTO {
    private String username;
    private String name;
    private String surname;
    private String addressOfResidence;
    private String telephone;
}
