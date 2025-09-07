package ru.cource.inno.java_pro.payments.client;

import ru.cource.inno.java_pro.payments.dto.ProductDto;
import ru.cource.inno.java_pro.payments.exception.UpstreamException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/** RestClient для вызовов в продуктовый сервис. */
@Component
@RequiredArgsConstructor
public class ProductClient {
    private final RestClient rest;

    public List<ProductDto> getProductsByUser(long userId) {
        try {
            ProductDto[] arr = rest.get()
                    .uri("/api/users/{userId}/products", userId)
                    .retrieve()
                    .body(ProductDto[].class);
            return arr == null ? List.of() : Arrays.asList(arr);
        } catch (RestClientResponseException ex) {
            throw new UpstreamException(HttpStatusCode.valueOf(ex.getStatusCode().value()), "product", ex.getResponseBodyAsString());
        } catch (RestClientException ex) {
            throw new UpstreamException(HttpStatusCode.valueOf(502), "product", ex.getMessage());
        }
    }

    public ProductDto getProduct(long productId) {
        try {
            return rest.get()
                    .uri("/api/products/{id}", productId)
                    .retrieve()
                    .body(ProductDto.class);
        } catch (RestClientResponseException ex) {
            throw new UpstreamException(HttpStatusCode.valueOf(ex.getStatusCode().value()), "product", ex.getResponseBodyAsString());
        } catch (RestClientException ex) {
            throw new UpstreamException(HttpStatusCode.valueOf(502), "product", ex.getMessage());
        }
    }

    public DebitResponse debit(long productId, java.math.BigDecimal amount) {
        try {
            return rest.post()
                    .uri("/api/products/{id}/debit", productId)
                    .body(Map.of("amount", amount))
                    .retrieve()
                    .body(DebitResponse.class);
        } catch (RestClientResponseException ex) {
            throw new UpstreamException(HttpStatusCode.valueOf(ex.getStatusCode().value()), "product", ex.getResponseBodyAsString());
        } catch (RestClientException ex) {
            throw new UpstreamException(HttpStatusCode.valueOf(502), "product", ex.getMessage());
        }
    }

    /** Вспомогательный DTO под ответ дебета. */
    @lombok.Data
    public static class DebitResponse { Long productId; java.math.BigDecimal debitedAmount; java.math.BigDecimal balanceAfter; }
}
