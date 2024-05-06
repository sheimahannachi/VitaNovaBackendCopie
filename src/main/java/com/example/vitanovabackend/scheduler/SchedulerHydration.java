package com.example.vitanovabackend.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
@ComponentScan
public class SchedulerHydration {

    private final SimpMessagingTemplate messagingTemplate;


    // Schedule a reminder every 30 minutes (adjust cron expression as needed)
    @Scheduled(cron = "0 */2 * * * *")
    public void sendHydrationReminder() {
        // Logic to send hydration reminder
        String message = "Don't forget to stay hydrated!";
        messagingTemplate.convertAndSend("/topic/hydrationReminders", message);
    }
}

