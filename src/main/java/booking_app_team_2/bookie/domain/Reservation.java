package booking_app_team_2.bookie.domain;

public class Reservation {
    private Long id = null;
    private int numberOfGuests;
    private ReservationStatus status = ReservationStatus.Waiting;
    private Accommodation accommodation;
    private Guest reservee;
    private Period period;
}
