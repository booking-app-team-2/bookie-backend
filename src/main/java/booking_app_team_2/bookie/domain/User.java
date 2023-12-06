package booking_app_team_2.bookie.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@NoArgsConstructor
@SQLDelete(sql
        = "UPDATE User "
        + "SET isDeleted = true "
        + "WHERE id = ?")
@Where(clause = "isDeleted = false")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    private Long id;
    private String email;
    private String password;
    private String name;
    private String surname;
    private String addressOfResidence;
    private String telephone;
    private UserRole role;
    private boolean isBlocked = false;
    private boolean isDeleted = false;
}
