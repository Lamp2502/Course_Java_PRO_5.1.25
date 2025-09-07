package ru.cource.inno.java_pro.hw6_product_app.model;

import lombok.*;
import java.math.BigDecimal;

/** Продукт клиента. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    private Long id;              // Идентификатор продукта
    private String accountNumber; // Номер счёта/карты
    private BigDecimal balance;   // Баланс
    private ProductType productType; // Тип продукта
    private Long userId;          // Владелец (userId)
}
