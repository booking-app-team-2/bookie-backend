package booking_app_team_2.bookie.service;

import booking_app_team_2.bookie.domain.Accommodation;
import booking_app_team_2.bookie.dto.AccommodationDTO;

import java.util.List;

public interface AccommodationService extends GenericService<Accommodation> {
    List<AccommodationDTO> getAll();
    List<Accommodation> findSearched(String location,int numberOfGuests,long startDate,long endDate);
}
