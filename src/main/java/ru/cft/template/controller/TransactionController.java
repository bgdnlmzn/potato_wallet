package ru.cft.template.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.cft.template.dto.TransDto;
import ru.cft.template.dto.TransferViaPhoneRequest;
import ru.cft.template.dto.TransferViaWalletRequest;
import ru.cft.template.service.TransactionService;

import java.util.List;

@RestController
@RequestMapping("potato")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/transfer/phone")
    public TransDto createTransactionViaPhone(Authentication authentication, @RequestBody TransferViaPhoneRequest body) {
        return transactionService.createTransactionPhone(authentication,body);
    }
    @PostMapping("/transfer/wallet")
    public TransDto createTransactionViaWallet(Authentication authentication, @RequestBody TransferViaWalletRequest body) {
        return transactionService.createTransactionWallet(authentication,body);
    }
    @GetMapping("/transfer/{type}")
    public List<TransDto> getTransaction(Authentication authentication, @PathVariable String type) {
        return transactionService.getAllTransactions(authentication,type);
    }
}
