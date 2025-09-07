package ru.cource.inno.java_pro.hw6_product_app.exception;

/** Недостаточно средств на продукте. */
public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String message) { super(message); }
}
