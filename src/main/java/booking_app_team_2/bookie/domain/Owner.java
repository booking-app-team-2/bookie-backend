package booking_app_team_2.bookie.domain;

public class Owner extends User {
    private boolean receivesReservationRequestNotifications = true;
    private boolean receivesReservationCancellationNotifiactions = true;
    private boolean receivesReviewNotifications = true;
    private boolean receivesAccommodationReviewNotifications = true;
}
