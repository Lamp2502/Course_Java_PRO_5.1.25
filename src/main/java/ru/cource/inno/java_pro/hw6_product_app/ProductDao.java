/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2023 VTB Group. All rights reserved.
 */

package ru.cource.inno.java_pro.hw6_product_app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

/** DAO для работы с продуктами (хранение + выборки). */
@Repository
@RequiredArgsConstructor
@Slf4j
public class ProductDao {

    private final JdbcTemplate jdbc;

    private static final RowMapper<Product> MAPPER = (rs, rowNum) -> Product.builder()
        .id(rs.getLong("id"))
        .accountNumber(rs.getString("account_number"))
        .balance(rs.getBigDecimal("balance"))
        .productType(ProductType.valueOf(rs.getString("product_type")))
        .userId(rs.getLong("user_id"))
        .build();

    /** Создать продукт. */
    public Product create(Product p) {
        String sql = "INSERT INTO products (account_number, balance, product_type, user_id) VALUES (?,?,?,?)";
        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, p.getAccountNumber());
            ps.setBigDecimal(2, p.getBalance());
            ps.setString(3, p.getProductType().name());
            ps.setLong(4, p.getUserId());
            return ps;
        }, kh);
        Number key = kh.getKey();
        p.setId(key == null ? null : key.longValue());
        return p;
    }

    /** Найти продукт по productId. */
    public Optional<Product> findById(Long id) {
        try {
            return Optional.ofNullable(jdbc.queryForObject(
                "SELECT id, account_number, balance, product_type, user_id FROM products WHERE id = ?",
                MAPPER, id
            ));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    /** Все продукты конкретного пользователя. */
    public List<Product> findAllByUserId(Long userId) {
        return jdbc.query(
            "SELECT id, account_number, balance, product_type, user_id " +
                "FROM products WHERE user_id = ? ORDER BY id",
            MAPPER, userId
        );
    }
}
