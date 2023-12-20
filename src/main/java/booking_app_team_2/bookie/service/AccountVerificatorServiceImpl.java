package booking_app_team_2.bookie.service;

import booking_app_team_2.bookie.domain.AccountVerificator;
import booking_app_team_2.bookie.domain.User;
import booking_app_team_2.bookie.repository.AccountVerificatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountVerificatorServiceImpl implements AccountVerificatorService {
    private AccountVerificatorRepository accountVerificatorRepository;

    @Autowired
    public AccountVerificatorServiceImpl(AccountVerificatorRepository accountVerificatorRepository) {
        this.accountVerificatorRepository = accountVerificatorRepository;
    }

    @Override
    public List<AccountVerificator> findAll() {
        return null;
    }

    @Override
    public Page<AccountVerificator> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<AccountVerificator> findOne(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<AccountVerificator> findOneByUser(User user) {
        return accountVerificatorRepository.findOneByUser(user);
    }

    @Override
    public AccountVerificator save(AccountVerificator accountVerificator) {
        return null;
    }

    @Override
    public void remove(Long id) {

    }

    @Autowired
    public void setAccountVerificatorRepository(AccountVerificatorRepository accountVerificatorRepository) {
        this.accountVerificatorRepository = accountVerificatorRepository;
    }
}
