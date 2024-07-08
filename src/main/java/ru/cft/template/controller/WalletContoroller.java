package ru.cft.template.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.cft.template.dto.WalletDto;
import ru.cft.template.service.WalletService;

@RestController
@RequestMapping("potato/wallet")
@RequiredArgsConstructor
public class WalletContoroller {
    private final WalletService walletService;

    @GetMapping("/getwallet")
    public ResponseEntity<WalletDto> getUserWallet(Authentication authentication) {
        return ResponseEntity.ok(walletService.getWallet(authentication));
    }
}
