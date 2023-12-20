package booking_app_team_2.bookie.service;

import booking_app_team_2.bookie.domain.AvailabilityPeriod;
import booking_app_team_2.bookie.repository.AvailabilityPeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AvailabilityPeriodServiceImpl implements AvailabilityPeriodService {
    private AvailabilityPeriodRepository availabilityPeriodRepository;

    @Autowired
    public AvailabilityPeriodServiceImpl(AvailabilityPeriodRepository availabilityPeriodRepository) {
        this.availabilityPeriodRepository = availabilityPeriodRepository;
    }

    @Override
    public List<AvailabilityPeriod> findAll() {
        return null;
    }

    @Override
    public Page<AvailabilityPeriod> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<AvailabilityPeriod> findOne(Long id) {
        return Optional.empty();
    }

    @Override
    public AvailabilityPeriod save(AvailabilityPeriod availabilityPeriod) {
        return availabilityPeriodRepository.save(availabilityPeriod);
    }

    @Override
    public void remove(Long id) {
        availabilityPeriodRepository.deleteById(id);
    }

    @Autowired
    public void setAvailabilityPeriodRepository(AvailabilityPeriodRepository availabilityPeriodRepository) {
        this.availabilityPeriodRepository = availabilityPeriodRepository;
    }
}
