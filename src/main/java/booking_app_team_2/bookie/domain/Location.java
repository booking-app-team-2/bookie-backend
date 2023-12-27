package booking_app_team_2.bookie.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class Location {
    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;
}
