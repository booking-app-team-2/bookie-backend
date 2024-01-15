package booking_app_team_2.bookie.dto;

import booking_app_team_2.bookie.domain.AccountVerificator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZoneId;

@NoArgsConstructor
@Getter
public class AccountVerificatorDTO {
    private long timestampOfRegistration;
    private boolean isVerified;
    private Long userId;

    @JsonIgnore
    public AccountVerificatorDTO(AccountVerificator accountVerificator) {
        timestampOfRegistration = accountVerificator
                .getTimestampOfRegistration()
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
        isVerified = accountVerificator.isVerified();
        userId = accountVerificator.getUser().getId();
    }
}
