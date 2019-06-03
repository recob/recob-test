package com.recob.scheduler;

import com.recob.map.RecobUserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class SurveyScheduler {

    private RecobUserRepository recobUserRepository;

//    @Scheduled(fixedDelay = 1, initialDelay = 1000 * 60 * 5)
    public void checkConnections() {
        if (recobUserRepository.count() == 0) {
            System.exit(0);
        }
    }

//    @Scheduled(fixedDelay = 1, initialDelay = 1000 * 60 * 60)
    public void exit() {
        System.exit(0);
    }
}
