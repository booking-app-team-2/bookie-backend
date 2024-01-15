package booking_app_team_2.bookie.dto;

import booking_app_team_2.bookie.domain.Guest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserBasicInfoDTO {
    private String name;
    private String surname;

    @JsonIgnore
    public UserBasicInfoDTO(Guest guest) {
        name = guest.getName();
        surname = guest.getSurname();
    }
}
