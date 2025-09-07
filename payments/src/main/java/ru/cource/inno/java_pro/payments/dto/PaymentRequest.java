package ru.cource.inno.java_pro.payments.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

/** Запрос на исполнение платежа. */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class PaymentRequest {
    @NotNull private Long productId;
    @NotNull @Digits(integer = 19, fraction = 2) @Positive private BigDecimal amount;
}
