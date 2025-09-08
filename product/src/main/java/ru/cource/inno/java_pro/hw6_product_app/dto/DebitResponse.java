package ru.cource.inno.java_pro.hw6_product_app.dto;

import lombok.*;
import java.math.BigDecimal;

/** Ответ на списание средств. */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class DebitResponse {
    private Long productId;
    private BigDecimal debitedAmount;
    private BigDecimal balanceAfter;
}
