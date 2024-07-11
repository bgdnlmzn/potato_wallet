package ru.cft.template.dto;

import lombok.Builder;

@Builder
public record TransferViaWalletRequest(
        String walletId,
        Long amount
) {
}
