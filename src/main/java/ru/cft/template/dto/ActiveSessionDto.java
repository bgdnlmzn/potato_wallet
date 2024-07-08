package ru.cft.template.dto;

import java.util.Date;
import java.util.UUID;

public record ActiveSessionDto(
        UUID id,
        UUID userId,
        Date expirationTime,
        Boolean isActive
) {
}
