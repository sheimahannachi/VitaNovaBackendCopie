package com.example.vitanovabackend.Controllers;

import com.example.vitanovabackend.DAO.Entities.Notification;
import com.example.vitanovabackend.Service.INotificationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
public class NotificationController {
    INotificationService iNotificationService;

    @PutMapping("archiveNotification")
    public String archiveNotification(@RequestParam Long idNotif){
        return iNotificationService.archiveNotification(idNotif);
    }

    @PostMapping("AddNotification")
    public Notification AddNotification(@RequestBody Notification notification) {
        return iNotificationService.AddNotification(notification);
    }
    @PutMapping ("UpdateNotification")
    public Notification UpdateNotification(@RequestBody Notification UpdatedNotification ,@RequestParam long idNotif ) {
        return iNotificationService.UpdateNotification(UpdatedNotification,idNotif);
    }
    @GetMapping("getNotification")
    public List<Notification> getNotification(){
        return iNotificationService.getNotification();
    }
    @GetMapping("getNotificationById/{IdNotif}")
    public Notification getNotificationById(@PathVariable("IdNotif") long idNotif) {

        return iNotificationService.getNotificationById(idNotif);}
}
