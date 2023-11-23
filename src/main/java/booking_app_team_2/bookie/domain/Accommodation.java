package booking_app_team_2.bookie.domain;

import java.util.EnumSet;

public class Accommodation {
    private Long id;
    private String name;
    private String description;
    private Location location;
    private EnumSet<Amenities> amenities;
    private int minimumGuests;
    private int maximumGuests;
    private int reservationCancellationDeadline;
    private AccommodationType type;
    private boolean isPricedPerGuest;
    private boolean isApproved = false;
    private boolean isReservationAutoAccepted = false;
    private Owner owner;
}
