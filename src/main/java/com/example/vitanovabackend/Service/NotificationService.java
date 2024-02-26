package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.DAO.Entities.Notification;
import com.example.vitanovabackend.DAO.Entities.PeriodTracker;
import com.example.vitanovabackend.DAO.Repositories.NotificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationService implements INotificationService {
    NotificationRepository notificationRepository;

    @Override
    public String archiveNotification(Long id) {
        Notification notification = notificationRepository.findById(id).orElse(null);
        if (notification != null) {
            notification.setArchive(true);
            notificationRepository.save(notification);
            return "archived";
        } else {
            return "not found";
        }}}

