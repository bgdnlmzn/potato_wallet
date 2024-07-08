package ru.cft.template.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record WalletDto(
        UUID id,
        Long amount,
        LocalDateTime updatedAt
) {
}
