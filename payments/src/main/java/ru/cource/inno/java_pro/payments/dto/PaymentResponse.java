package ru.cource.inno.java_pro.payments.dto;

import lombok.*;
import java.math.BigDecimal;

/** Ответ платёжного сервиса. */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class PaymentResponse {
    private String paymentId;
    private Long productId;
    private BigDecimal amount;
    private String status;
    private BigDecimal balanceAfter;
}
