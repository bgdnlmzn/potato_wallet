package ru.cft.template.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.cft.template.dto.HesoyamResponse;
import ru.cft.template.dto.WalletDto;
import ru.cft.template.service.WalletService;

@RestController
@RequestMapping("potato")
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;

    @GetMapping("/wallet")
    public WalletDto getUserWallet(Authentication authentication) {
        return walletService.getWallet(authentication);
    }
    @PostMapping("/wallet/hesoyam")
    public HesoyamResponse hesoyam(Authentication authentication) {
        return walletService.hesoyam(authentication);
    }
}
