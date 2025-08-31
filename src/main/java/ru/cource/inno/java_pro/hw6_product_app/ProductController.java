/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2023 VTB Group. All rights reserved.
 */

package ru.cource.inno.java_pro.hw6_product_app;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** REST-эндпоинты для продуктов клиентов. */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    /** Создать продукт для пользователя. */
    @PostMapping("/users/{userId}/products")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(@PathVariable long userId, @Valid @RequestBody ProductRequest req) {
        Product created = productService.create(req.toProduct(userId));
        return ProductResponse.from(created);
    }

    /** Получить продукт по productId. */
    @GetMapping("/products/{productId}")
    public ProductResponse getById(@PathVariable long productId) {
        return ProductResponse.from(productService.getById(productId));
    }

    /** Получить все продукты пользователя по userId. */
    @GetMapping("/users/{userId}/products")
    public List<ProductResponse> getAllByUser(@PathVariable long userId) {
        return productService.getAllByUserId(userId).stream().map(ProductResponse::from).toList();
    }
}

