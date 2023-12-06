package booking_app_team_2.bookie.domain;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AccountVerificator {
    private Long id = null;
    private long dateOfRegistration;
    private boolean isVerified = false;
    private User user;
}
