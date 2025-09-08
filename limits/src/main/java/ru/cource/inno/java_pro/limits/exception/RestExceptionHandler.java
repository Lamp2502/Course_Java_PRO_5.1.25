package ru.cource.inno.java_pro.limits.exception;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> conf(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("timestamp", OffsetDateTime.now().toString(), "status", 409, "code", "LIMIT_CONFLICT", "message", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> gen(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("timestamp", OffsetDateTime.now().toString(), "status", 500, "code", "INTERNAL_ERROR", "message", ex.getMessage()));
    }
}