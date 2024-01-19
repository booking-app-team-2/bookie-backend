package booking_app_team_2.bookie.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserTelephoneDTO {
    @NotNull(message = "Telephone must not be null.")
    @Pattern(
            regexp = "^[+]?[(]?[0-9]{3}[)]? ?[0-9]{2,3}[ /]?[0-9]{3}[- ]?[0-9]{4,6}$",
            message = "Telephone is not in the correct format."
    )
    private String telephone;
}
