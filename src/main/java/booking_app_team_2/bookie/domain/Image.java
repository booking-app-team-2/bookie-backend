package booking_app_team_2.bookie.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class Image {
    @Id
    @SequenceGenerator(name = "IMAGE_SEQ", sequenceName = "SEQUENCE_IMAGE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IMAGE_SEQ")
    private Long id = null;
    private String path;
    private String name;
}
