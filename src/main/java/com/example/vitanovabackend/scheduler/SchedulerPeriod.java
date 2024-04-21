package com.example.vitanovabackend.scheduler;

import com.example.vitanovabackend.DAO.Entities.PeriodTracker;
import com.example.vitanovabackend.DAO.Entities.User;
import com.example.vitanovabackend.Service.IPeriodTrackerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
@ComponentScan
public class SchedulerPeriod {

    private final IPeriodTrackerService iPeriodTrackerService;
    // @Scheduled(cron = "0 0 0 * * *") // Run once a day at midnight
   @Scheduled(cron = "0/10 * * * * *") // Run every 10 second

    public void updateCyclePhases() {
        log.info("Scheduler executing updateCyclePhases() method...");
        // Retrieve users with period information from the database
        List<User> findByPeriodNotNull = iPeriodTrackerService.findByPeriodNotNull();

        // Iterate through each user and calculate their cycle phase
        for (User user : findByPeriodNotNull) {
            PeriodTracker periodTracker = user.getPeriodTracker();
            if (periodTracker != null) {
                String cyclePhase = iPeriodTrackerService.calculateCyclePhase(periodTracker);
                // Update the cycle phase in the PeriodTracker
                periodTracker.setCyclePhase(cyclePhase);
                // Save the updated PeriodTracker
                iPeriodTrackerService.AddPeriodInformation(periodTracker);
            }
        }
    }
}

