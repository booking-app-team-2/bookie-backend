package booking_app_team_2.bookie.dto;

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
public class AccommodationBasicInfoDTO {
    private Long id = null;
    private String name;
    private String description;
    private int minimumGuests;
    private int maximumGuests;
    private Location location;
    private Set<Amenities> amenities;
    private Set<Image> images;
    private AccommodationType type;
    private Set<AvailabilityPeriodDTO> availabilityPeriods;
    private boolean isReservationAutoAccepted;
}
