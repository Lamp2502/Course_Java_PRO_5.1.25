package ru.cource.inno.java_pro.payments.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.Map;

/** Глобальный обработчик ошибок платёжного сервиса. */
@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(UpstreamException.class)
    public ResponseEntity<?> upstream(UpstreamException ex) {
        HttpStatus status = HttpStatus.valueOf(ex.getStatusCode().value());
        return ResponseEntity.status(status).body(Map.of(
                "timestamp", OffsetDateTime.now().toString(),
                "status", status.value(),
                "code", "UPSTREAM_" + status.name(),
                "service", ex.getUpstreamService(),
                "message", ex.getMessage()
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> generic(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "timestamp", OffsetDateTime.now().toString(),
                "status", 500,
                "code", "INTERNAL_ERROR",
                "message", ex.getMessage()
        ));
    }
}
