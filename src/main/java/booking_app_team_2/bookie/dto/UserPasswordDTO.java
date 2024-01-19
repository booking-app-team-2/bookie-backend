package booking_app_team_2.bookie.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserPasswordDTO {
    @NotEmpty(message = "Your current password must not be empty.")
    private String currentPassword;
    @NotNull(message = "Your new password must not be null.")
    @Pattern(
            regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{12,}$",
            message = "Your new password is not in the valid form."
    )
    private String newPassword;
}
