package booking_app_team_2.bookie.domain;

import java.util.Set;

public class Guest extends User {
    private boolean receivesReservationRequestNotifications = true;
    private Set<Accommodation> favouriteAccommodations;
}
