package ru.cft.template.mapper;

import org.springframework.stereotype.Component;
import ru.cft.template.dto.WalletDto;
import ru.cft.template.entity.Wallet;

import java.util.Optional;

@Component
public class WalletMapper {
    public static WalletDto mapWallet(Optional<Wallet> wallet) {
        return new WalletDto(
                wallet.get().getId(),
                wallet.get().getAmount(),
                wallet.get().getUpdatedAt()
        );
    }
}
