package com.example.vitanovabackend.Scheduling;

import com.example.vitanovabackend.Service.IChallengeResultService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class ChallengeScheduling {

    IChallengeResultService service;
//cron = "0 0 0 * * 0"
    @Scheduled(fixedDelay = 5000)
    public void getWeeklyResult(){
        service.ChallengeResult();
        log.info("Weekly result done");
    }
}
