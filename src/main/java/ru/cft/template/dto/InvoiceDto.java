package ru.cft.template.dto;

import ru.cft.template.enums.InvoiceStatus;
import ru.cft.template.enums.TransType;

import java.time.LocalDateTime;
import java.util.UUID;

public record InvoiceDto(
        UUID id,
        Long amount,
        UUID receiverId,
        UUID senderId,
        String comment,
        InvoiceStatus status,
        LocalDateTime date

) {
}
