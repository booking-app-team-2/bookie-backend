package booking_app_team_2.bookie.domain;

import java.util.HashSet;

public class Guest extends User {
    private boolean receivesReservationRequestNotifications = true;
    private HashSet<Accommodation> favouriteAccommodations;
}
