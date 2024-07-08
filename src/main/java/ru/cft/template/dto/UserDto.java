package ru.cft.template.dto;


import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

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
        Date regDate,
        Date updDate
) {
}
