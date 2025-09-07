package ru.cource.inno.java_pro.hw6_product_app.exception;

/** 404 Not Found для доменных сущностей. */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) { super(message); }
}
