package com.example.vitanovabackend.DAO.Repositories;

import com.example.vitanovabackend.DAO.Entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.vitanovabackend.DAO.Entities.User;

import java.util.List;


@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {
    List<Notification> findByUser_IdUserAndCategoriesInAndSubscription(Long idUser, List<String> categories, Boolean subscription);

}

