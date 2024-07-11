package ru.cft.template.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.cft.template.dto.TransDto;
import ru.cft.template.dto.TransferViaPhoneRequest;
import ru.cft.template.dto.TransferViaWalletRequest;
import ru.cft.template.entity.Transaction;
import ru.cft.template.entity.User;
import ru.cft.template.entity.Wallet;
import ru.cft.template.enums.TransStatus;
import ru.cft.template.enums.TransType;
import ru.cft.template.exception.InsufficientFundsException;
import ru.cft.template.exception.UserNotFoundException;
import ru.cft.template.exception.WalletNotFoundException;
import ru.cft.template.mapper.TransMapper;
import ru.cft.template.repository.TransactionRepository;
import ru.cft.template.repository.UserRepository;
import ru.cft.template.repository.WalletRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final UserServiceImpl userServiceImpl;
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public TransDto createTransactionPhone(Authentication authentication, TransferViaPhoneRequest body) {
        User sender = userServiceImpl.getUserByAuthentication(authentication);
        Wallet senderWallet = sender.getWallet();

        User receiver = findUserByMobilePhone(body.mobilePhone());
        Wallet receiverWallet = receiver.getWallet();

        Transaction transaction = checkSufficientFunds(senderWallet, receiverWallet, body.amount(), body.mobilePhone(), true);

        if (transaction.getStatus() == TransStatus.FAILED) {
            throw new InsufficientFundsException("Недостаточно средств на кошельке отправителя.");
        }

        updateWalletBalancesAndSaveTransaction(senderWallet, receiverWallet, body.amount(), transaction);
        createAndSaveIncomingTransaction(body.amount(), senderWallet, receiverWallet, receiver.getMobilePhone());

        return TransMapper.mapTransDto(transaction);
    }

    @Override
    public TransDto createTransactionWallet(Authentication authentication, TransferViaWalletRequest body) {
        User sender = userServiceImpl.getUserByAuthentication(authentication);
        Wallet senderWallet = sender.getWallet();

        Wallet receiverWallet = findWalletById(body.walletId());
        User receiver = findUserByWallet(receiverWallet);

        Transaction transaction = checkSufficientFunds(senderWallet, receiverWallet, body.amount(), receiver.getMobilePhone(), false);

        if (transaction.getStatus() == TransStatus.FAILED) {
            throw new InsufficientFundsException("Недостаточно средств на кошельке отправителя.");
        }

        transaction.setReceiverPhone(Long.valueOf(receiver.getMobilePhone()));
        updateWalletBalancesAndSaveTransaction(senderWallet, receiverWallet, body.amount(), transaction);
        createAndSaveIncomingTransaction(body.amount(), senderWallet, receiverWallet, receiver.getMobilePhone());

        return TransMapper.mapTransDto(transaction);
    }

    @Override
    public List<TransDto> getAllTransactions(Authentication authentication, String type) {
        TransType transType = TransType.valueOf(type.toUpperCase());
        User user = userServiceImpl.getUserByAuthentication(authentication);
        Wallet wallet = user.getWallet();

        List<Transaction> transactions;
        if (transType == TransType.INCOMING) {
            transactions = transactionRepository.findByReceiverWalletAndType(wallet, transType);
        } else {
            transactions = transactionRepository.findBySenderWalletAndType(wallet, transType);
        }

        return transactions.stream()
                .map(TransMapper::mapTransDto)
                .collect(Collectors.toList());
    }


    private User findUserByMobilePhone(String mobilePhone) {
        return userRepository.findByMobilePhone(mobilePhone)
                .orElseThrow(() -> new UserNotFoundException("Получатель не найден."));
    }

    private Wallet findWalletById(String walletId) {
        return walletRepository.findById(UUID.fromString(walletId))
                .orElseThrow(() -> new WalletNotFoundException("Кошелек получателя не найден."));
    }

    private User findUserByWallet(Wallet wallet) {
        return userRepository.findByWallet(wallet)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
    }

    private Transaction checkSufficientFunds(Wallet senderWallet, Wallet receiverWallet, long amount, String receiverPhone, boolean isPhone) {
        Transaction transaction;
        transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setReceiverPhone(Long.valueOf(receiverPhone));
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setSenderWallet(senderWallet);
        transaction.setReceiverWallet(receiverWallet);
        transaction.setType(TransType.OUTGOING);

        if (senderWallet.getAmount().compareTo(amount) < 0) {
            transaction.setStatus(TransStatus.FAILED);
            transactionRepository.save(transaction);
            return transaction;
        }

        transaction.setStatus(TransStatus.SUCCESSFUL);
        return transaction;
    }

    private void updateWalletBalancesAndSaveTransaction(Wallet senderWallet, Wallet receiverWallet, long amount, Transaction transaction) {
        senderWallet.setAmount(senderWallet.getAmount() - amount);
        receiverWallet.setAmount(receiverWallet.getAmount() + amount);

        transaction.setStatus(TransStatus.SUCCESSFUL);

        transactionRepository.save(transaction);
        walletRepository.save(senderWallet);
        walletRepository.save(receiverWallet);
    }

    private void createAndSaveIncomingTransaction(long amount, Wallet senderWallet, Wallet receiverWallet, String receiverPhone) {
        Transaction incomingTransaction = new Transaction();
        incomingTransaction.setAmount(amount);
        incomingTransaction.setReceiverPhone(Long.valueOf(receiverPhone));
        incomingTransaction.setTransactionDate(LocalDateTime.now());
        incomingTransaction.setSenderWallet(senderWallet);
        incomingTransaction.setReceiverWallet(receiverWallet);
        incomingTransaction.setStatus(TransStatus.SUCCESSFUL);
        incomingTransaction.setType(TransType.INCOMING);

        transactionRepository.save(incomingTransaction);
    }
}

