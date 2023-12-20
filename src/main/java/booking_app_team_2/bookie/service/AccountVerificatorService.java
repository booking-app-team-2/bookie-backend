package booking_app_team_2.bookie.service;

import booking_app_team_2.bookie.domain.AccountVerificator;
import booking_app_team_2.bookie.domain.User;

import java.util.Optional;

public interface AccountVerificatorService extends GenericService<AccountVerificator> {
    Optional<AccountVerificator> findOneByUser(User user);
}
