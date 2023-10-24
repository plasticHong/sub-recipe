package com.hong.schedule;

import com.hong.service.MemberAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Log4j2
@RequiredArgsConstructor
public class ScheduledJobs {

    private final String EVERY_MIDNIGHT = "0 0 0 * * *";
    private final MemberAccountService memberAccountService;

    @Scheduled(cron = EVERY_MIDNIGHT)
    public void extract() {
        log.info("job started in {}", LocalDateTime.now());
        memberAccountService.memberDelete();
        log.info("job finished in {}",LocalDateTime.now());
    }

}
