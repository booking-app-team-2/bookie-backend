package booking_app_team_2.bookie.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserBasicInfoDTO {
    @NotBlank(message = "Name must not be blank.")
    private String name;
    @NotBlank(message = "Surname must not be blank.")
    private String surname;
}
