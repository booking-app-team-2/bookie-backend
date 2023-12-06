package booking_app_team_2.bookie.domain;

import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Embeddable
public class Location {
    private double latitude;
    private double longitude;
}
