package ru.cource.inno.java_pro.payments.exception;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

/** Ошибка обращения к апстрим-сервису. */
@Getter
public class UpstreamException extends RuntimeException {
    private final HttpStatusCode statusCode;
    private final String upstreamService;

    public UpstreamException(HttpStatusCode statusCode, String upstreamService, String message) {
        super(message);
        this.statusCode = statusCode;
        this.upstreamService = upstreamService;
    }
}
