package booking_app_team_2.bookie.domain;

public class User {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String surname;
    private Location addressOfResidence;
    private String telephone;
    private UserRole role;
    private boolean isBlocked = false;
}
