package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.DAO.Entities.Notification;

import java.util.List;

public interface INotificationService {
      String archiveNotification(Long idNotif);
      List<Notification> getNotification();
      Notification AddNotification(Notification notification);
      Notification getNotificationById (long idNotif);
      Notification UpdateNotification(Notification UpdatedNotification , long idNotif );

}
