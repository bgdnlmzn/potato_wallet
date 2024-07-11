package ru.cft.template.service;

import org.springframework.security.core.Authentication;
import ru.cft.template.dto.HesoyamResponse;
import ru.cft.template.dto.WalletDto;
import ru.cft.template.entity.Wallet;

public interface WalletService {
    Wallet createWallet();
    WalletDto getWallet(Authentication authentication);
    HesoyamResponse hesoyam(Authentication authentication);

}
