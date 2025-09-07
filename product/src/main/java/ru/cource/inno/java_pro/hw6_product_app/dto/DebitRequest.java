package ru.cource.inno.java_pro.hw6_product_app.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.math.BigDecimal;

/** Запрос на списание средств с продукта. */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class DebitRequest {
    @NotNull
    @Digits(integer = 19, fraction = 2)
    private BigDecimal amount;
}
