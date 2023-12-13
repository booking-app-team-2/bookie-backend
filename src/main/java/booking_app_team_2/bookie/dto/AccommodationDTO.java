package booking_app_team_2.bookie.dto;

import booking_app_team_2.bookie.domain.AccommodationType;
import booking_app_team_2.bookie.domain.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private int reservationCancellationDeadline;
    private AccommodationType type;
}
