/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2023 VTB Group. All rights reserved.
 */

package ru.cource.inno.java_pro.hw6_product_app;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Product.
 * @author Kirill_Lustochkin
 * @since 31.08.2025
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    /** Идентификатор продукта. */
    private Long id;
    /** Номер счёта/карты. */
    private String accountNumber;
    /** Баланс. */
    private BigDecimal balance;
    /** Тип продукта. */
    private ProductType productType;
    /** Владелец (userId). */
    private Long userId;
}
