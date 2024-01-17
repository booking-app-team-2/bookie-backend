package booking_app_team_2.bookie.dto;

import booking_app_team_2.bookie.domain.Guest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

@Getter
public class ReserveeBasicInfoDTO {
    private final Long id;
    private final String name;
    private final String surname;

    @JsonIgnore
    public ReserveeBasicInfoDTO(Guest reservee) {
        id = reservee.getId();
        name = reservee.getName();
        surname = reservee.getSurname();
    }
}
