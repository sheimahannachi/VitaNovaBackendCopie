package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.DAO.Entities.Notification;
import com.example.vitanovabackend.DAO.Entities.PeriodTracker;
import com.example.vitanovabackend.DAO.Repositories.NotificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class NotificationService implements INotificationService {
    NotificationRepository notificationRepository;

    @Override
    public String archiveNotification(Long idNotif) {
        Notification notification = notificationRepository.findById(idNotif).orElse(null);
        if (notification != null) {
            notification.setArchive(true);
            notificationRepository.save(notification);
            return "archived";
        } else {
            return "not found";
        }}
    @Override
    public List<Notification> getNotification() {
        return notificationRepository.findAll();
    }
    @Override
    public Notification getNotificationById (long idNotif) {
        return notificationRepository.findById(idNotif)
                .orElseThrow(() -> new NoSuchElementException("Notification not found with id: " + idNotif));
    }
    @Override
    public Notification AddNotification(Notification notification) {

        return notificationRepository.save(notification);
    }

    @Override
    public Notification UpdateNotification(Notification UpdatedNotification , long idNotif ) {

        Notification periodTracker = notificationRepository.findById(idNotif).orElse(null);

        if (periodTracker == null)
            return null;

        UpdatedNotification.setIdNotif(idNotif);
        return notificationRepository.save(UpdatedNotification);
    }
}



