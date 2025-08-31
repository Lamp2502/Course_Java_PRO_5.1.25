/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2023 VTB Group. All rights reserved.
 */

package ru.cource.inno.java_pro.hw6_product_app;

import lombok.*;

import java.math.BigDecimal;

/**
 * ProductResponse.
 * @author Kirill_Lustochkin
 * @since 31.08.2025
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {
    private Long id;
    private String accountNumber;
    private BigDecimal balance;
    private ProductType productType;
    private Long userId;

    public static ProductResponse from(Product p) {
        return ProductResponse.builder()
            .id(p.getId())
            .accountNumber(p.getAccountNumber())
            .balance(p.getBalance())
            .productType(p.getProductType())
            .userId(p.getUserId())
            .build();
    }
}

