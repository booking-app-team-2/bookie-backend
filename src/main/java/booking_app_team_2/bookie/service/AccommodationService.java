package booking_app_team_2.bookie.service;

import booking_app_team_2.bookie.domain.Accommodation;
import booking_app_team_2.bookie.dto.AccommodationBasicInfoDTO;
import booking_app_team_2.bookie.dto.AccommodationDTO;
import booking_app_team_2.bookie.dto.AccommodationApprovalDTO;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;

public interface AccommodationService extends GenericService<Accommodation> {
    // Example service interface
    // TODO: Declare accommodation-specific service methods
    List<AccommodationDTO> getAll();

    List<Accommodation> findSearched(String location, int numberOfGuests, String startDate, String endDate);

    AccommodationBasicInfoDTO updateAccommodationBasicInfo(Accommodation accommodation, AccommodationBasicInfoDTO accommodationBasicInfoDTO);

    List<Accommodation> findAllByIsApproved(boolean isApproved);

    void updateIsApproved(Long id, AccommodationApprovalDTO accommodationApprovalDTO);
}
