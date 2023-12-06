package booking_app_team_2.bookie.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class Image {
    @Id
    private Long id = null;
    private String path;
    private String name;
}
