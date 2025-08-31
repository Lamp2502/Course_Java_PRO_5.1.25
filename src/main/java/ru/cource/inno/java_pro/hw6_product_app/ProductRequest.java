/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2023 VTB Group. All rights reserved.
 */

package ru.cource.inno.java_pro.hw6_product_app;

/**
 * ProductRequest.
 * @author Kirill_Lustochkin
 * @since 31.08.2025
 */

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

/** Запрос на создание продукта. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {

    /** Номер счёта/карты. */
    @NotBlank
    @Size(max = 64)
    private String accountNumber;

    /** Баланс. */
    @NotNull
    @Digits(integer = 19, fraction = 2)
    private BigDecimal balance;

    /** Тип продукта (ACCOUNT|CARD). */
    @NotNull
    private ProductType productType;

    public Product toProduct(long userId) {
        return Product.builder()
            .accountNumber(accountNumber)
            .balance(balance)
            .productType(productType)
            .userId(userId)
            .build();
    }
}

