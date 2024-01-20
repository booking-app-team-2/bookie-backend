package booking_app_team_2.bookie.service;

import booking_app_team_2.bookie.domain.Owner;

public interface OwnerService extends GenericService<Owner>{
    Long findIdByAccommodationId(Long accommodationId);
}
