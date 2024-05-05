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
public class SchedulerBooks {
    private final SimpMessagingTemplate messagingTemplate;

    // Schedule a book recommendation notification (adjust cron expression as needed)
    @Scheduled(cron = "0 0 10 ? * MON-FRI") // Run everyday at 10:00 AM
    public void sendBookRecommendation() {
        String message = "Check out this book for meditation and relaxation!";
        messagingTemplate.convertAndSend("/topic/bookRecommendation", message);
        log.info("Book recommendation sent: {}", message);
    }
}
