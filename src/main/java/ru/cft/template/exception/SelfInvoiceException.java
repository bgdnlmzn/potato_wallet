package ru.cft.template.exception;

public class SelfInvoiceException extends RuntimeException {
    public SelfInvoiceException(String message) {
        super(message);
    }
}