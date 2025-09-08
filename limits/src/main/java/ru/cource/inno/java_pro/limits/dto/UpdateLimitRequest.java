package ru.cource.inno.java_pro.limits.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateLimitRequest {
    @NotNull
    @Digits(integer = 19, fraction = 2)
    @PositiveOrZero
    private BigDecimal dayLimit;
}