package booking_app_team_2.bookie.domain;

import java.util.EnumSet;
import java.util.HashSet;

public class Accommodation {
    private Long id = null;
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
    private double averageRaiting;
    private Owner owner;
    private HashSet<AvailabilityPeriod> availabilityPeriods;
}
