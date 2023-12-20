package booking_app_team_2.bookie.service;

import booking_app_team_2.bookie.domain.Guest;
import booking_app_team_2.bookie.repository.GuestRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Setter
@Service
public class GuestServiceImpl implements GuestService {

    private GuestRepository guestRepository;

    @Autowired
    public GuestServiceImpl(GuestRepository guestRepository){
        this.guestRepository=guestRepository;
    }
    @Override
    public List<Guest> findAll() {
        return guestRepository.findAll();
    }

    @Override
    public Page<Guest> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<Guest> findOne(Long id) {
        return Optional.empty();
    }

    @Override
    public Guest save(Guest guest) {
        return null;
    }

    @Override
    public void remove(Long id) {

    }
}
