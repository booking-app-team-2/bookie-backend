package booking_app_team_2.bookie.service;

import booking_app_team_2.bookie.domain.Accommodation;
import booking_app_team_2.bookie.domain.Owner;
import booking_app_team_2.bookie.exception.HttpTransferException;
import booking_app_team_2.bookie.repository.AccommodationRepository;
import booking_app_team_2.bookie.repository.OwnerRepository;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Setter
@Service
public class OwnerServiceImpl implements OwnerService{

    private OwnerRepository ownerRepository;
    private AccommodationRepository accommodationRepository;

    public OwnerServiceImpl(OwnerRepository ownerRepository, AccommodationRepository accommodationRepository){
        this.ownerRepository=ownerRepository;
        this.accommodationRepository=accommodationRepository;
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

    @Override
    public Long findIdByAccommodationId(Long accommodationId) {
        Optional<Accommodation> optionalAccommodation=accommodationRepository.findById(accommodationId);
        if(optionalAccommodation.isEmpty()){
            throw new HttpTransferException(HttpStatus.NOT_FOUND, "No such accommodation exists.");
        }
        Accommodation accommodation=optionalAccommodation.get();
        Owner owner=ownerRepository.findByAccommodationsContaining(accommodation);
        if(owner==null){
            throw new HttpTransferException(HttpStatus.NOT_FOUND, "No owner has this accommodation");
        }
        return owner.getId();
    }
}
