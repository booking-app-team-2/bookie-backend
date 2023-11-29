package booking_app_team_2.bookie.dto;

import booking_app_team_2.bookie.domain.AccommodationType;
import booking_app_team_2.bookie.domain.Location;

public class AccommodationDTO {
    private Long id = null;
    private String name;
    private String description;
    private Location location;
    private int minimumGuests;
    private int maximumGuests;
    private int reservationCancellationDeadline;
    private AccommodationType type;
    private boolean isPricedPerGuest;
}
