package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.DAO.Entities.Notification;

import java.util.List;

public interface INotificationService {
      String archiveNotification(Long Id);
      List<Notification> getNotification();
      Notification addNotification(Notification notification);
      Notification getNotificationById (long Id);
      Notification UpdateNotification(Notification UpdatedNotification , long Id );
      void subscribeToCategory(Long idUser, List<String> categories);
      void unsubscribeFromCategory(Long idUser);
      List<String> getNotificationContentsByCriteria(Long idUser, List<String> categories, Boolean subscription);

}
