package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.DAO.Entities.Notification;
import com.example.vitanovabackend.DAO.Repositories.NotificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import com.example.vitanovabackend.DAO.Entities.*;
import com.example.vitanovabackend.DAO.Repositories.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NotificationService implements INotificationService {
    NotificationRepository notificationRepository;
    UserRepository userRepository;
    private final SimpMessageSendingOperations messagingTemplate;


    @Override
    public String archiveNotification(Long Id) {
        Notification notification = notificationRepository.findById(Id).orElse(null);
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
    public Notification getNotificationById (long Id) {
        return notificationRepository.findById(Id)
                .orElseThrow(() -> new NoSuchElementException("Notification not found with id: " + Id));
    }
    @Override
    public Notification addNotification(Notification notification) {
        notification.setUser(userRepository.findById(notification.getUser().getIdUser())
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + notification.getUser().getIdUser())));
        return notificationRepository.save(notification);
    }


    @Override
    public Notification UpdateNotification(Notification UpdatedNotification , long Id ) {

        Notification periodTracker = notificationRepository.findById(Id).orElse(null);

        if (periodTracker == null)
            return null;

        UpdatedNotification.setId(Id);
        return notificationRepository.save(UpdatedNotification);
    }
    @Override
    public void subscribeToCategory(Long idUser, List<String> categories) {
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + idUser));

        // Create new notifications for each category and save them to the database
        List<Notification> notifications = new ArrayList<>();
        for (String category : categories) {
            Notification notification = new Notification();
            notification.setUser(user);
            notification.setSubscription(true);
            notification.setCategories(List.of(category)); // Set categories for this notification

            // Set content based on category
            String content = ""; // Initialize content

            // Assign content based on category
            switch (category) {
                case "Period Tracking":
                    content = "Track your menstrual cycle and receive personalized insights.";
                    break;
                case "Exercises":
                    content = "Discover new workout routines and stay active.";
                    break;
                case "Food":
                    content = "Explore healthy recipes and dietary tips.";
                    break;
                case "Community":
                    content = "Connect with like-minded individuals and share experiences.";
                    break;
                case "Shop":
                    content = "Explore our latest products and exclusive offers.";
                    break;
                default:
                    content = "New updates and information about " + category + ".";
                    break;
            }

            notification.setContent(content); // Set content for this notification
            notifications.add(notification);
        }
        notificationRepository.saveAll(notifications);
    }

    @Override
    public void unsubscribeFromCategory(Long idUser) {
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + idUser));

        List<Notification> notifications = user.getNotifications();
        for (Notification notification : notifications) {
            notification.setSubscription(false);
        }
        notificationRepository.saveAll(notifications);
    }
    @Override
    public List<String> getNotificationContentsByCriteria(Long idUser, List<String> categories, Boolean subscription) {
        // Fetch notifications from database based on selected categories
        List<Notification> notifications = notificationRepository.findByUser_IdUserAndCategoriesInAndSubscription(idUser, categories, subscription);

        // Extract content from notifications and collect as list of strings
        List<String> notificationContents = notifications.stream()
                .map(Notification::getContent)
                .collect(Collectors.toList());

        // Log the notifications being sent
        System.out.println("Sending notifications to user " + idUser + ": " + notificationContents);

        // Send notifications' content to WebSocket topic for the user
        String destination = "/topic/notifications/" + idUser;
        messagingTemplate.convertAndSend(destination, notificationContents);

        return notificationContents;
    }
}



