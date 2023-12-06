package booking_app_team_2.bookie.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class Report {
    @Id
    @SequenceGenerator(name = "REPORT_SEQ", sequenceName = "SEQUENCE_REPORT", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REPORT_SEQ")
    private Long id = null;
    private String body;
    @ManyToOne
    private User reporter;
    @ManyToOne
    private User reportee;
}
