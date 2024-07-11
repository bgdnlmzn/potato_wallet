package ru.cft.template.dto;

import lombok.Builder;

@Builder
public record LoginRequest(
        String mobilePhone,
        String password
) {
}
