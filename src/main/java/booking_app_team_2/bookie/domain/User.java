package booking_app_team_2.bookie.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@SQLDelete(sql
        = "UPDATE user_bookie "
        + "SET is_deleted = true "
        + "WHERE id = ?")
@Where(clause = "is_deleted = false")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "user_bookie")
public class User implements UserDetails {
    @Id
    @SequenceGenerator(name = "user_seq", sequenceName = "sequence_user", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @Column(unique = true, nullable = false)
    private Long id;

    @Getter(AccessLevel.NONE)
    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(name = "address_of_residence", nullable = false)
    private String addressOfResidence;

    @Column(nullable = false)
    private String telephone;

    @Column(name = "last_password_reset_date", nullable = false)
    private Timestamp lastPasswordResetDate = new Timestamp(new Date().getTime());

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Column(name = "is_blocked", nullable = false)
    private boolean isBlocked = false;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    public User(Long id, String username, String password, String name, String surname, String addressOfResidence, String telephone, UserRole role, boolean isBlocked) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.addressOfResidence = addressOfResidence;
        this.telephone = telephone;
        this.role = role;
        this.isBlocked = isBlocked;
    }

    public User(String username, String password, String name, String surname, String addressOfResidence,
                String telephone, UserRole role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.addressOfResidence = addressOfResidence;
        this.telephone = telephone;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(role);
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // TODO: Decide what enabled means and set this up
    @Override
    public boolean isEnabled() {
        return !isBlocked;
    }
}
