package booking_app_team_2.bookie.service;

import booking_app_team_2.bookie.domain.Notification;
import booking_app_team_2.bookie.domain.User;

import java.util.List;

public interface NotificationService extends GenericService {

    void addNotification(Notification notification);

    List<Notification> findByReciever(User user);

}
