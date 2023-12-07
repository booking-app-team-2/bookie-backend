package booking_app_team_2.bookie.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Embeddable
public class Location {
    @Column(nullable = false)
    private double latitude;
    @Column(nullable = false)
    private double longitude;
}
