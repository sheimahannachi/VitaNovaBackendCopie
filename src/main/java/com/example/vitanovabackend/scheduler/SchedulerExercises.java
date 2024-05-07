package com.example.vitanovabackend.scheduler;

import com.example.vitanovabackend.DAO.Entities.WorkoutSession;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
@ComponentScan
public class SchedulerExercises {
    private final SimpMessagingTemplate messagingTemplate;

    @PersistenceContext
    private EntityManager entityManager;
    @Scheduled(cron = "0 */1 * * * *")

   // @Scheduled(cron = "0 0 9 * * MON-FRI") // Run every weekday at 9:00 AM
    public void sendExerciseNotification() {
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime thresholdDate = currentDate.minusDays(2); // Calculate threshold date

        // Query to find workout sessions that ended more than 2 days ago
        TypedQuery<WorkoutSession> query = entityManager.createQuery(
                "SELECT ws FROM WorkoutSession ws WHERE ws.time_start <= :thresholdDate",
                WorkoutSession.class
        );
        query.setParameter("thresholdDate", thresholdDate);

        List<WorkoutSession> expiredSessions = query.getResultList();
        for (WorkoutSession session : expiredSessions) {
            String message = "It’s been 2 days since you didn’t exercise so don’t forget to exercise!";
            messagingTemplate.convertAndSend("/topic/exerciseNotification", message);
            log.info("Exercise notification sent: {}", message);
        }
    }
}

