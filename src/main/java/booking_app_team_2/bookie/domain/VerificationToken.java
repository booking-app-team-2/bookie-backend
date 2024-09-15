package booking_app_team_2.bookie.domain;

import booking_app_team_2.bookie.dto.UserRegistrationDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Entity
public class VerificationToken {
    @Getter
    @Setter
    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    private String token;

    @Setter
    @Getter
    @OneToOne(targetEntity = User.class)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Getter
    @Setter
    private LocalDateTime expiryDate;

    public VerificationToken(String token, User user, LocalDateTime localDateTime) {
        this.token = token;
        this.user = user;
        this.expiryDate = localDateTime;
    }
}
