package booking_app_team_2.bookie.dto;

import booking_app_team_2.bookie.domain.AccommodationType;
import booking_app_team_2.bookie.domain.Amenities;
import booking_app_team_2.bookie.domain.AvailabilityPeriod;
import booking_app_team_2.bookie.domain.Location;
import booking_app_team_2.bookie.domain.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccommodationDTO {
    private Long id = null;
    private String name;
    private String description;
    private int minimumGuests;
    private int maximumGuests;
    private Location location;
    private Set<Amenities> amenities;

    // TODO: Refactor this, unless you want to send dates over HTTP.

    private Set<AvailabilityPeriod> availabilityPeriods;
    private Set<Image> images;
    private int reservationCancellationDeadline;
    private AccommodationType type;
    private boolean isReservationAutoAccepted;
}
