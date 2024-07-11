package ru.cft.template.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateInvoiceRequest(
        @NotNull(message = "Введите сумму.")
        Long amount,

        @NotNull(message = "Введите номер телефона")
        @Pattern(regexp = "7\\d{10}", message = "Формат телефона: 11 цифр, начинается с '7'.")
        String mobilePhone,

        @Size(max = 255, message = "Слишком длинное сообщение.")
        String comment
) {
}
