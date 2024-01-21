package booking_app_team_2.bookie.repository;

import booking_app_team_2.bookie.domain.Notification;
import booking_app_team_2.bookie.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends GenericRepository<Notification>{
    List<Notification> findAllByReceiver(User user);
}
