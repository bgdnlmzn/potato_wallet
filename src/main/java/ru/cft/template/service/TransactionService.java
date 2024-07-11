package ru.cft.template.service;

import org.springframework.security.core.Authentication;
import ru.cft.template.dto.TransDto;
import ru.cft.template.dto.TransferViaPhoneRequest;
import ru.cft.template.dto.TransferViaWalletRequest;
import ru.cft.template.enums.TransType;

import java.util.List;

public interface TransactionService {
    TransDto createTransactionPhone(Authentication authentication, TransferViaPhoneRequest body);
    TransDto createTransactionWallet(Authentication authentication, TransferViaWalletRequest body);
    List<TransDto> getAllTransactions(Authentication authentication, String type);

}
