package ru.cft.template.dto;

import lombok.Builder;

import java.util.Date;
import java.util.UUID;

@Builder
public record SessionDto(
        UUID id,
        UUID userId,
        String token,
        Date expTime
) {
}
