package ru.cource.inno.java_pro.payments.dto;

import lombok.*;
import java.math.BigDecimal;

/** DTO продукта из сервиса продуктов. */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductDto {
    private Long id;
    private String accountNumber;
    private BigDecimal balance;
    private String productType;
    private Long userId;
}
