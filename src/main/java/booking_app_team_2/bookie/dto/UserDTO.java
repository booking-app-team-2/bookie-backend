package booking_app_team_2.bookie.dto;

import booking_app_team_2.bookie.domain.Location;
import booking_app_team_2.bookie.domain.UserRole;

public class UserDTO {
    private Long id;
    private String email;
    private String name;
    private String surname;
    private Location addressOfResidence;
    private String telephone;
    private UserRole role;
    private boolean isBlocked = false;
}
