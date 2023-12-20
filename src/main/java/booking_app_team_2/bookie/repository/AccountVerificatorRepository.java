package booking_app_team_2.bookie.repository;

import booking_app_team_2.bookie.domain.AccountVerificator;
import booking_app_team_2.bookie.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountVerificatorRepository extends GenericRepository<AccountVerificator> {
    Optional<AccountVerificator> findOneByUser(User user);
}
