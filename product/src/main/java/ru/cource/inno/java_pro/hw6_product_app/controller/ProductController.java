package ru.cource.inno.java_pro.hw6_product_app.controller;

import ru.cource.inno.java_pro.hw6_product_app.dao.ProductDao;
import ru.cource.inno.java_pro.hw6_product_app.dto.DebitRequest;
import ru.cource.inno.java_pro.hw6_product_app.dto.DebitResponse;
import ru.cource.inno.java_pro.hw6_product_app.exception.InsufficientFundsException;
import ru.cource.inno.java_pro.hw6_product_app.exception.NotFoundException;
import ru.cource.inno.java_pro.hw6_product_app.model.Product;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
    import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** REST-эндпоинты сервиса продуктов. */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductDao productDao;

    @GetMapping("/products/{productId}")
    public Product getById(@PathVariable long productId) {
        return productDao.findById(productId)
                .orElseThrow(() -> new NotFoundException("Продукт не найден: id=" + productId));
    }

    @GetMapping("/users/{userId}/products")
    public List<Product> getAllByUser(@PathVariable long userId) {
        return productDao.findAllByUserId(userId);
    }

    /** Атомарное списание средств с продукта. */
    @PostMapping("/products/{productId}/debit")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public DebitResponse debit(@PathVariable long productId, @Valid @RequestBody DebitRequest req) {
        productDao.findById(productId)
                .orElseThrow(() -> new NotFoundException("Продукт не найден: id=" + productId));

        int updated = productDao.debitAtomic(productId, req.getAmount());
        if (updated == 0) {
            throw new InsufficientFundsException("Недостаточно средств на продукте: id=" + productId);
        }
        var after = productDao.findById(productId).orElseThrow();
        return DebitResponse.builder()
                .productId(productId)
                .debitedAmount(req.getAmount())
                .balanceAfter(after.getBalance())
                .build();
    }
}
