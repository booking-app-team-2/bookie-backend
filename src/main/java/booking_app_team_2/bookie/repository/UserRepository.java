package booking_app_team_2.bookie.repository;

import booking_app_team_2.bookie.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends GenericRepository<User> {
    // TODO: Fix this if JPA cannot generate a query from method that returns optional
    Optional<User> findOneByUsername(String username);
}
