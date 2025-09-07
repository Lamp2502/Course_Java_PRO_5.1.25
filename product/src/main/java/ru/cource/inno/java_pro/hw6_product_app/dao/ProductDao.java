package ru.cource.inno.java_pro.hw6_product_app.dao;

import ru.cource.inno.java_pro.hw6_product_app.model.Product;
import ru.cource.inno.java_pro.hw6_product_app.model.ProductType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/** DAO для продуктов: выборки и атомарное списание. */
@Repository
@RequiredArgsConstructor
@Slf4j
public class ProductDao {

    private final JdbcTemplate jdbc;

    private static final RowMapper<Product> M = (rs, i) -> Product.builder()
            .id(rs.getLong("id"))
            .accountNumber(rs.getString("account_number"))
            .balance(rs.getBigDecimal("balance"))
            .productType(ProductType.valueOf(rs.getString("product_type")))
            .userId(rs.getLong("user_id"))
            .build();

    public Optional<Product> findById(Long id) {
        var list = jdbc.query("SELECT id, account_number, balance, product_type, user_id FROM products WHERE id=?", M, id);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.getFirst());
    }

    public List<Product> findAllByUserId(Long userId) {
        return jdbc.query("SELECT id, account_number, balance, product_type, user_id FROM products WHERE user_id=? ORDER BY id", M, userId);
    }

    /** Атомарное списание, если достаточно средств. Возвращает 1 при успехе, 0 иначе. */
    public int debitAtomic(long productId, java.math.BigDecimal amount) {
        return jdbc.update(
                "UPDATE products SET balance = balance - ? WHERE id = ? AND balance >= ?",
                ps -> {
                    ps.setBigDecimal(1, amount);
                    ps.setLong(2, productId);
                    ps.setBigDecimal(3, amount);
                }
        );
    }
}
