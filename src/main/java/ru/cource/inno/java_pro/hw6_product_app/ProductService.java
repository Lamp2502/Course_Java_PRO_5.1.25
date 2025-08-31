/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2023 VTB Group. All rights reserved.
 */

package ru.cource.inno.java_pro.hw6_product_app;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/** Сервис продуктов: хранит и выдаёт нужные выборки. */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductDao productDao;

    /** Создать продукт для пользователя. */
    public Product create(Product p) {
        return productDao.create(p);
    }

    /** Найти продукт по productId. */
    public Product getById(long productId) {
        return productDao.findById(productId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Продукт не найден"));
    }

    /** Все продукты пользователя. */
    public List<Product> getAllByUserId(long userId) {
        return productDao.findAllByUserId(userId);
    }
}

