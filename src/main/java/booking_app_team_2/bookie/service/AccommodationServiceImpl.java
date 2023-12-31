package booking_app_team_2.bookie.service;

import booking_app_team_2.bookie.domain.*;
import booking_app_team_2.bookie.dto.AccommodationBasicInfoDTO;
import booking_app_team_2.bookie.dto.AccommodationDTO;
import booking_app_team_2.bookie.domain.Accommodation;
import booking_app_team_2.bookie.domain.AvailabilityPeriod;
import booking_app_team_2.bookie.dto.AccommodationApprovalDTO;
import booking_app_team_2.bookie.exception.HttpTransferException;
import booking_app_team_2.bookie.repository.AccommodationRepository;
import booking_app_team_2.bookie.repository.ReservationRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.*;
import java.util.stream.Collectors;

@Setter
@Service
public class AccommodationServiceImpl implements AccommodationService {
    private AccommodationRepository accommodationRepository;
    private ReservationRepository reservationRepository;

    @Autowired
    public AccommodationServiceImpl(AccommodationRepository accommodationRepository,ReservationRepository reservationRepository) {
        this.accommodationRepository = accommodationRepository;
        this.reservationRepository=reservationRepository;
    }

    @Override
    public List<Accommodation> findSearched(String location,int numberOfGuests,long startDate,long endDate){
        List<Accommodation> accommodations = accommodationRepository.findAll();
        List<Accommodation> newAccommodations= new ArrayList<>(Collections.emptyList());
        for(Accommodation accommodation:accommodations){
            if((numberOfGuests>=accommodation.getMinimumGuests() && numberOfGuests<=accommodation.getMaximumGuests())||(numberOfGuests==0)){
                //TODO:Add for location
                for(AvailabilityPeriod availabilityPeriod:accommodation.getAvailabilityPeriods()){
                    if((startDate>=availabilityPeriod.getPeriod().getStartDate() && endDate<=availabilityPeriod.getPeriod().getEndDate())||(startDate==0 && endDate==0)){
                        newAccommodations.add(accommodation);
                        break;
                    }
                }
            }
        }
        return newAccommodations;
    }
    @Override
    public List<AccommodationDTO> getAll(){
        List<Accommodation> accommodations = accommodationRepository.findAll();
        return accommodations.stream()
                .map(accommodation -> new AccommodationDTO(accommodation.getId(),accommodation.getName(),accommodation.getDescription(),accommodation.getMinimumGuests(),accommodation.getMaximumGuests(),accommodation.getLocation(),accommodation.getAmenities(),accommodation.getAvailabilityPeriods(),accommodation.getImages(),accommodation.getReservationCancellationDeadline(),accommodation.getType(),accommodation.isReservationAutoAccepted()))
                .collect(Collectors.toList());
    }
    @Override
    public AccommodationBasicInfoDTO updateAccommodationBasicInfo(Accommodation accommodation, AccommodationBasicInfoDTO accommodationBasicInfoDTO){
        accommodation.setName(accommodationBasicInfoDTO.getName());
        accommodation.setDescription(accommodationBasicInfoDTO.getDescription());
        accommodation.setLocation(accommodationBasicInfoDTO.getLocation());
        accommodation.getImages().clear();
        Set<Image> images=accommodationBasicInfoDTO.getImages();
        accommodation.getImages().addAll(images);
        accommodation.setAmenities(accommodationBasicInfoDTO.getAmenities());
        for(Reservation reservation:reservationRepository.findReservationsByAccommodation_Id(accommodation.getId())){
            if(accommodationBasicInfoDTO.getMinimumGuests()>reservation.getNumberOfGuests() || accommodationBasicInfoDTO.getMaximumGuests()<reservation.getNumberOfGuests()){
                return null;
            }
            for(AvailabilityPeriod period:accommodation.getAvailabilityPeriods()){
                if(reservation.getPeriod().getStartDate()>=period.getPeriod().getStartDate() && reservation.getPeriod().getEndDate()<=period.getPeriod().getEndDate()){
                    boolean flag=true;
                    for(AvailabilityPeriod afterPeriod:accommodationBasicInfoDTO.getAvailabilityPeriods()){
                        if(afterPeriod.getId()==period.getId() && afterPeriod.getPeriod().getEndDate()==period.getPeriod().getEndDate() && afterPeriod.getPeriod().getStartDate()==period.getPeriod().getStartDate() && afterPeriod.getPrice().setScale(2).equals(period.getPrice()) && afterPeriod.isDeleted()==period.isDeleted()){
                            flag=false;
                            break;
                        }
                    }
                    if(flag){
                        return null;
                    }
                }
            }
        }
        accommodation.getAvailabilityPeriods().clear();
        Set<AvailabilityPeriod> availabilityPeriods=accommodationBasicInfoDTO.getAvailabilityPeriods();
        accommodation.getAvailabilityPeriods().addAll(availabilityPeriods);
        accommodation.setType(accommodationBasicInfoDTO.getType());
        accommodation.setMinimumGuests(accommodationBasicInfoDTO.getMinimumGuests());
        accommodation.setMaximumGuests(accommodationBasicInfoDTO.getMaximumGuests());
        accommodation.setReservationAutoAccepted(accommodationBasicInfoDTO.isReservationAutoAccepted());
        this.save(accommodation);
        return accommodationBasicInfoDTO;
    }
    @Override
    public List<Accommodation> findAll() {
        return accommodationRepository.findAll();
    }

    @Override
    public Page<Accommodation> findAll(Pageable pageable) {
        return accommodationRepository.findAll(pageable);
    }

    @Override
    public List<Accommodation> findAllByIsApproved(boolean isApproved) {
        return accommodationRepository.findAllByIsApproved(isApproved);
    }

    @Override
    public Optional<Accommodation> findOne(Long id) {
        return accommodationRepository.findById(id);
    }

    @Override
    public Accommodation save(Accommodation accommodation) {
        return accommodationRepository.save(accommodation);
    }

    @Override
    public void updateIsApproved(Long id, AccommodationApprovalDTO accommodationApprovalDTO) {
        Optional<Accommodation> accommodationOptional = accommodationRepository.findById(id);
        if (accommodationOptional.isEmpty())
            throw new HttpTransferException(HttpStatus.NOT_FOUND, "No such accommodation exists.");

        Accommodation accommodation = accommodationOptional.get();

        accommodation.setApproved(accommodationApprovalDTO.isApproved());
        accommodationRepository.save(accommodation);
    }

    @Override
    public void remove(Long id) {
        accommodationRepository.deleteById(id);
    }

}
