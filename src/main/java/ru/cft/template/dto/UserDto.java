package ru.cft.template.dto;


import lombok.Builder;

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
        LocalDateTime regDate,
        LocalDateTime updDate
) {
}
