package ru.cft.template.mapper;

import org.springframework.stereotype.Component;
import ru.cft.template.dto.WalletDto;
import ru.cft.template.entity.Wallet;

@Component
public class WalletMapper {
    public static WalletDto mapWallet(Wallet wallet) {
        return new WalletDto(
                wallet.getId(),
                wallet.getAmount(),
                wallet.getUpdatedAt()
        );
    }
}
