package ru.cource.inno.java_pro.limits.dao;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;
import ru.cource.inno.java_pro.limits.model.UserLimit;

import java.util.*;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserLimitDao {
    private final JdbcTemplate jdbc;
    private static final RowMapper<UserLimit> M = (rs, i) -> UserLimit.builder()
        .userId(rs.getLong("user_id")).dayLimit(rs.getBigDecimal("day_limit")).remaining(rs.getBigDecimal("remaining"))
        .lastResetDate(rs.getDate("last_reset_date").toLocalDate()).build();

    public void ensureExists(long userId) {
        jdbc.update("INSERT INTO user_limits(user_id,day_limit,remaining,last_reset_date) VALUES (?,10000.00,10000.00,CURRENT_DATE) ON CONFLICT (user_id) DO NOTHING", userId);
    }

    public void lazyResetIfNeeded(long userId) {
        jdbc.update("UPDATE user_limits SET remaining=day_limit,last_reset_date=CURRENT_DATE WHERE user_id=? AND last_reset_date<CURRENT_DATE", userId);
    }

    public Optional<UserLimit> get(long userId) {
        var list = jdbc.query("SELECT user_id,day_limit,remaining,last_reset_date FROM user_limits WHERE user_id=?", M, userId);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.getFirst());
    }

    public int spend(long userId, java.math.BigDecimal amount) {
        return jdbc.update("UPDATE user_limits SET remaining=remaining-? WHERE user_id=? AND remaining>=?", ps -> {
            ps.setBigDecimal(1, amount);
            ps.setLong(2, userId);
            ps.setBigDecimal(3, amount);
        });
    }

    public int restore(long userId, java.math.BigDecimal amount) {
        return jdbc.update("UPDATE user_limits SET remaining=LEAST(remaining+?, day_limit) WHERE user_id=?", ps -> {
            ps.setBigDecimal(1, amount);
            ps.setLong(2, userId);
        });
    }

    public int updateDayLimit(long userId, java.math.BigDecimal newLimit) {
        return jdbc.update("UPDATE user_limits SET day_limit=?, remaining=LEAST(remaining, ?) WHERE user_id=?", ps -> {
            ps.setBigDecimal(1, newLimit);
            ps.setBigDecimal(2, newLimit);
            ps.setLong(3, userId);
        });
    }

    public int resetAllToday() {
        return jdbc.update("UPDATE user_limits SET remaining=day_limit,last_reset_date=CURRENT_DATE WHERE last_reset_date<CURRENT_DATE");
    }
}
