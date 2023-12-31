package booking_app_team_2.bookie.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@NoArgsConstructor
@Getter
@Setter
@SQLDelete(sql
        = "UPDATE image "
        + "SET is_deleted = true "
        + "WHERE id = ?")
@Where(clause = "is_deleted = false")
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"path", "name"}))
public class Image {
    @Id
    @SequenceGenerator(name = "image_seq", sequenceName = "sequence_image", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "image_seq")
    @Column(unique = true, nullable = false)
    private Long id = null;

    @Column(nullable = false)
    private String path;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;
}
