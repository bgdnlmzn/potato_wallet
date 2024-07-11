package ru.cft.template.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record LoginRequest(
        @NotNull(message = "Введите номер телефона.")
        @Pattern(regexp = "7\\d{10}", message = "Формат телефона: 11 цифр, начинается с '7' ")
        String mobilePhone,

        @NotNull(message = "Введите пароль.")
        String password
) {
}
