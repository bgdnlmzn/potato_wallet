package ru.cft.template.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record TransferViaWalletRequest(
        @NotNull(message = "Введите номер кошелька.")
        String walletId,

        @NotNull(message = "Введите сумму.")
        Long amount
) {
}
