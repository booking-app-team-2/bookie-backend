package booking_app_team_2.bookie.service;

import booking_app_team_2.bookie.domain.Accommodation;
import booking_app_team_2.bookie.dto.AccommodationApprovalDTO;

import java.util.List;

public interface AccommodationService extends GenericService<Accommodation> {
    List<Accommodation> findAllByIsApproved(boolean isApproved);

    void updateIsApproved(Long id, AccommodationApprovalDTO accommodationApprovalDTO);
}
