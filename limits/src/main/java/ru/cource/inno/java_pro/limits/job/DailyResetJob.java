package ru.cource.inno.java_pro.limits.job;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.cource.inno.java_pro.limits.service.UserLimitService;

@Component
@RequiredArgsConstructor
@Slf4j
public class DailyResetJob {
    private final UserLimitService service;

    @Scheduled(cron = "0 0 0 * * *")
    public void resetAll() {
        int n = service.resetAllToday();
    }
}