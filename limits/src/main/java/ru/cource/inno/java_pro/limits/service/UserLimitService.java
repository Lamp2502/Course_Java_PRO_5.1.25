package ru.cource.inno.java_pro.limits.service;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cource.inno.java_pro.limits.dao.UserLimitDao;
import ru.cource.inno.java_pro.limits.model.UserLimit;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserLimitService {
    private final UserLimitDao dao;

    @Transactional
    public UserLimit getOrCreate(long userId) {
        dao.ensureExists(userId);
        dao.lazyResetIfNeeded(userId);
        return dao.get(userId).orElseThrow();
    }

    @Transactional
    public UserLimit spend(long userId, BigDecimal amount) {
        dao.ensureExists(userId);
        dao.lazyResetIfNeeded(userId);
        int u = dao.spend(userId, amount);
        if (u == 0) throw new IllegalStateException("Недостаточно дневного лимита");
        return dao.get(userId).orElseThrow();
    }

    @Transactional
    public UserLimit restore(long userId, BigDecimal amount) {
        dao.ensureExists(userId);
        dao.lazyResetIfNeeded(userId);
        dao.restore(userId, amount);
        return dao.get(userId).orElseThrow();
    }

    @Transactional
    public UserLimit updateDayLimit(long userId, BigDecimal newLimit) {
        dao.ensureExists(userId);
        dao.lazyResetIfNeeded(userId);
        dao.updateDayLimit(userId, newLimit);
        return dao.get(userId).orElseThrow();
    }

    @Transactional
    public int resetAllToday() {
        return dao.resetAllToday();
    }
}
