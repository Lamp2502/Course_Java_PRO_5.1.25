package ru.cource.inno.java_pro.limits.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LimitResponse {
    private Long userId;
    private BigDecimal dayLimit;
    private BigDecimal remaining;
    private LocalDate lastResetDate;
}