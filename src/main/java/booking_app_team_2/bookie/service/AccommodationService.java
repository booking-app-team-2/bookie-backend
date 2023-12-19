package booking_app_team_2.bookie.service;

import booking_app_team_2.bookie.domain.Accommodation;
import booking_app_team_2.bookie.domain.AvailabilityPeriod;
import booking_app_team_2.bookie.domain.Owner;
import booking_app_team_2.bookie.dto.AccommodationBasicInfoDTO;
import booking_app_team_2.bookie.dto.AccommodationDTO;

import java.util.List;
import java.util.Set;

public interface AccommodationService extends GenericService<Accommodation> {
    // Example service interface
    // TODO: Declare accommodation-specific service methods
    List<AccommodationDTO> getAll();
    List<Accommodation> findSearched(String location,int numberOfGuests,long startDate,long endDate);
    List<Accommodation> findAccommodationByOwner(Owner owner);
    AccommodationBasicInfoDTO updateAccommodationBasicInfo(Accommodation accommodation,AccommodationBasicInfoDTO accommodationBasicInfoDTO);
}
