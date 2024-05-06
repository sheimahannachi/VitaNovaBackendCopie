package com.example.vitanovabackend.Controllers;

import com.example.vitanovabackend.DAO.Entities.Notification;
import com.example.vitanovabackend.Service.INotificationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@AllArgsConstructor
@RequestMapping("/Notification")
public class NotificationController {
    INotificationService iNotificationService;
    // Endpoint to archive a notification
    @PutMapping("/archive/{Id}")
    public String archiveNotification(@PathVariable Long Id) {
        return iNotificationService.archiveNotification(Id);
    }

    // Endpoint to get all notifications
    @GetMapping("/getNotification")
    public List<Notification> getNotification() {
        return iNotificationService.getNotification();
    }

    // Endpoint to get a notification by id
    @GetMapping("getNotificationById/{Id}")
    public Notification getNotificationById(@PathVariable long Id) {
        return iNotificationService.getNotificationById(Id);
    }

    // Endpoint to add a notification
    @PostMapping("/addNotification")
    public ResponseEntity<?> addNotification(@RequestBody Notification notification) {
        try {
            Notification savedNotification = iNotificationService.addNotification(notification);
            return ResponseEntity.ok(savedNotification);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found with id: " + notification.getUser().getIdUser());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to add notification: " + e.getMessage());
        }
    }

    // Endpoint to update a notification



    @PutMapping("update/{Id}")
    public ResponseEntity<Notification> updateNotification(@PathVariable("Id") long Id,
                                                           @RequestBody Notification updatedNotification) {
        Notification notification = iNotificationService.UpdateNotification(updatedNotification, Id);
        if (notification == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(notification);
    }

    @PostMapping("/subscribe/{idUser}")
    public ResponseEntity<Map<String, String>> subscribeToCategories(@PathVariable Long idUser,
                                                                     @RequestBody List<String> categories) {
        try {
            iNotificationService.subscribeToCategory(idUser, categories);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Subscribed successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Failed to subscribe: " + e.getMessage()));
        }
    }

    @PostMapping("/unsubscribe/{idUser}")
    public ResponseEntity<Map<String, String>> unsubscribeFromCategories(@PathVariable Long idUser) {
        try {
            iNotificationService.unsubscribeFromCategory(idUser);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Unsubscribed successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Failed to unsubscribe: " + e.getMessage()));
        }
    }
    @GetMapping("/notifications")
    public ResponseEntity<List<String>> getNotificationContentsByCriteria(
            @RequestParam Long idUser,
            @RequestParam List<String> categories,
            @RequestParam Boolean subscription) {

        List<String> notificationContents = iNotificationService.getNotificationContentsByCriteria(idUser, categories, subscription);

        if (notificationContents.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(notificationContents);
    }



}
