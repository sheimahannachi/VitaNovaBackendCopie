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
public class SchedulerExercises {
    private final SimpMessagingTemplate messagingTemplate;
    @Scheduled(cron = "0 */1 * * * *")

    // Schedule a notification to check exercises (adjust cron expression as needed)
    //@Scheduled(cron = "0 0 9 ? * MON-FRI") // Run every weekday at 9:00 AM
    public void sendExerciseNotification() {
        String message = "Go check exercises now!";
        messagingTemplate.convertAndSend("/topic/exerciseNotification", message);
        log.info("Exercise notification sent: {}", message);
    }
}

