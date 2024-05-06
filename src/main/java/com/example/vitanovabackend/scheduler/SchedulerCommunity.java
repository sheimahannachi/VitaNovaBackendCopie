package com.example.vitanovabackend.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Component
@AllArgsConstructor
@Slf4j
@ComponentScan
public class SchedulerCommunity {
    private final SimpMessagingTemplate messagingTemplate;
    @Scheduled(cron = "0/30 * * * * *") // Run every 10 second

    // Schedule a notification every Sunday at midnight (00:00)
    //@Scheduled(cron = "0 0 0 ? * SUN")
    public void sendWeeklyNotification() {
        // Check if today is Sunday
        LocalDateTime now = LocalDateTime.now();
        if (now.getDayOfWeek() == DayOfWeek.SUNDAY) {
            String message = "Go check your new ranking now!";
            messagingTemplate.convertAndSend("/topic/weeklyNotification", message);
            log.info("Weekly notification sent: {}", message);
        }
    }
}
