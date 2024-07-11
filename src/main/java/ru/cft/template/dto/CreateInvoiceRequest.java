package ru.cft.template.dto;

public record CreateInvoiceRequest(
        Long amount,
        String mobilePhone,
        String comment
) {
}
