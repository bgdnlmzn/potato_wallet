package ru.cft.template.dto;


import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record UserDto(
        String id,
        String walletId,
        String lastName,
        String firstName,
        String middleName,
        String email,
        String mobilePhone,
        LocalDate birthDate,
        LocalDateTime regDate,
        LocalDateTime updDate
) {
}
