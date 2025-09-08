package ru.cource.inno.java_pro.limits.controller;

import jakarta.validation.Valid;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.cource.inno.java_pro.limits.dto.*;
import ru.cource.inno.java_pro.limits.model.UserLimit;
import ru.cource.inno.java_pro.limits.service.UserLimitService;

@RestController
@RequestMapping("/api/limits")
@RequiredArgsConstructor
public class LimitsController {
    private final UserLimitService service;

    @GetMapping("/{userId}")
    public LimitResponse get(@PathVariable long userId) {
        UserLimit l = service.getOrCreate(userId);
        return LimitResponse.builder().userId(l.getUserId()).dayLimit(l.getDayLimit()).remaining(l.getRemaining()).lastResetDate(l.getLastResetDate()).build();
    }

    @PostMapping("/{userId}/spend")
    @ResponseStatus(HttpStatus.OK)
    public LimitResponse spend(@PathVariable long userId, @Valid @RequestBody SpendRequest req) {
        var l = service.spend(userId, req.getAmount());
        return LimitResponse.builder().userId(l.getUserId()).dayLimit(l.getDayLimit()).remaining(l.getRemaining()).lastResetDate(l.getLastResetDate()).build();
    }

    @PostMapping("/{userId}/restore")
    @ResponseStatus(HttpStatus.OK)
    public LimitResponse restore(@PathVariable long userId, @Valid @RequestBody RestoreRequest req) {
        var l = service.restore(userId, req.getAmount());
        return LimitResponse.builder().userId(l.getUserId()).dayLimit(l.getDayLimit()).remaining(l.getRemaining()).lastResetDate(l.getLastResetDate()).build();
    }

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public LimitResponse update(@PathVariable long userId, @Valid @RequestBody UpdateLimitRequest req) {
        var l = service.updateDayLimit(userId, req.getDayLimit());
        return LimitResponse.builder().userId(l.getUserId()).dayLimit(l.getDayLimit()).remaining(l.getRemaining()).lastResetDate(l.getLastResetDate()).build();
    }

    @PostMapping("/_reset-daily")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String resetDaily() {
        int n = service.resetAllToday();
        return "Reset done, affected=" + n;
    }
}
