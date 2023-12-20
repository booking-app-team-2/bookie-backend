package booking_app_team_2.bookie.service;

import booking_app_team_2.bookie.domain.Accommodation;
import booking_app_team_2.bookie.domain.Owner;
import booking_app_team_2.bookie.repository.OwnerRepository;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Setter
@Service
public class OwnerServiceImpl implements OwnerService{

    private OwnerRepository ownerRepository;

    public OwnerServiceImpl(OwnerRepository ownerRepository){
        this.ownerRepository=ownerRepository;
    }
    @Override
    public List<Owner> findAll() {
        return null;
    }

    @Override
    public Page<Owner> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<Owner> findOne(Long id) {
        return this.ownerRepository.findById(id);
    }

    @Override
    public Owner save(Owner owner) {
        return null;
    }


    @Override
    public void remove(Long id) {

    }
}
