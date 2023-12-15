package booking_app_team_2.bookie.service;

import booking_app_team_2.bookie.domain.User;
import booking_app_team_2.bookie.dto.UserPasswordDTO;

public interface UserService extends GenericService<User> {
    boolean isCorrectPassword(UserPasswordDTO userPasswordDTO, User user);
}
