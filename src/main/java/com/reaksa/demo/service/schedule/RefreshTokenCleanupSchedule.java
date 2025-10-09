package com.reaksa.demo.service.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenCleanupSchedule {

    @Scheduled(fixedRate = 5000)
    public void cleanupRefreshToken() {
        System.out.println("Cleaning up refresh token");
    }
}
