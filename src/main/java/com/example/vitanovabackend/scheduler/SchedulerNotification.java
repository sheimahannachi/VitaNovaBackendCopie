package com.example.vitanovabackend.scheduler;

import com.example.vitanovabackend.Service.INotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
@ComponentScan
public class SchedulerNotification {
    private final SimpMessagingTemplate messagingTemplate;

    private final INotificationService iNotificationService;

  //  @Scheduled(cron = "0 0/3 * * * *") // Triggered every 10 minutes
    @Scheduled(cron = "0 */3 * * * *")

    //@Scheduled(fixedRate = 10000) // Execute every 10 seconds
   // @Scheduled(cron = "0 0 8 * * *") // Execute daily at 8am
    public void sendNotification() {
        String message = "Go check nearby Gyms and Restaurants";
        messagingTemplate.convertAndSend("/topic/notifications", message);
    }

}
