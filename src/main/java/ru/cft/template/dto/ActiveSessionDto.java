package ru.cft.template.dto;

import lombok.Builder;

import java.util.Date;
import java.util.UUID;

@Builder
public record ActiveSessionDto(
        UUID id,
        UUID userId,
        Date expirationTime,
        Boolean isActive
) {
}
