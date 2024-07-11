package ru.cft.template.dto;

import ru.cft.template.enums.TransStatus;
import ru.cft.template.enums.TransType;

import java.time.LocalDateTime;
import java.util.UUID;

public record TransDto(
    UUID id,
    Long amount,
    TransType type,
    TransStatus status,
    LocalDateTime date

){

}
