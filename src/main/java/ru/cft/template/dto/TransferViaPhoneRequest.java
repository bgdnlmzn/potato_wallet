package ru.cft.template.dto;

import lombok.Builder;

@Builder
public record TransferViaPhoneRequest(
        String mobilePhone,
        Long amount
) {
}
