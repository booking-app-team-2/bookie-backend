package booking_app_team_2.bookie.exception;

public class GuestHasReservationsException extends RuntimeException{
    public GuestHasReservationsException(String message) {
        super(message);
    }
}
