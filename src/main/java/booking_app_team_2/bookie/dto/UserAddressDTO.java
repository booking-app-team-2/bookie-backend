package booking_app_team_2.bookie.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserAddressDTO {
    @NotBlank(message = "Address of residence must not be blank.")
    private String addressOfResidence;
}
