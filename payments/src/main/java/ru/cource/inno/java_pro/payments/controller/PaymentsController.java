package ru.cource.inno.java_pro.payments.controller;

import ru.cource.inno.java_pro.payments.client.ProductClient;
import ru.cource.inno.java_pro.payments.dto.PaymentRequest;
import ru.cource.inno.java_pro.payments.dto.PaymentResponse;
import ru.cource.inno.java_pro.payments.dto.ProductDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/** REST-эндпоинты платёжного сервиса. */
@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Slf4j
public class PaymentsController {

    private final ProductClient productClient;

    @GetMapping("/users/{userId}/products")
    public List<ProductDto> productsByUser(@PathVariable long userId) {
        return productClient.getProductsByUser(userId);
    }

    @PostMapping("/execute")
    @ResponseStatus(HttpStatus.OK)
    public PaymentResponse execute(@Valid @RequestBody PaymentRequest req) {
        productClient.getProduct(req.getProductId());
        var debit = productClient.debit(req.getProductId(), req.getAmount());
        return PaymentResponse.builder()
                .paymentId(UUID.randomUUID().toString())
                .productId(req.getProductId())
                .amount(req.getAmount())
                .status("SUCCESS")
                .balanceAfter(debit.getBalanceAfter())
                .build();
    }
}
