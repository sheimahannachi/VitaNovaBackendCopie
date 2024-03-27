package com.example.vitanovabackend.Controllers;

import com.example.vitanovabackend.Service.INotificationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
public class NotificationController {
    INotificationService iNotificationService;

    @PutMapping("archiveNotification")
    public String archiveNotification(@RequestParam Long id){
        return iNotificationService.archiveNotification(id);
    }
}
