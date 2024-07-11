package ru.cft.template.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.cft.template.entity.Transaction;
import ru.cft.template.entity.Wallet;
import ru.cft.template.enums.TransType;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findByReceiverWalletAndType(Wallet receiverWallet, TransType type);
    List<Transaction> findBySenderWalletAndType(Wallet senderWallet, TransType type);
}
