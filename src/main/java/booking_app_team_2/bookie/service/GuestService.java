package booking_app_team_2.bookie.service;

import booking_app_team_2.bookie.domain.Guest;

public interface GuestService extends GenericService<Guest> {
    Boolean addFavouriteAccommodation(Long guestId,Long accommodationId);
}
