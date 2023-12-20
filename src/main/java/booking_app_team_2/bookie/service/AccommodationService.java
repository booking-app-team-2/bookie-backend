package booking_app_team_2.bookie.service;

import booking_app_team_2.bookie.domain.Accommodation;

import java.util.List;

public interface AccommodationService extends GenericService<Accommodation> {
    List<Accommodation> findAllByApproved(boolean isApproved);
}
