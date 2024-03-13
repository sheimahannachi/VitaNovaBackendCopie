package com.example.vitanovabackend.Scheduling;

import com.example.vitanovabackend.Service.IInactiveCommunity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class CommunityScheduling {
    IInactiveCommunity iInactiveCommunity;

    @Scheduled(cron = "0 0 0 * * *")
    public void inactiveCommunties(){
        iInactiveCommunity.inactiveCommunities();
        log.info("Daily inactivity check done...");
    }

}

