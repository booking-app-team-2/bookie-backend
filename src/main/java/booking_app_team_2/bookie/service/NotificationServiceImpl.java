package booking_app_team_2.bookie.service;

import booking_app_team_2.bookie.domain.Notification;
import booking_app_team_2.bookie.domain.User;
import booking_app_team_2.bookie.repository.NotificationRepository;
import booking_app_team_2.bookie.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService{

    private NotificationRepository notificationRepository;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List findAll() {
        return null;
    }

    @Override
    public Page findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Optional findOne(Long id) {
        return Optional.empty();
    }

    @Override
    public Object save(Object o) {
        return null;
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void addNotification(Notification notification) {
        this.notificationRepository.save(notification);
    }

    @Override
    public List<Notification> findByReciever(User user) {
        return notificationRepository.findAllByReceiver(user);
    }
}
